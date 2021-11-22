package com.example.vinilosandroid.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MusiciansRepository (val application: Application){
    private var key = 2
    suspend fun refreshData(): List<Musician>{
        var musicians = getMusicians()
        return if(musicians.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
                addMusicians(key, musicians)
                musicians
            }
        } else musicians
    }
    fun getMusicians(): List<Musician>{
        val format = Json {  }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.MUSICIANS_SPREFS)
        if(prefs.contains(key.toString())){
            val storedVal = prefs.getString(key.toString(), "")
            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<Musician>>(storedVal)
            }
        }
        return listOf<Musician>()
    }
    fun addMusicians(key:Int, musicians: List<Musician>){
        val format = Json {  }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.MUSICIANS_SPREFS)
        if(!prefs.contains(key.toString())){
            var store = format.encodeToString(musicians)
            with(prefs.edit(),{
                putString(key.toString(), store)
                apply()
            })
        }
    }
}