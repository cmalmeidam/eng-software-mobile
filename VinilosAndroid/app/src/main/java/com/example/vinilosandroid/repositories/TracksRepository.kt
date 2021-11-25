package com.example.vinilosandroid.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.vinilosandroid.models.Track
import com.example.vinilosandroid.network.CacheManager
import com.example.vinilosandroid.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.json.JSONArray

class TracksRepository (val application: Application){
    private val format = Json

    suspend fun refreshData(albumId: Int): List<Track>{
        var tracks = getTracks(albumId)
        return if(tracks.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                tracks = NetworkServiceAdapter.getInstance(application).getTracks(albumId)
                addTracks(albumId, tracks)
                tracks
            }
        } else tracks
    }


    suspend fun getTracks(albumId:Int): List<Track>{
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                return format.decodeFromString<List<Track>>(storedVal)
            }
        }
        return listOf<Track>()
    }
    suspend fun addTracks(albumId:Int, tracks: List<Track>){
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = format.encodeToString(tracks)
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }



    /*suspend fun postData(
        albumnName: String,
        albumCover: String,
        albumGenre: String,
        albumDescription: String,
        albumRecordLabel: String,
        albumDate: String
    ){
        NetworkServiceAdapter.getInstance(application).postAlbum(albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate
        )
    }*/
    }

