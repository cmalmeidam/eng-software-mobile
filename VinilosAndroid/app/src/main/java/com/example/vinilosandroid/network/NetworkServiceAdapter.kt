package com.example.vinilosandroid.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.models.Musician
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.example.vinilosandroid.models.Track
import org.json.JSONException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter  constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://backvynils.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(getRequest("collectors",
            { response ->
                Log.d("tagb", response)
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                var item: JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    val collector = Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"))
                    list.add(i, collector)
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item : JSONObject?
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    val album = Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"))
                    list.add(i, album)
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    suspend fun getMusicians() = suspendCoroutine<List<Musician>>{ cont->
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    val musician = Musician(musicianId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate"))
                    list.add(i,musician)
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    suspend fun getTracks(albumId:Int) = suspendCoroutine<List<Track>>{ cont->
        requestQueue.add(getRequest("albums/$albumId/tracks",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Track>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    val track = Track(trackId = item.getInt("id"), name = item.getString("name"), duration = item.getString("duration"))
                    list.add(i,track)
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }
    /*fun postTrack(body: JSONObject, albumId: Int,  onComplete:(resp:JSONObject)->Unit , onError: (error: VolleyError)->Unit){
        requestQueue.add(postRequest("albums/$albumId/tracks",
            body,
            Response.Listener<JSONObject> { response ->
                onComplete(response)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }*/

    suspend fun postAlbum(
        albumName: String,
        albumCover: String,
        albumGenre: String,
        albumDescription: String,
        albumRecordLabel: String,
        albumDate: String
    ) = suspendCoroutine<String>{ cont->
        requestQueue.add(postRequest(
            { response ->
                JSONArray(response)
                cont.resume("success")
            },
            {
                cont.resumeWithException(it)
            },
            albumName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate
        ))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    private fun postRequest(
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener,
        albumName: String,
        albumCover: String,
        albumGenre: String,
        albumDescription: String,
        albumRecordLabel: String,
        albumDate: String
    ): JsonObjectRequest {
        val postUrl: String = BASE_URL + "albums"
        val postData = JSONObject()
        try {
            postData.put("name", albumName)
            postData.put("cover", albumCover)
            postData.put("releaseDate", albumDate)
            postData.put("description", albumDescription)
            postData.put("genre", albumGenre)
            postData.put("recordLabel", albumRecordLabel)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response -> println(response) }
        ) { error -> error.printStackTrace() }

        return jsonObjectRequest

    }

}