package com.example.f1apiadministrator.api

import com.example.f1apiadministrator.model.Circuit
import com.example.f1apiadministrator.model.Driver
import com.example.f1apiadministrator.model.Team
import com.example.f1apiadministrator.settings.*

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET(DRIVER)
    fun getDrivers(): Call<List<Driver>>
    @PUT(DRIVER_UPDATE) // Reemplaza {id} con el ID del piloto que deseas actualizar
    fun updateDriver(@Path("id") id: String, @Body driver: Driver): Call<Driver>
    @GET(TEAM)
    fun getTeams():Call<List<Team>>
    @PUT(TEAM_UPDATE)
    fun updateTeam(@Path("id") id: String, @Body team: Team): Call<Team>
    @GET(CIRCUIT)
    fun getCircuits(): Call<List<Circuit>>
    @PUT(CIRCUIT_UPDATE)
    fun updateCircuit(@Path("id") id: String, @Body circuit: Circuit): Call<Circuit>

}