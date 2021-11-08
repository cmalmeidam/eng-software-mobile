package com.example.vinilosandroid.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.network.NetworkServiceAdapter

class MusiciansRepository (val application: Application){
    fun refreshData(callback:(List<Musician>)->Unit, onError:(VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getMusicians({
            callback(it)
        },
            onError
        )
    }
}