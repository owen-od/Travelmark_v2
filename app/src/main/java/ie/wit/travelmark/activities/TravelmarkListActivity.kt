package ie.wit.travelmark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.travelmark.R
import ie.wit.travelmark.adapters.TravelmarkAdapter
import ie.wit.travelmark.adapters.TravelmarkListener
import ie.wit.travelmark.databinding.ActivityTravelmarkListBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel

class TravelmarkListActivity : AppCompatActivity(), TravelmarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTravelmarkListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerRefreshCallback()

        app = application as MainApp

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadTravelmarks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, TravelmarkActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTravelmarkClick(travelmark: TravelmarkModel) {
        val launcherIntent = Intent(this, TravelmarkActivity::class.java)
        launcherIntent.putExtra("travelmark_edit", travelmark)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadTravelmarks() }
    }

    private fun loadTravelmarks() {
        showTravelmarks(app.travelmarks.findAll())
    }

    fun showTravelmarks (travelmarks: List<TravelmarkModel>) {
        binding.recyclerView.adapter = TravelmarkAdapter(travelmarks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
