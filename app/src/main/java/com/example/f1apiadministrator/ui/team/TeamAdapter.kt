package com.example.f1apiadministrator.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.f1apiadministrator.databinding.DriverListBinding
import com.example.f1apiadministrator.databinding.TeamListBinding
import com.example.f1apiadministrator.model.Driver
import com.example.f1apiadministrator.model.Team

class TeamAdapter(
    var teams: List<Team>,
    private val teamIdCounter: Int
) : RecyclerView.Adapter<TeamAdapter.TeamsViewHolder>() {
    var onItemClickListener: ((Team, String) -> Unit)? = null

    inner class TeamsViewHolder(private val binding: TeamListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val team = teams[position]
                    val teamId = (teamIdCounter + position).toString()
                    onItemClickListener?.invoke(team, teamId)
                }
            }
        }

        fun bind(team: Team) {
            binding.chiefTextView.text = "Jefe: \n${team.jefe}"
            binding.yearTextView.text = "Año entrada:\n ${team.año_entrada.toString()}"
            binding.championsWinTextView.text = "Campeonatos ganados: \n ${team.campeonatos_ganados.toString()}"
            Glide.with(binding.nationalityImageView.context).load(team.nacionalidad).into(binding.nationalityImageView)
            Glide.with(binding.imageView.context).load(team.image).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val binding = TeamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val driver = teams[position]
        holder.bind(driver)
    }

    override fun getItemCount() = teams.size
}