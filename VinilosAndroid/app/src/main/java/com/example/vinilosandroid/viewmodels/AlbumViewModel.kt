package com.example.vinilosandroid.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.repositories.AlbumsRepository
import kotlinx.coroutines.*

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
        inicio()
    }
    fun inicio() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    refreshDataFromNetwork()
                }
            }
        }catch (e: Exception) {
            println("job: I'm cancel catch")}
    }

    suspend fun refreshDataFromNetwork() {
        try {
            var job = viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshData()
                    _albums.postValue(data)
                }
            }
            _eventNetworkError.postValue(false)
            _isNetworkErrorShown.postValue(false)
            delay(2000)
            job.cancelAndJoin()
        }
        catch (e:Exception){
            println("job: I'm cancel catch")
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
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

