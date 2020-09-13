package com.example.rxkotlinsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppDelegate :Application() {

    var token:String? = "token"
    companion object {
        lateinit var appDelegate: AppDelegate
    }

    override fun onCreate() {
        super.onCreate()
        appDelegate = this
    }


}