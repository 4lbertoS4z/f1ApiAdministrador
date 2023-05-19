package com.example.f1apiadministrator.ui.circuit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1apiadministrator.api.ApiService
import com.example.f1apiadministrator.model.Circuit
import com.example.f1apiadministrator.settings.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CircuitViewModel : ViewModel() {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val f1Api: ApiService = retrofit.create(ApiService::class.java)

    private val _circuits = MutableLiveData<List<Circuit>>()
    val circuits: LiveData<List<Circuit>> = _circuits

    fun getCircuitsList() {
        f1Api.getCircuits().enqueue(object : Callback<List<Circuit>> {
            override fun onResponse(call: Call<List<Circuit>>, response: Response<List<Circuit>>) {
                if (response.isSuccessful) {
                    val drivers = response.body() ?: emptyList()

                    // Asignar IDs personalizados a cada circuit
                    val circuitsWithId = drivers.mapIndexed { index, circuit ->
                        circuit.id = index.toString()
                        circuit
                    }

                    _circuits.value = circuitsWithId
                } else {
                    Log.e("API error", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Circuit>>, t: Throwable) {
                Log.e("API error", "Error: ${t.message}")
            }
        })
    }
}