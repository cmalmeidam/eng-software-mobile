package com.example.vinilosandroid.viewmodels

import android.app.Activity
import android.app.Application
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.vinilosandroid.databinding.FragmentCreateAlbumBinding
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.repositories.AlbumsRepository
import com.example.vinilosandroid.ui.CreateAlbumFragment
import com.example.vinilosandroid.ui.CreateAlbumFragmentDirections
import java.lang.Exception
import java.text.SimpleDateFormat

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<Album>>()
    private val albumsRepository =AlbumsRepository(application)
    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
//        postDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        albumsRepository.refreshData({
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    fun postDataFromNetwork(
        application: Application,
        albumnName: String,
        albumCover: String,
        albumGenre: String,
        albumDescription: String,
        albumRecordLabel: String,
        albumDate: String
    ) {
        val albumsRepository =AlbumsRepository(application)
        albumsRepository.postData({

        },{
        }, albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate)
    }
    fun processInformation(_binding : FragmentCreateAlbumBinding, activity: Activity){
        if(!TextUtils.isEmpty(_binding!!.albumname.text) && !TextUtils.isEmpty(_binding!!.albumcover.text) && !TextUtils.isEmpty(_binding!!.albumdescription.text) && !TextUtils.isEmpty(_binding!!.albumReleaseDate.text)) {
            try {
                val isoDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

                val albumnName = _binding!!.albumname.text.toString()
                val albumCover = _binding!!.albumcover.text.toString()
                val albumGenre = _binding!!.genreSelector.selectedItem.toString()
                val albumDescription = _binding!!.albumdescription.text.toString()
                val albumRecordLabel = _binding!!.recordLabelSelector.selectedItem.toString()
                var albumDate = isoDate.format(SimpleDateFormat("dd/MM/yyyy").parse(_binding!!.albumReleaseDate.text.toString()))
                postDataFromNetwork(activity.application, albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate)
                val action = CreateAlbumFragmentDirections.actionCreateAlbumToAlbumDetailFragment(
                    0,
                    albumDescription,
                    albumCover,
                    albumGenre,
                    albumnName,
                    albumRecordLabel,
                    albumDate)
                _binding!!.crearAlbumBtn.findNavController().navigate(action)
            } catch (e: Exception) {
                Toast.makeText(activity,e.message, Toast.LENGTH_LONG).show()
                println(e)
            }
        } else {
            if(TextUtils.isEmpty(_binding!!.albumname.text)) {
                Toast.makeText(activity,"El nombre no puede estar vacio", Toast.LENGTH_LONG).show()
            }

            if(TextUtils.isEmpty(_binding!!.albumcover.text)) {
                Toast.makeText(activity,"La url de la portada del album no puede estar vacia", Toast.LENGTH_LONG).show()
            }

            if(TextUtils.isEmpty(_binding!!.albumdescription.text)) {
                Toast.makeText(activity,"La descripcion no puede estar vacia", Toast.LENGTH_LONG).show()
            }

            if(TextUtils.isEmpty(_binding!!.albumReleaseDate.text)) {
                Toast.makeText(activity,"La fecha no puede estar vacia", Toast.LENGTH_LONG).show()
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

