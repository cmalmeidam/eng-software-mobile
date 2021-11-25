package com.example.vinilosandroid.viewmodels

import android.app.Activity
import android.app.Application
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.vinilosandroid.databinding.FragmentCreateAlbumBinding
import com.example.vinilosandroid.databinding.TrackFragmentBinding
import com.example.vinilosandroid.models.Track
import com.example.vinilosandroid.repositories.TracksRepository
import com.example.vinilosandroid.ui.CreateAlbumFragmentDirections
import com.example.vinilosandroid.ui.TrackFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat

class TrackViewModel (application: Application, albumId:Int) :  AndroidViewModel(application) {

    private val _tracks = MutableLiveData<List<Track>>()

    private val tracksRepository = TracksRepository(application)

    val tracks: LiveData<List<Track>>
        get() = _tracks

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    val id:Int =albumId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = tracksRepository.refreshData(id)
                    _tracks.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e: Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    fun processInformation(_binding: TrackFragmentBinding, activity: Activity) {
        when (true){
            TextUtils.isEmpty(_binding!!.txtTrackName.text) -> Toast.makeText(activity,"El nombre no puede estar vacio", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(_binding!!.txtSegundos.text) && TextUtils.isEmpty(_binding!!.txtMinutos.text) -> Toast.makeText(activity,"El track debe durar más de 0 segundos",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(_binding!!.txtMinutos.text) -> Toast.makeText(activity, "Si el track dura menos de un minuto poner 00", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(_binding!!.txtSegundos.text) -> Toast.makeText(activity, "Si el track dura 0 segundos poner 00", Toast.LENGTH_LONG).show()
            (_binding!!.txtMinutos.text.toString().toInt()) > 59 -> Toast.makeText(activity, "Los minutos no pueden exceder de 59", Toast.LENGTH_LONG).show()
            (_binding!!.txtSegundos.text.toString().toInt()) > 59 -> Toast.makeText(activity, "Los segundos no pueden exceder de 59", Toast.LENGTH_LONG).show()
            !TextUtils.isEmpty(_binding!!.txtTrackName.text) && !TextUtils.isEmpty(_binding!!.txtMinutos.text) && !TextUtils.isEmpty(_binding!!.txtSegundos.text) ->
                try {
                    val track = Track(
                        0,
                        _binding.txtTrackName.text.toString(),
                        (_binding!!.txtMinutos.text.toString() + ":" + _binding!!.txtSegundos.text.toString())
                    )
                    println("entra al track")
                    println(track.name)
                    /*postDataFromNetwork(activity.application, track)*/
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    println(e)
                }
        }
    }

    /*fun postDataFromNetwork(
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
    }*/

    class Factory(val app: Application, val albumId:Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

