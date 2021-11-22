package com.example.vinilosandroid.models
import kotlinx.serialization.Serializable

@Serializable
class Musician (
    val musicianId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String
        )