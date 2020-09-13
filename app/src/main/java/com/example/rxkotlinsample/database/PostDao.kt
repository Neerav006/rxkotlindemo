package com.example.rxkotlinsample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rxkotlinsample.model.postmodel.Post
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

@Dao
interface PostDao {

    @Query("SELECT * from post ORDER BY title ASC")
    fun getNewsList(): List<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(newsList:List<Post>): List<Long>

    @Query("DELETE FROM post")
    fun deleteAllNews()
}