package com.example.vinilosandroid.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter


class CollectorsRepository (val application: Application){
    suspend fun refreshData(): List<Collector> {
        var key = 1
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors(key)
        if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            var collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(key,collectors)
            return collectors
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }
}