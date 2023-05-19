package com.example.f1apiadministrator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.f1apiadministrator.databinding.ActivityMainBinding
import com.example.f1apiadministrator.ui.circuit.CircuitActivity
import com.example.f1apiadministrator.ui.driver.DriverActivity
import com.example.f1apiadministrator.ui.team.TeamActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPilotos.setOnClickListener {
            val intent = Intent(this, DriverActivity::class.java)
            startActivity(intent)
        }
        binding.buttonEquipos.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCircuitos.setOnClickListener {
            val intent = Intent(this, CircuitActivity::class.java)
            startActivity(intent)
        }
    }
}