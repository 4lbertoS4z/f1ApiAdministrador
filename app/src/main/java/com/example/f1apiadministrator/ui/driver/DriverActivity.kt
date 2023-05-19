package com.example.f1apiadministrator.ui.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.f1apiadministrator.DriverUpdateActivity
import com.example.f1apiadministrator.R


class DriverActivity : AppCompatActivity() {
    private var pilotoIdCounter = 0 // Variable para el contador de IDs
    private lateinit var swipeRefreshLayoutDriver: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        val driverList = findViewById<RecyclerView>(R.id.driverRecycler)
        driverList.layoutManager = LinearLayoutManager(this)

        // Agrega el siguiente código para obtener una referencia al SwipeRefreshLayout
        swipeRefreshLayoutDriver = findViewById(R.id.swipeRefresh)

        val viewModel = ViewModelProvider(this)[DriversViewModel::class.java]
        viewModel.getDriversList()
        viewModel.drivers.observe(this) { drivers ->
            val adapter = DriversAdapter(drivers, pilotoIdCounter)
            adapter.onItemClickListener = { driver, driverId ->
                val intent = Intent(this, DriverUpdateActivity::class.java)
                intent.putExtra("driver", driver)
                intent.putExtra("driverId", driverId)
                startActivity(intent)
            }
            driverList.adapter = adapter
        }
        // Configura el OnRefreshListener para el SwipeRefreshLayout
        swipeRefreshLayoutDriver.setOnRefreshListener {
            // Aquí llamas a la función para obtener la lista de pilotos nuevamente
            viewModel.getDriversList()

            // Detiene el efecto de actualización después de un breve retraso
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayoutDriver.isRefreshing = false
            }, 1000) // Tiempo en milisegundos
        }
    }
}