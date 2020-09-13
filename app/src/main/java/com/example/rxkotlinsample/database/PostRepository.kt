package com.example.rxkotlinsample.database

import com.example.rxkotlinsample.model.postmodel.Post
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

class PostRepository(private val newsDao: PostDao) {

     fun insertAll(newsList:List<Post>):List<Long>{
       return newsDao.insertNews(newsList)

    }

     fun getAllNews() : List<Post>{
        return  newsDao.getNewsList()
    }

     fun deleteAllNews(){
         newsDao.deleteAllNews()
    }

}