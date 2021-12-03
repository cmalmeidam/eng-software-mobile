package com.example.vinilosandroid.viewmodels

import android.app.Application
import android.os.Build
import androidx.lifecycle.*
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.repositories.MusiciansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MusicianViewModel(application: Application) :  AndroidViewModel(application) {

    private val musiciansRepository = MusiciansRepository(application)
    private val _musicians = MutableLiveData<List<Musician>>()

    val musicians: LiveData<List<Musician>>
        get() = _musicians

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = musiciansRepository.refreshData()
                    _musicians.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicianViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicianViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
    fun formateandoDate(birthDate:String): String {
        var finalDate: String?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var odt = OffsetDateTime.parse(birthDate)
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
            finalDate = formatter.format(odt)
        } else
        {
            val originalFormat: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val targetFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val originDate: Date = originalFormat.parse(birthDate)
            finalDate = targetFormat.format(originDate)
        }
        return finalDate
    }
}