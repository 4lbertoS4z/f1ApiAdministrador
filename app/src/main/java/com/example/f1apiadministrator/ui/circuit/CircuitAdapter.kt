package com.example.f1apiadministrator.ui.circuit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.f1apiadministrator.databinding.CircuitListBinding
import com.example.f1apiadministrator.model.Circuit

class CircuitAdapter(
    var circuits: List<Circuit>,
    private val circuitIdCounter: Int
) : RecyclerView.Adapter<CircuitAdapter.CircuitViewHolder>(){
    var onItemClickListener: ((Circuit, String) -> Unit)? = null

    inner class CircuitViewHolder(private val binding: CircuitListBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.imageView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val circuit = circuits[position]
                    val circuitId = (circuitIdCounter + position).toString()
                    onItemClickListener?.invoke(circuit,circuitId)
                }
            }
        }
        fun bind(circuit: Circuit){

            binding.nameCircuitTextView.text = "Nombre: \n${circuit.nombre}"
            binding.lapsTextView.text = "Nº vueltas: \n${circuit.vueltas.toString()}"
            binding.distenceTextView.text = "Longitud:\n ${circuit.longitud.toString()}"
            binding.dateTextView.text = "Fechas: \n ${circuit.fechas}"
            binding.lastWinnerTextView.text = "Último ganador: \n${circuit.ultimo_ganador}"
            Glide.with(binding.imageView.context).load(circuit.image).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitViewHolder {
        val binding = CircuitListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  CircuitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CircuitViewHolder, position: Int) {
        val circuit = circuits[position]
        holder.bind(circuit)
    }

    override fun getItemCount() = circuits.size
}