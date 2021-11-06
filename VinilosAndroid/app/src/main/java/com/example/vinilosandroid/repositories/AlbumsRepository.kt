package com.example.vinilosandroid.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.network.NetworkServiceAdapter

class AlbumsRepository (val application: Application){
    fun refreshData(callback:(List<Album>)->Unit, onError:(VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }

}