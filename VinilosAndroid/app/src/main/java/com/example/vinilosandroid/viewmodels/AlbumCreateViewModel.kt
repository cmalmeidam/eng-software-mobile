package com.example.vinilosandroid.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.repositories.AlbumsRepository
import kotlinx.coroutines.*

class AlbumCreateViewModel(application: Application) :  AndroidViewModel(application) {

    private val albumsRepository =AlbumsRepository(application)

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    fun inicio(album:Album) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    postDataFromNetwork(album)
                }
            }
        }catch (e: Exception) {
            println("job: I'm cancel catch init")}
    }
    suspend fun postDataFromNetwork(
      album:Album
    ) {
        try {
            var job = viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    albumsRepository.postData(
                        album
                    )
                }
            }
            _eventNetworkError.postValue(false)
            _isNetworkErrorShown.postValue(false)
            delay(1000)
            job.cancelAndJoin()
            println("job: I'm cancel try")
        } catch (e: Exception) {
            println("job: I'm cancel catchpost")
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
