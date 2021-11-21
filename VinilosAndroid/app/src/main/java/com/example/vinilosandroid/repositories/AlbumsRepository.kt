package com.example.vinilosandroid.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import kotlinx.serialization.encodeToString

class AlbumsRepository (val application: Application) {
    val format = Json {  }

    suspend fun refreshData(): List<Album> {
        var keys = 1
        var albums = getAlbums()
        return if(albums.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                albums = NetworkServiceAdapter.getInstance(application).getAlbums()
                addAlbums(1,albums)
                albums
            }
        } else albums
    }

    suspend fun getAlbums(): List<Album> {
        var keys = 1
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if (prefs.contains(keys.toString())) {
            val storedVal = prefs.getString(keys.toString(), "")
            if (!storedVal.isNullOrBlank()) {
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                return format.decodeFromString<List<Album>>(storedVal)
            }
        }
        return listOf<Album>()
    }

    suspend fun addAlbums(key: Int, albums: List<Album>) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if (!prefs.contains(key.toString())) {
            var store = format.encodeToString(albums)
            with(prefs.edit(), {
                putString(key.toString(), store)
                apply()
            })
        }
    }
}



