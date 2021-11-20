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

import org.json.JSONException




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

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error: VolleyError)->Unit) {
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                Log.d("tagb", response)
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit) {
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
    fun getMusicians(onComplete:(resp:List<Musician>)->Unit, onError: (error: VolleyError)->Unit) {
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Musician(musicianId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun postAlbum(onComplete:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit) {
        requestQueue.add(postRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)

            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    private fun postRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): JsonObjectRequest {
        val postUrl: String = BASE_URL + path

        val postData = JSONObject()
        try {
            postData.put("name", "Test")
            postData.put("cover", "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg")
            postData.put("releaseDate", "1984-08-01T00:00:00-05:00")
            postData.put("description", "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.")
            postData.put("genre", "Salsa")
            postData.put("recordLabel", "Elektra")
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