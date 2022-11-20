package ie.wit.travelmark.views.travelmarklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ie.wit.travelmark.R
import ie.wit.travelmark.adapters.TravelmarkAdapter
import ie.wit.travelmark.adapters.TravelmarkListener
import ie.wit.travelmark.databinding.ActivityTravelmarkListBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber.i

class TravelmarkListView : AppCompatActivity(), TravelmarkListener {

    lateinit var app: MainApp
    lateinit var presenter: TravelmarkListPresenter
    private lateinit var binding: ActivityTravelmarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        presenter = TravelmarkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadTravelmarks()

        binding.chipOptions.check(R.id.chip_option_all)
        binding.chipOptions.setOnCheckedChangeListener(object: ChipGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(chipGroup: ChipGroup, id: Int) {
                var selectedChip = chipGroup.findViewById(id) as Chip
                presenter.doChipChange(selectedChip.id)
            }
        })
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
                presenter.doSearch(query, checkedChip)
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                val checkedChip = binding.chipOptions.checkedChipId
                presenter.doSearch(msg, checkedChip)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddTravelmark() }
            R.id.item_map -> { presenter.doShowTravelmarksMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTravelmarkClick(travelmark: TravelmarkModel) {
        presenter.doEditTravelmark(travelmark)
    }

    private fun loadTravelmarks() {
        showTravelmarks(presenter.getTravelmarks())
    }

    fun showTravelmarks (travelmarks: List<TravelmarkModel>) {
        binding.recyclerView.adapter = TravelmarkAdapter(travelmarks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun filter(text: String, category: String) {
        val filteredList = presenter.doTextFilter(text, category)
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = TravelmarkAdapter(filteredList, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun filterCategory(category: String) {
        val filteredList = presenter.doCategoryFilter(category)
        binding.recyclerView.adapter = TravelmarkAdapter(filteredList, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        i("recyclerView onResume")
        super.onResume()
    }
}
