package com.example.vinilosandroid.network
import android.content.Context
import android.content.SharedPreferences
import android.util.LruCache
import android.util.SparseArray
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.models.Musician

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
            const val APP_SPREFS = "com.example.vinilosandroid.app"
            const val ALBUMS_SPREFS = "com.example.vinilosandroid.app.albums"
            const val MUSICIANS_SPREFS = "com.example.vinilosandroid.app.musicians"
            const val COLLECTORS_SPREFS = "com.example.vinilosandroid.app.collectors"
            fun getPrefs(context: Context, name:String): SharedPreferences {
                return context.getSharedPreferences(name,
                    Context.MODE_PRIVATE
                )
            }
    }
    private var collectors: ArrayMap<Int,List<Collector>> = arrayMapOf()
    private var musicians: ArrayMap<Int,List<Musician>> = arrayMapOf()
    private var albums: ArrayMap<Int,List<Album>> = arrayMapOf()
    fun addCollectors( key: Int, collector: List<Collector>) {
        if (!collectors.containsKey(key)){
            collectors[key] = collector
        }
    }

    fun getCollectors(key: Int) : List<Collector> {
        return return if (collectors.containsKey(key)) collectors[key]!! else listOf<Collector>()
    }

    fun addMusicians( key: Int, musician: List<Musician>) {
        if (musicians.containsKey(key)) {
            musicians[key] = musician
        }
    }

    fun getMusicians(key: Int) : List<Musician> {
        return return if (musicians.containsKey(key)) musicians[key]!! else listOf<Musician>()
    }
    fun addAlbums( key: Int, album: List<Album>) {
        if (albums.containsKey(key)) {
            albums[key] = album
        }
    }

    fun getAlbums(key: Int) : List<Album> {
        return return if (albums.containsKey(key)) albums[key]!! else listOf<Album>()
    }
}
