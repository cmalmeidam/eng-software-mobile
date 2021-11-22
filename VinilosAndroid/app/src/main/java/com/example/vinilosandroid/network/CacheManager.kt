package com.example.vinilosandroid.network

import android.content.Context
import android.content.SharedPreferences

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
        const val ALBUMS_SPREFS = "com.example.vinilosandroid.app.albums"
        const val MUSICIANS_SPREFS = "com.example.vinilosandroid.app.musicians"
        const val COLLECTORS_SPREFS = "com.example.vinilosandroid.app.collectors"
        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }
}