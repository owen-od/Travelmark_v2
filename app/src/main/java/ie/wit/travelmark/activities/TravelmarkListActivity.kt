package ie.wit.travelmark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.travelmark.R
import ie.wit.travelmark.adapters.TravelmarkAdapter
import ie.wit.travelmark.adapters.TravelmarkListener
import ie.wit.travelmark.databinding.ActivityTravelmarkListBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber
import timber.log.Timber.i

class TravelmarkListActivity : AppCompatActivity(), TravelmarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTravelmarkListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerRefreshCallback()
        registerMapCallback()

        app = application as MainApp

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadTravelmarks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        // get menu item
        val searchItem: MenuItem = menu.findItem(R.id.search)
        // getting search view of menu item and set query hint
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setQueryHint(getString(R.string.search_hint))

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filter(query)
                //i(query)
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                //i(msg)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, TravelmarkActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, TravelmarkMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
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

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

    private fun loadTravelmarks() {
        showTravelmarks(app.travelmarks.findAll())
    }

    fun showTravelmarks (travelmarks: List<TravelmarkModel>) {
        binding.recyclerView.adapter = TravelmarkAdapter(travelmarks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun filter(text: String) {
        val filteredlist: MutableList<TravelmarkModel> = mutableListOf()

        for (item in app.travelmarks.findAll()) {
            if (item.location.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item)
                // i("item added to list")
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            loadTravelmarks()
        } else {
            binding.recyclerView.adapter = TravelmarkAdapter(filteredlist, this)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
