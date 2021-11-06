package com.example.vinilosandroid.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.network.NetworkServiceAdapter


class CollectorsRepository (val application: Application){
    fun refreshData(callback:(List<Collector>)->Unit, onError:(VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
        onError
        )
    }

}