package com.example.vinilosandroid.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosandroid.models.Track
import com.example.vinilosandroid.repositories.TracksRepository
import kotlinx.coroutines.*

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
    fun inicio(track: Track) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    postDataFromNetwork(track)
                }
            }
        }catch (e: Exception) {
            println("job: I'm cancel catch")}
    }

    suspend fun postDataFromNetwork(
        track: Track
    ) {
        try {
            var job = viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    tracksRepository.postData(
                        id, track
                    )
                }
            }
            _eventNetworkError.postValue(false)
            _isNetworkErrorShown.postValue(false)
            delay(1000)
            job.cancelAndJoin()
            println("job: I'm cancel try")
        } catch (e: Exception) {
            println("job: I'm cancel catch")
        }
    }

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

