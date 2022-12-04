package ie.wit.travelmark.views.travelmarklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import ie.wit.travelmark.R
import ie.wit.travelmark.adapters.TravelmarkAdapter
import ie.wit.travelmark.adapters.TravelmarkListener
import ie.wit.travelmark.databinding.ActivityTravelmarkListBinding
import ie.wit.travelmark.helpers.SwipeToDeleteCallback
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber.i

class TravelmarkListView : AppCompatActivity(), TravelmarkListener {

    lateinit var app: MainApp
    lateinit var presenter: TravelmarkListPresenter
    lateinit var bottomNav : BottomNavigationView
    private lateinit var binding: ActivityTravelmarkListBinding
    private lateinit var travelmarkList: List<TravelmarkModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            binding.toolbar.title = "User: ${user.email}"
        }
        setSupportActionBar(binding.toolbar)

        presenter = TravelmarkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        GlobalScope.launch(Dispatchers.Main) {
            travelmarkList = presenter.getTravelmarks()
        }
        loadTravelmarks()

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    true
                }
                R.id.item_add -> {
                    presenter.doAddTravelmark()
                    i("we got here")
                    true
                }
                R.id.item_map -> {
                    presenter.doShowTravelmarksMap()
                    true
                }
                R.id.item_logout -> {
                    presenter.doLogout()
                    true
                }
                else -> false
            }
        }

        binding.chipOptions.check(R.id.chip_option_all)
        binding.chipOptions.setOnCheckedChangeListener(object: ChipGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(chipGroup: ChipGroup, id: Int) {
                var selectedChip = chipGroup.findViewById(id) as Chip
                GlobalScope.launch(Dispatchers.Main) {
                    presenter.doChipChange(selectedChip.id)
                }
            }
        })

        val swipeDeleteHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerView.adapter as TravelmarkAdapter
                var travelmark = travelmarkList[viewHolder.adapterPosition]
                adapter.removeAt(viewHolder.adapterPosition)
                i("Deleting travelmark: $travelmark")
                GlobalScope.launch(Dispatchers.IO) {
                    presenter.doDelete(travelmark)
                }
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(binding.recyclerView)
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
                GlobalScope.launch(Dispatchers.Main) {
                    presenter.doSearch(query, checkedChip)
                }
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                val checkedChip = binding.chipOptions.checkedChipId
                GlobalScope.launch(Dispatchers.Main) {
                    presenter.doSearch(msg, checkedChip)
                }
                return false
            }
        })
        return true
    }

    override fun onTravelmarkClick(travelmark: TravelmarkModel) {
        presenter.doEditTravelmark(travelmark)
    }

    private fun loadTravelmarks() {
        GlobalScope.launch(Dispatchers.Main) {
            showTravelmarks(presenter.getTravelmarks() as MutableList<TravelmarkModel>)
        }
    }

    fun showTravelmarks (travelmarks: MutableList<TravelmarkModel>) {
        binding.recyclerView.adapter = TravelmarkAdapter(travelmarks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()

        //update travelmark list here to avoid bug in swipe to delete when using filtered data
        travelmarkList = travelmarks
    }

    override fun onResume() {
        loadTravelmarks()
        binding.recyclerView.adapter?.notifyDataSetChanged()
        i("recyclerView onResume")
        bottomNav.setSelectedItemId(R.id.item_home)
        super.onResume()
    }
}
