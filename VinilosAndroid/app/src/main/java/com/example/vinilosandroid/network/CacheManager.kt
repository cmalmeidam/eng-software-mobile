package com.example.vinilosandroid.network
import android.content.Context
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
    }
    private var collectors: LruCache<Int,List<Collector>> = LruCache(3)
    private var musicians: LruCache<Int,List<Musician>> = LruCache(3)
    private var albums: LruCache<Int,List<Album>> = LruCache(3)
    fun addCollectors( key: Int, collector: List<Collector>) {
        if (collectors[key]==null) {
            collectors.put(key,collector)
        }
    }

    fun getCollectors(key: Int) : List<Collector> {
        return return if (collectors[key]!=null) collectors[key]!! else listOf<Collector>()
    }

    fun addMusicians( key: Int, musician: List<Musician>) {
        if (musicians[key]==null) {
            musicians.put(key,musician)
        }
    }

    fun getMusicians(key: Int) : List<Musician> {
        return return if (musicians[key]!=null) musicians[key]!! else listOf<Musician>()
    }
    fun addAlbums( key: Int, album: List<Album>) {
        if (albums[key]==null) {
            albums.put(key,album)
        }
    }

    fun getAlbums(key: Int) : List<Album> {
        return return if (albums[key]!=null) albums[key]!! else listOf<Album>()
    }
}
