package ie.wit.travelmark.views.travelmarklist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.travelmark.R
import ie.wit.travelmark.views.travelmarksmap.TravelmarksMapView
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel
import ie.wit.travelmark.views.travelmark.TravelmarkView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TravelmarkListPresenter (private val view: TravelmarkListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerRefreshCallback()
        registerMapCallback()
    }

    fun doAddTravelmark() {
        val launcherIntent = Intent(view, TravelmarkView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowTravelmarksMap() {
        val launcherIntent = Intent(view, TravelmarksMapView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditTravelmark(travelmark: TravelmarkModel) {
        val launcherIntent = Intent(view, TravelmarkView::class.java)
        launcherIntent.putExtra("travelmark_edit", travelmark)
        refreshIntentLauncher.launch(launcherIntent)
    }

    suspend fun doSearch(query: String, checkedChip: Int) {
        var chipCategory = when (checkedChip) {
            R.id.chip_option_see -> "Sight to see"
            R.id.chip_option_do -> "Thing to do"
            R.id.chip_option_eat -> "Food to eat"
            else -> "All"
        }
        val filteredlist: MutableList<TravelmarkModel> = mutableListOf()
        val travelmarks = app.travelmarks.findTravelmarksByCategory(chipCategory)

        for (item in travelmarks) {
            if (item.location.toLowerCase().contains(query.toLowerCase())) {
                filteredlist.add(item)
                // i("item added to list")
            }
        }
        view.showTravelmarks(filteredlist)
    }

    suspend fun doChipChange(selectedChip: Int) {
        var chipCategory = when (selectedChip) {
            R.id.chip_option_see -> "Sight to see"
            R.id.chip_option_do -> "Thing to do"
            R.id.chip_option_eat -> "Food to eat"
            else -> "All"
        }
        var filteredlist: MutableList<TravelmarkModel>
        var travelmarks = app.travelmarks.findAll().toMutableList()

        when (chipCategory) {
            "All" -> {
                filteredlist = travelmarks
            }
            "Thing to do" -> {
                filteredlist =
                    travelmarks.filter { it.category == "Thing to do" } as MutableList<TravelmarkModel>
            }

            "Sight to see" -> {
                filteredlist =
                    travelmarks.filter { it.category == "Sight to see" } as MutableList<TravelmarkModel>
            }

            "Food to eat" -> {
                filteredlist =
                    travelmarks.filter { it.category == "Food to eat" } as MutableList<TravelmarkModel>
            }
            else -> {
                filteredlist = travelmarks
            }
        }
        view.showTravelmarks(filteredlist)
    }
    
    suspend fun getTravelmarks() = app.travelmarks.findAll()

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
                GlobalScope.launch(Dispatchers.Main){
                    getTravelmarks()
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}