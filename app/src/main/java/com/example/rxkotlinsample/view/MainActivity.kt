package com.example.rxkotlinsample.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxkotlinsample.R
import com.example.rxkotlinsample.adapter.PostListAdapter
import com.example.rxkotlinsample.database.AppDatabase
import com.example.rxkotlinsample.database.PostRepository
import com.example.rxkotlinsample.di.PreferenceHelper
import com.example.rxkotlinsample.model.ErrorBodyResponse
import com.example.rxkotlinsample.model.postmodel.Post
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val subscription = CompositeDisposable()
    private lateinit var postListAdapter: PostListAdapter
    private lateinit var appDatabase: AppDatabase
    private lateinit var postRepository: PostRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var postList:List<Post> = ArrayList()

    @Inject lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences.edit {
            putString("name","hello")
        }

        Log.e("name:-",preferences.getString("name","")?:"")


        appDatabase = AppDatabase.getDatabase(applicationContext)
        postRepository = PostRepository(appDatabase.appDao())

        // no internet than load from local database
        /*if (Constants.isInternetConnected(applicationContext)){
            getAllPost()
        }
        else{
           coroutineScope.launch {
             val postList =  getPostsFromLocalDb()
              if (postList.isNotEmpty()){
                  rvList.layoutManager = LinearLayoutManager(
                      this@MainActivity,
                      LinearLayoutManager.VERTICAL, false
                  )
                  postListAdapter = PostListAdapter(
                      this@MainActivity,
                      postList as ArrayList<Post>
                  ) { post, i -> onItemClick(post, i) }
                  rvList.adapter = postListAdapter
              }
           }
        }*/

        // if no data in local database than load from network
        coroutineScope.launch {
            postList =  getPostsFromLocalDb()
            if (postList.isEmpty()){
                getAllPost()
            }
            else{
                progressBar.visibility = View.GONE
                rvList.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL, false
                )
                postListAdapter = PostListAdapter(
                    this@MainActivity,
                    postList
                ) { post, i -> onItemClick(post, i) }
                rvList.adapter = postListAdapter
            }
        }


    }
    // json placeholder get post api
    private fun getAllPost() {
        subscription.add(APIManager.instance.api.getAllPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            // filter list where title length > 50
           // .map { it.filter { it.title.length > 50 } }
            .doOnComplete {
                progressBar.visibility = View.GONE
            }
            .doOnError {
                progressBar.visibility = View.GONE
                if (it is HttpException) {
                    val exception = it
                    val errorBody = exception.response()?.errorBody()?.string()
                    errorBody?.let {
                        val errorBodyResponse = Gson().fromJson(it, ErrorBodyResponse::class.java)
                        Toast.makeText(
                            this@MainActivity,
                            errorBodyResponse.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
            .subscribeBy {
                if (it.isNotEmpty()) {

                    insertAllPost(it)
                    postList = it
                    rvList.layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.VERTICAL, false
                    )
                    postListAdapter = PostListAdapter(
                        this@MainActivity,
                        postList
                    ) { post, i -> onItemClick(post, i) }
                    rvList.adapter = postListAdapter
                } else {
                    Toast.makeText(this, "No record found!", Toast.LENGTH_SHORT).show()
                }


            })

    }

    // paid api it has limit
    private fun getAllNews() {
        subscription.add(APIManager.instance.api.getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                progressBar.visibility = View.GONE
            }
            .doOnError {
                progressBar.visibility = View.GONE
                if (it is HttpException) {
                    val exception = it as HttpException
                    val errorBody = exception.response()?.errorBody()?.string()
                    errorBody?.let {
                        val errorBodyResponse = Gson().fromJson(it, ErrorBodyResponse::class.java)
                        Toast.makeText(
                            this@MainActivity,
                            errorBodyResponse.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
            .subscribe(
                {
                    Log.e("news list", it.news.size.toString())
                },
                {

                }
            ))
    }

    private fun onItemClick(post: Post, pos: Int) {
        Toast.makeText(this, post.title, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription.clear()
    }

    // insert all news in local database
    private fun insertAllPost(list: List<Post>) {
        coroutineScope.launch {
            withContext(Dispatchers.IO){
             val insertIdList = postRepository.insertAll(list)
              Log.e("inserted data",insertIdList.size.toString())

            }
        }
    }

    // get all data from local db
    private suspend fun getPostsFromLocalDb():List<Post> = withContext(Dispatchers.IO)  {
             postRepository.getAllNews()
    }


}