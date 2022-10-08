package ie.wit.travelmark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.travelmark.databinding.ActivityTravelmarkListBinding
import ie.wit.travelmark.databinding.CardTravelmarkBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel

class TravelmarkListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTravelmarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TravelmarkAdapter(app.travelmarks)
    }
}

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
            binding.travelmarkTitle.text = travelmark.title
            binding.travelmarkDescription.text = travelmark.description
        }
    }
}