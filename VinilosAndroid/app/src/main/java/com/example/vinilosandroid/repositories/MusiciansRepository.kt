package com.example.vinilosandroid.repositories

import android.app.Application
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.network.NetworkServiceAdapter

class MusiciansRepository (val application: Application){
    suspend fun refreshData(): List<Musician> {
        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }
}