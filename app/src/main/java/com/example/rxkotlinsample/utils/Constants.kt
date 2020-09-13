package com.example.rxkotlinsample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


object Constants {

    const val BASE_URL = "https://newsapi.org/v2/"
    const val BASE_URL_POST = "https://jsonplaceholder.typicode.com/"

   fun isInternetConnected(context: Context):Boolean{
       val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
       return activeNetwork?.isConnectedOrConnecting == true

   }

}