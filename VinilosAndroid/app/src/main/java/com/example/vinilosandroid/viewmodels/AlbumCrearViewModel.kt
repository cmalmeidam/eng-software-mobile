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
import com.example.vinilosandroid.ui.CreateAlbumFragmentDirections
import java.lang.Exception
import java.text.SimpleDateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*class AlbumCrearViewModel(application: Application) :  AndroidViewModel(application) {

    private val _album = MutableLiveData<Album>()
    private val albumsRepository =AlbumsRepository(application)
    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        postDataFromNetwork(
            application: Application,
            albumnName: String,
            albumCover: String,
            albumGenre: String,
            albumDescription: String,
            albumRecordLabel: String,
            albumDate: String)
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
        try {
            val albumsRepository = AlbumsRepository(application)
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    albumsRepository.postData(
                        albumnName,
                        albumCover,
                        albumGenre,
                        albumDescription,
                        albumRecordLabel,
                        albumDate
                    )
                }
            }
        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
    }
    fun processInformation(_binding : FragmentCreateAlbumBinding, activity: Activity){
        if(!TextUtils.isEmpty(_binding!!.albumname.text) && !TextUtils.isEmpty(_binding!!.albumcover.text) && !TextUtils.isEmpty(_binding!!.albumdescription.text) && !TextUtils.isEmpty(_binding!!.albumReleaseDate.text)) {
            try {
                val albumnName = _binding!!.albumname.text.toString()
                val albumCover = _binding!!.albumcover.text.toString()
                val albumGenre = _binding!!.genreSelector.selectedItem.toString()
                val albumDescription = _binding!!.albumdescription.text.toString()
                val albumRecordLabel = _binding!!.recordLabelSelector.selectedItem.toString()
                var albumDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(SimpleDateFormat("dd/MM/yyyy").parse(_binding!!.albumReleaseDate.text.toString()))
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
            when(TextUtils.isEmpty(_binding!!.albumname.text) || TextUtils.isEmpty(_binding!!.albumcover.text) || TextUtils.isEmpty(_binding!!.albumdescription.text) || TextUtils.isEmpty(_binding!!.albumReleaseDate.text)){
                TextUtils.isEmpty(_binding!!.albumname.text) -> Toast.makeText(activity,"El nombre no puede estar vacio", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(_binding!!.albumReleaseDate.text) -> Toast.makeText(activity,"La fecha no puede estar vacia", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(_binding!!.albumcover.text) -> Toast.makeText(activity,"La url de la portada del album no puede estar vacia", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(_binding!!.albumdescription.text) -> Toast.makeText(activity,"La descripcion no puede estar vacia", Toast.LENGTH_LONG).show()

            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCrearViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCrearViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
*/
