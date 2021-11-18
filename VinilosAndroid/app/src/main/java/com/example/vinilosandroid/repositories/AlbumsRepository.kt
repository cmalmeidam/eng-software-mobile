package com.example.vinilosandroid.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.network.NetworkServiceAdapter

class AlbumsRepository (val application: Application){
    suspend fun refreshData(): List<Album>{
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }
}