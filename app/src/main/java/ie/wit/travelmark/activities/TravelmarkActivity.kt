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
        var edit = false

        binding = ActivityTravelmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Travelmark Activity started..")

        if (intent.hasExtra("travelmark_edit")) {
            edit = true
            travelmark = intent.extras?.getParcelable("travelmark_edit")!!
            binding.travelmarkTitle.setText(travelmark.title)
            binding.travelmarkDescription.setText(travelmark.description)
            binding.travelmarkLocation.setText(travelmark.location)
            binding.btnAdd.setText(R.string.menu_saveTravelmark)
        }

        binding.btnAdd.setOnClickListener() {
            travelmark.location = binding.travelmarkLocation.text.toString()
            travelmark.title = binding.travelmarkTitle.text.toString()
            travelmark.description = binding.travelmarkDescription.text.toString()
            if (travelmark.title.isEmpty()) {
                Snackbar
                    .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.travelmarks.update(travelmark.copy())
                } else {
                    app.travelmarks.create(travelmark.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
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