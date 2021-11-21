package com.example.vinilosandroid.repositories

import android.app.Application
import android.util.Log
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter

class AlbumsRepository (val application: Application){
    suspend fun refreshData(): List<Album>{
        var key = 3
        var potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums(key)
        if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            var albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(key,albums)
            return albums
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }


    suspend fun postData(
        albumnName: String,
        albumCover: String,
        albumGenre: String,
        albumDescription: String,
        albumRecordLabel: String,
        albumDate: String
    ){
        NetworkServiceAdapter.getInstance(application).postAlbum(albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate
        )
    }}