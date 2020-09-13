package com.example.rxkotlinsample.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class NewsResponse {

    @Parcelize
    data class Response(
        @SerializedName("articles")
        val news: List<News>
    ) : Parcelable {

    }

    @Parcelize
    data class News(
        val title: String,
        @SerializedName("urlToImage")
        val image: String?,
        @SerializedName("description")
        val description: String?
    ) : Parcelable


}