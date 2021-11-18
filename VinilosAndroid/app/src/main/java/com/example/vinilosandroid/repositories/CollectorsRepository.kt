package com.example.vinilosandroid.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.network.NetworkServiceAdapter


class CollectorsRepository (val application: Application){
    suspend fun refreshData(): List<Collector>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

}