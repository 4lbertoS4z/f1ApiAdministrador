package com.example.f1apiadministrator.ui.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1apiadministrator.api.ApiService
import com.example.f1apiadministrator.model.Driver
import com.example.f1apiadministrator.model.Team
import com.example.f1apiadministrator.settings.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamViewModel : ViewModel() {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val f1Api: ApiService = retrofit.create(ApiService::class.java)

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> = _teams
    fun getTeamsList() {
        f1Api.getTeams().enqueue(object : Callback<List<Team>> {
            override fun onResponse(call: Call<List<Team>>, response: Response<List<Team>>) {
                if (response.isSuccessful) {
                    val drivers = response.body() ?: emptyList()

                    // Asignar IDs personalizados a cada piloto
                    val teamsWithId = drivers.mapIndexed { index, team ->
                        team.id = index.toString()
                        team
                    }

                    _teams.value = teamsWithId
                } else {
                    Log.e("API error", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                Log.e("API error", "Error: ${t.message}")
            }
        })
    }
}
