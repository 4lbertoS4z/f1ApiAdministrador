package com.example.f1apiadministrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.f1apiadministrator.api.ApiService
import com.example.f1apiadministrator.databinding.ActivityDriverUpdateBinding
import com.example.f1apiadministrator.model.Driver
import com.example.f1apiadministrator.settings.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DriverUpdateActivity : AppCompatActivity() {
    private lateinit var driver: Driver
    private lateinit var binding: ActivityDriverUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        driver = intent.getSerializableExtra("driver") as Driver

        // Mostrar los detalles del piloto en los campos de texto
        binding.txtName.setText(driver.nombre)
        binding.txtPhoto.setText(driver.image)
        binding.txtYear.setText(driver.edad.toString())
        binding.txtTeamName.setText(driver.escudería)
        binding.txtWins.setText(driver.victorias.toString())
        binding.txtPodiums.setText(driver.podios.toString())
        binding.txtPoles.setText(driver.poles.toString())

        binding.btnUpdate.setOnClickListener {
            updateDriver()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateDriver() {
        val updatedDriver = Driver(
            id = binding.txtId.text.toString(),
            edad = binding.txtYear.text.toString().toInt(),
            escudería = binding.txtTeamName.text.toString(),
            image = binding.txtPhoto.text.toString(),
            nacionalidad = driver.nacionalidad,
            nombre = binding.txtName.text.toString(),
            podios = binding.txtPodiums.text.toString().toInt(),
            poles = binding.txtPoles.text.toString().toInt(),
            victorias = binding.txtWins.text.toString().toInt()
        )

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = driver.id?.let { apiService.updateDriver(it, updatedDriver) }
        if (call != null) {
            call.enqueue(object : Callback<Driver> {
                override fun onResponse(call: Call<Driver>, response: Response<Driver>) {
                    if (response.isSuccessful) {
    // Actualización exit
                        val updatedDriver = response.body()
                        Toast.makeText(
                            this@DriverUpdateActivity,
                            "Piloto actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Puedes realizar alguna acción adicional aquí si es necesario

                        finish() // Cierra la actividad de actualización y vuelve a la actividad anterior
                    } else {
                        // Error en la actualización
                        Toast.makeText(
                            this@DriverUpdateActivity,
                            "Error al actualizar el piloto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Driver>, t: Throwable) {
                    // Error de conexión o solicitud fallida
                    Toast.makeText(
                        this@DriverUpdateActivity,
                        "Error en la solicitud de actualización",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}