package com.example.f1apiadministrator.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f1apiadministrator.DriverUpdateActivity
import com.example.f1apiadministrator.R


class DriverActivity : AppCompatActivity() {
    private var pilotoIdCounter = 0 // Variable para el contador de IDs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        val spellList = findViewById<RecyclerView>(R.id.teamRecycler)
        spellList.layoutManager = LinearLayoutManager(this)

        val viewModel = ViewModelProvider(this)[DriversViewModel::class.java]
        viewModel.getDriversList()
        viewModel.drivers.observe(this) { drivers ->
            val adapter = DriversAdapter(drivers, pilotoIdCounter)
            adapter.onItemClickListener = { driver, pilotoId ->
                val intent = Intent(this, DriverUpdateActivity::class.java)
                intent.putExtra("driver", driver)
                intent.putExtra("driverId", pilotoId)
                startActivity(intent)
            }
            spellList.adapter = adapter
        }
    }
}