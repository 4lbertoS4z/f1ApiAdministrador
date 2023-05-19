package com.example.f1apiadministrator.ui.circuit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.f1apiadministrator.CircuitUpdateActivity
import com.example.f1apiadministrator.R

class CircuitActivity : AppCompatActivity() {
    private var circuitIdCounter = 0 // Variable para el contador de IDs
    private lateinit var swipeRefreshLayoutCircuit: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circuit)
        val circuitList = findViewById<RecyclerView>(R.id.circuitRecycler)

        circuitList.layoutManager = LinearLayoutManager(this)

        // Agrega el siguiente código para obtener una referencia al SwipeRefreshLayout
        swipeRefreshLayoutCircuit = findViewById(R.id.swipeRefreshCircuit)

        val viewModel = ViewModelProvider(this)[CircuitViewModel::class.java]
        viewModel.getCircuitsList()
        viewModel.circuits.observe(this) { circuits ->
            val adapter = CircuitAdapter(circuits, circuitIdCounter)
            adapter.onItemClickListener = { circuit, circuitId ->
                val intent = Intent(this, CircuitUpdateActivity::class.java)
                intent.putExtra("circuit", circuit)
                intent.putExtra("circuitId", circuitId)
                startActivity(intent)
            }
            circuitList.adapter = adapter
        }
        // Configura el OnRefreshListener para el SwipeRefreshLayout
        swipeRefreshLayoutCircuit.setOnRefreshListener {
            // Aquí llamas a la función para obtener la lista de pilotos nuevamente
            viewModel.getCircuitsList()

            // Detiene el efecto de actualización después de un breve retraso
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayoutCircuit.isRefreshing = false
            }, 1000) // Tiempo en milisegundos
        }
    }
}