package com.example.rxkotlinsample.model.postmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String
)