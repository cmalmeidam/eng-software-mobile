package com.example.vinilosandroid.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter

class MusiciansRepository (val application: Application) {
    suspend fun refreshData(): List<Musician> {
        var key = 2
        var potentialResp = CacheManager.getInstance(application.applicationContext).getMusicians(key)
        if (potentialResp.isEmpty()) {
            Log.d("Cache decision", "get from network")
            var musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
            CacheManager.getInstance(application.applicationContext).addMusicians(key,musicians)
            return musicians
        } else {
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }
}