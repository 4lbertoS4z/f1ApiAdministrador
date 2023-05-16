package com.example.f1apiadministrator.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.f1apiadministrator.R
import com.example.f1apiadministrator.databinding.DriverListBinding
import com.example.f1apiadministrator.model.Driver

class DriversAdapter(
    var drivers: List<Driver>,
    private val pilotoIdCounter: Int
) : RecyclerView.Adapter<DriversAdapter.DriversViewHolder>() {
    var onItemClickListener: ((Driver, String) -> Unit)? = null

    inner class DriversViewHolder(private val binding: DriverListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val driver = drivers[position]
                    val pilotoId = (pilotoIdCounter + position).toString()
                    onItemClickListener?.invoke(driver, pilotoId)
                }
            }
        }

        fun bind(driver: Driver) {
            binding.txtId.text = "Id: \n${driver.id}"
            binding.nameTextView.text = "Nombre: \n${driver.nombre}"
            binding.equipTextView.text = "Escuderia: \n${driver.escudería}"
            binding.yearTextView.text = "Edad:\n ${driver.edad.toString()}"
            binding.winsTextView.text = "Victorias: \n ${driver.victorias.toString()}"
            binding.podiumTextView.text = "Podios: \n${driver.podios.toString()}"
            binding.poleTextView.text = "Poles: \n${driver.poles.toString()}"
            Glide.with(binding.nationalityImageView.context).load(driver.nacionalidad).into(binding.nationalityImageView)
            Glide.with(binding.imageView.context).load(driver.image).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        val binding = DriverListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriversViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) {
        val driver = drivers[position]
        holder.bind(driver)
    }

    override fun getItemCount() = drivers.size
}