package ie.wit.travelmark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.travelmark.R
import ie.wit.travelmark.databinding.ActivityTravelmarkBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber
import timber.log.Timber.i

class TravelmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTravelmarkBinding
    var travelmark = TravelmarkModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravelmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Travelmark Activity started..")

        binding.btnAdd.setOnClickListener() {
            travelmark.title = binding.travelmarkTitle.text.toString()
            travelmark.description = binding.travelmarkDescription.text.toString()
            if (travelmark.title.isNotEmpty()) {
                app.travelmarks.add(travelmark.copy())
                i("add Button Pressed: ${travelmark.title}")
                for (i in app.travelmarks.indices)
                { i("Placemark[$i]:${this.app.travelmarks[i]}") }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_travelmark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}