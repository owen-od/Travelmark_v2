package ie.wit.travelmark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.travelmark.databinding.CardTravelmarkBinding
import ie.wit.travelmark.models.TravelmarkModel

class TravelmarkAdapter constructor(private var travelmarks: List<TravelmarkModel>) :
            RecyclerView.Adapter<TravelmarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTravelmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val travelmark = travelmarks[holder.adapterPosition]
        holder.bind(travelmark)
    }

    override fun getItemCount(): Int = travelmarks.size

    class MainHolder(private val binding : CardTravelmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(travelmark: TravelmarkModel) {
            binding.travelmarkLocation.text = travelmark.location
            binding.travelmarkTitle.text = travelmark.title
            binding.travelmarkDescription.text = travelmark.description
        }
    }
}