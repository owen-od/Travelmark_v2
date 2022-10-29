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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerRefreshCallback()
        registerMapCallback()

        app = application as MainApp

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        binding.chipOptions.check(R.id.chip_option_all)

        binding.chipOptions.setOnCheckedChangeListener(object: ChipGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(chipGroup: ChipGroup, id: Int) {
                var selectedChip = chipGroup.findViewById(id) as Chip
                var chipCategory = when (selectedChip.id) {
                    R.id.chip_option_see -> "Sight to see"
                    R.id.chip_option_do -> "Thing to do"
                    R.id.chip_option_eat -> "Food to eat"
                    else -> "All"
                }
                i(chipCategory)
                filterCategory(chipCategory)
            }
        })

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

        // call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val checkedChip = binding.chipOptions.checkedChipId
                var chipCategory = when (checkedChip) {
                    R.id.chip_option_see -> "Sight to see"
                    R.id.chip_option_do -> "Thing to do"
                    R.id.chip_option_eat -> "Food to eat"
                    else -> "All"
                }
                filter(query, chipCategory)
                //i(query)
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                val checkedChip = binding.chipOptions.checkedChipId
                var chipCategory = when (checkedChip) {
                    R.id.chip_option_see -> "Sight to see"
                    R.id.chip_option_do -> "Thing to do"
                    R.id.chip_option_eat -> "Food to eat"
                    else -> "All"
                }
                filter(msg, chipCategory)
                //i(query)
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

    private fun filter(text: String, category: String) {
        val filteredlist: MutableList<TravelmarkModel> = mutableListOf()
        val travelmarks = app.travelmarks.findTravelmarksByCategory(category)

        for (item in travelmarks) {
            if (item.location.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item)
                // i("item added to list")
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            binding.recyclerView.adapter = TravelmarkAdapter(filteredlist, this)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        } else {
            binding.recyclerView.adapter = TravelmarkAdapter(filteredlist, this)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun filterCategory(category: String) {
        var filteredlist: MutableList<TravelmarkModel>
        var travelmarks = app.travelmarks.findAll().toMutableList()

        when (category) {
            "all" -> {
                filteredlist = travelmarks
            }
            "Thing to do" -> {
                filteredlist = travelmarks.filter { it.category == "Thing to do" } as MutableList<TravelmarkModel>
            }

            "Sight to see" -> {
                filteredlist = travelmarks.filter { it.category == "Sight to see" } as MutableList<TravelmarkModel>
            }

            "Food to eat" -> {
                filteredlist = travelmarks.filter { it.category == "Food to eat" } as MutableList<TravelmarkModel>
            }
            else -> {
                filteredlist = travelmarks
            }
        }
        binding.recyclerView.adapter = TravelmarkAdapter(filteredlist, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
