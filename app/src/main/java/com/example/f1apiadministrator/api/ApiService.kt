package com.example.f1apiadministrator.api

import com.example.f1apiadministrator.model.Driver
import com.example.f1apiadministrator.settings.DRIVER
import com.example.f1apiadministrator.settings.DRIVER_UPDATE

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET(DRIVER)
    fun getDrivers(): Call<List<Driver>>
    @PUT(DRIVER_UPDATE) // Reemplaza {id} con el ID del piloto que deseas actualizar
    fun updateDriver(@Path("id") id: String, @Body driver: Driver): Call<Driver>
}