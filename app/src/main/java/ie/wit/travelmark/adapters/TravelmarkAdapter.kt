package ie.wit.travelmark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.travelmark.databinding.CardTravelmarkBinding
import ie.wit.travelmark.models.TravelmarkModel

interface TravelmarkListener {
    fun onTravelmarkClick(travelmark: TravelmarkModel)
}

class TravelmarkAdapter constructor(private var travelmarks: MutableList<TravelmarkModel>,
                                    private val listener: TravelmarkListener) :
            RecyclerView.Adapter<TravelmarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTravelmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val travelmark = travelmarks[holder.adapterPosition]
        holder.bind(travelmark, listener)
    }

    override fun getItemCount(): Int = travelmarks.size

    fun removeAt(position: Int) {
        travelmarks.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder(private val binding : CardTravelmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(travelmark: TravelmarkModel, listener: TravelmarkListener) {
            binding.travelmarkLocation.text = travelmark.location
            binding.travelmarkTitle.text = travelmark.title
            binding.travelmarkDescription.text = travelmark.description
            if (travelmark.image != "") {
                Picasso.get().load(travelmark.image).resize(250, 250).into(binding.imageIcon)
            }
            binding.travelmarkCategory.text = travelmark.category
            binding.cardTravelmarkRating.rating = travelmark.rating
            if (travelmark.favourite) {
                binding.imagefavourite.setVisibility(View.VISIBLE)
            } else {
                binding.imagefavourite.setVisibility(View.GONE)
            }
            binding.root.setOnClickListener { listener.onTravelmarkClick(travelmark) }
        }
    }
}