package com.example.rxkotlinsample.retrofit

import android.content.SharedPreferences
import android.util.Log
import com.example.rxkotlinsample.AppDelegate
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject


class AuthInterceptor() : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("token", AppDelegate.appDelegate.token ?: "")

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + AppDelegate.appDelegate.token)
            .build()
        return chain.proceed(request)
    }
}