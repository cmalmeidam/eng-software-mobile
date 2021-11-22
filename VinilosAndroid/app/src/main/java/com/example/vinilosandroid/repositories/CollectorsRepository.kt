package com.example.vinilosandroid.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CollectorsRepository (val application: Application){
    private var key = 1
   suspend fun refreshData(): List<Collector>{
       var collectors = getCollectors()
       return if(collectors.isNullOrEmpty()){
           val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
               emptyList()
           } else {
               collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
               addCollectors(key, collectors)
               collectors
           }
       } else collectors
   }
    fun getCollectors(): List<Collector>{
        val format = Json {  }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.COLLECTORS_SPREFS)
        if(prefs.contains(key.toString())){
            val storedVal = prefs.getString(key.toString(), "")
            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<Collector>>(storedVal)
            }
        }
        return listOf<Collector>()
    }
    fun addCollectors(key:Int, collectors: List<Collector>){
        val format = Json {  }
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.COLLECTORS_SPREFS)
        if(!prefs.contains(key.toString())){
            var store = format.encodeToString(collectors)
            with(prefs.edit(),{
                putString(key.toString(), store)
                apply()
            })
        }
    }
}