package com.example.f1apiadministrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.f1apiadministrator.api.ApiService
import com.example.f1apiadministrator.databinding.ActivityTeamUpdateBinding
import com.example.f1apiadministrator.model.Driver

import com.example.f1apiadministrator.model.Team
import com.example.f1apiadministrator.settings.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamUpdateActivity : AppCompatActivity() {
    private lateinit var team: Team
    private lateinit var binding: ActivityTeamUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        team = intent.getSerializableExtra("team") as Team

        binding.txtPhoto.setText(team.image)
        binding.txtYear.setText(team.año_entrada.toString())
        binding.txtChief.setText(team.jefe)
        binding.txtWins.setText(team.campeonatos_ganados.toString())

        binding.btnUpdate.setOnClickListener {
            updateTeam()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun updateTeam(){
        val updateTeam = Team(
            jefe = binding.txtChief.text.toString(),
            image = binding.txtPhoto.text.toString(),
            campeonatos_ganados = binding.txtWins.text.toString().toInt(),
            año_entrada = binding.txtYear.text.toString().toInt(),
            nacionalidad = team.nacionalidad,
            equipo = team.equipo
        )
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = team.id?.let { apiService.updateTeam(it, updateTeam) }
        if (call != null) {
            call.enqueue(object : Callback<Team> {
                override fun onResponse(call: Call<Team>, response: Response<Team>) {
                    if (response.isSuccessful) {
                        // Actualización exit
                        val updateTeam = response.body()
                        Toast.makeText(
                            this@TeamUpdateActivity,
                            "Equipo actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Puedes realizar alguna acción adicional aquí si es necesario

                        finish() // Cierra la actividad de actualización y vuelve a la actividad anterior
                    } else {
                        // Error en la actualización
                        Toast.makeText(
                            this@TeamUpdateActivity,
                            "Error al actualizar el equipo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Team>, t: Throwable) {
                    // Error de conexión o solicitud fallida
                    Toast.makeText(
                        this@TeamUpdateActivity,
                        "Error en la solicitud de actualización",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}