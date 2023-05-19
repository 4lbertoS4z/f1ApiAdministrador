package com.example.f1apiadministrator.model

import java.io.Serializable

class Team(
    var id: String? = null,
    val equipo: String,
    val nacionalidad: String,
    val año_entrada: Int,
    val campeonatos_ganados: Int,
    val image: String,
    val jefe: String
) : Serializable
