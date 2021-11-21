package com.example.vinilosandroid.repositories

import android.app.Application
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.network.NetworkServiceAdapter

class AlbumsRepository (val application: Application){
    suspend fun refreshData(): List<Album>{
        return NetworkServiceAdapter.getInstance(application).getAlbums()
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