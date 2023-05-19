package com.example.f1apiadministrator.ui.team

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
import com.example.f1apiadministrator.TeamUpdateActivity
import com.example.f1apiadministrator.model.Team
import com.example.f1apiadministrator.ui.circuit.CircuitAdapter
import com.example.f1apiadministrator.ui.circuit.CircuitViewModel

class TeamActivity : AppCompatActivity() {
    private var teamIdCounter = 0 // Variable para el contador de IDs
    private lateinit var swipeRefreshLayoutTeam: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        val teamList = findViewById<RecyclerView>(R.id.teamRecycler)
        teamList.layoutManager = LinearLayoutManager(this)

        // Agrega el siguiente código para obtener una referencia al SwipeRefreshLayout
        swipeRefreshLayoutTeam = findViewById(R.id.swipeRefreshTeam)

        val viewModel = ViewModelProvider(this)[TeamViewModel::class.java]
        viewModel.getTeamsList()
        viewModel.teams.observe(this) { teams ->
            val adapter = TeamAdapter(teams, teamIdCounter)
            adapter.onItemClickListener = { team, teamId ->
                val intent = Intent(this, TeamUpdateActivity::class.java)
                intent.putExtra("team", team)
                intent.putExtra("teamId", teamId)
                startActivity(intent)
            }
             teamList.adapter = adapter
        }
        // Configura el OnRefreshListener para el SwipeRefreshLayout
        swipeRefreshLayoutTeam.setOnRefreshListener {
            // Aquí llamas a la función para obtener la lista de pilotos nuevamente
            viewModel.getTeamsList()
            // Detiene el efecto de actualización después de un breve retraso
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayoutTeam.isRefreshing = false
            }, 1000) // Tiempo en milisegundos
        }
    }
}