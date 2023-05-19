package com.example.f1apiadministrator.model

import java.io.Serializable

class Circuit(
    var id: String? = null,
    val fechas: String,
    val image: String,
    val longitud: String,
    val nombre: String,
    val ultimo_ganador: String,
    val vueltas: String
) : Serializable