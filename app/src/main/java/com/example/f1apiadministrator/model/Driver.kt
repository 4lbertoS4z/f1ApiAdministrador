package com.example.f1apiadministrator.model

import java.io.Serializable

class Driver(
    var id: String? = null,
    val edad: Int,
    val escudería: String,
    val image: String,
    val nacionalidad: String,
    val nombre: String,
    val podios: Int,
    val poles: Int,
    val victorias: Int
) : Serializable
