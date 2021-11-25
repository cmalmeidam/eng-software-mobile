package com.example.vinilosandroid.network

import android.content.Context
import android.content.SharedPreferences
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import com.example.vinilosandroid.models.Track

class CacheManager(context: Context) {
    companion object {
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
        const val TRACKS_SPREFS = "com.example.vinilosandroid.app.tracks"
        fun getPrefs(context: Context, name: String): SharedPreferences {
            return context.getSharedPreferences(
                name,
                Context.MODE_PRIVATE
            )
        }
    }
    private var tracks: ArrayMap<Int, List<Track>> = arrayMapOf()

    fun addTracks(albumId: Int, comment: List<Track>){
        if (tracks.containsKey(albumId)){
            tracks[albumId] = comment
        }
    }
    fun getTracks(albumId: Int) : List<Track>{
        return if (tracks.containsKey(albumId)) tracks[albumId]!! else listOf<Track>()
    }
}
