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
        val cm =
            application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
            var tracks = getTracks(albumId)
            return if (tracks.isNullOrEmpty()) {
                emptyList()
            } else {
                tracks
            }
        }
        else{
            var tracks = NetworkServiceAdapter.getInstance(application).getTracks(albumId)
            addTracks(albumId, tracks)
            return tracks
        }
    }

    suspend fun getTracks(albumId:Int): List<Track>{
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.TRACKS_SPREFS)
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
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.TRACKS_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = format.encodeToString(tracks)
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }

    suspend fun postData(
        albumId: Int,
        track:Track
    ){
        NetworkServiceAdapter.getInstance(application).postTrack(albumId, track)
    }
    }

