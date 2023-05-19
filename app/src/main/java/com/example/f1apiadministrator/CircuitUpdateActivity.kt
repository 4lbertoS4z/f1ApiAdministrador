package com.example.f1apiadministrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.f1apiadministrator.api.ApiService
import com.example.f1apiadministrator.databinding.ActivityCircuitUpdateBinding
import com.example.f1apiadministrator.model.Circuit
import com.example.f1apiadministrator.model.Driver
import com.example.f1apiadministrator.settings.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CircuitUpdateActivity : AppCompatActivity() {
    private lateinit var circuit: Circuit
    private lateinit var binding: ActivityCircuitUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircuitUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        circuit = intent.getSerializableExtra("circuit") as Circuit

        binding.txtName.setText(circuit.nombre)
        binding.txtPhoto.setText(circuit.image)
        binding.txtDates.setText(circuit.fechas)
        binding.txtLaps.setText(circuit.vueltas)
        binding.txtLastWinner.setText(circuit.ultimo_ganador)
        binding.txtDistance.setText(circuit.longitud)

        binding.btnUpdate.setOnClickListener {
            updateCircuit()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateCircuit(){
        val updateCircuit = Circuit(
            fechas = binding.txtDates.text.toString() ,
            image = binding.txtPhoto.text.toString(),
            longitud = binding.txtDistance.text.toString(),
            ultimo_ganador = binding.txtLastWinner.text.toString(),
            vueltas = binding.txtLaps.text.toString(),
            nombre = binding.txtName.text.toString()
        )
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService = retrofit.create(ApiService::class.java)
        val call = circuit.id?.let { apiService.updateCircuit(it, updateCircuit) }
        if (call != null) {
            call.enqueue(object : Callback<Circuit> {
                override fun onResponse(call: Call<Circuit>, response: Response<Circuit>) {
                    if (response.isSuccessful) {
                        // Actualización exit
                        val updateCircuit = response.body()
                        Toast.makeText(
                            this@CircuitUpdateActivity,
                            "Circuito actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Puedes realizar alguna acción adicional aquí si es necesario

                        finish() // Cierra la actividad de actualización y vuelve a la actividad anterior
                    } else {
                        // Error en la actualización
                        Toast.makeText(
                            this@CircuitUpdateActivity,
                            "Error al actualizar el circuito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Circuit>, t: Throwable) {
                    // Error de conexión o solicitud fallida
                    Toast.makeText(
                        this@CircuitUpdateActivity,
                        "Error en la solicitud de actualización",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}