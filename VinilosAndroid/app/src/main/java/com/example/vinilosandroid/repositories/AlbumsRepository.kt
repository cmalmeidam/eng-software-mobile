package com.example.vinilosandroid.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class AlbumsRepository (val application: Application){
    private var key = 3
    val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    suspend fun refreshData(): List<Album> {
        var albums = getAlbums()
        return if(albums.isNullOrEmpty()){
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                albums = NetworkServiceAdapter.getInstance(application).getAlbums()
                addAlbums(key, albums)
                albums
            }
        } else albums
    }
    suspend fun refreshData2(): List<Album> {
        var albums = listOf<Album>()
        if (cm.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI || cm.activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE) {
            albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            addAlbums(key, albums)
        }
        return albums
    }
    fun getAlbums(): List<Album>{
        val format = Json {  }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains(key.toString())){
            val storedVal = prefs.getString(key.toString(), "")
            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<Album>>(storedVal)
            }
        }
        return listOf<Album>()
    }
    fun addAlbums(key:Int, albums: List<Album>){
        val format = Json {  }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains(key.toString())){
            var store = format.encodeToString(albums)
            with(prefs.edit(),{
                putString(key.toString(), store)
                apply()
            })
        } else{
            prefs.edit().clear().apply()
            var store = format.encodeToString(albums)
            with(prefs.edit(),{
                putString(key.toString(), store)
                apply()
        })
        }
    }

    suspend fun postData(
        album:Album
    ){if (cm.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI || cm.activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE) {
            NetworkServiceAdapter.getInstance(application).postAlbum(
                album
            )
        }
        else{
            println("no hay red")
        }
    }}
