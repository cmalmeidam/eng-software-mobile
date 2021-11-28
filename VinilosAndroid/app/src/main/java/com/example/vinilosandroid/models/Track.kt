package com.example.vinilosandroid.models
import kotlinx.serialization.Serializable

@Serializable
class Track (
    val trackId:Int,
    val name:String,
    val duration:String
)
