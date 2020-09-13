package com.example.rxkotlinsample.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PreferenceHelper {

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context):SharedPreferences{

         return context.getSharedPreferences("app",Context.MODE_PRIVATE)

    }
}