package com.example.vinilosandroid.models
import kotlinx.serialization.Serializable

@Serializable
class Album   (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
    )