package ie.wit.travelmark.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.travelmark.R
import ie.wit.travelmark.databinding.ActivityTravelmarkBinding
import ie.wit.travelmark.helpers.showImagePicker
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber
import timber.log.Timber.i

class TravelmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTravelmarkBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    var travelmark = TravelmarkModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false

        registerImagePickerCallback()

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
            binding.addPlacemarkHeader.setText("Update Travelmark")
            Picasso.get()
                .load(travelmark.image)
                .into(binding.travelmarkImage)
        }
        if (travelmark.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.button_changeImage)
        }

        binding.btnAdd.setOnClickListener() {
            travelmark.location = binding.travelmarkLocation.text.toString()
            travelmark.title = binding.travelmarkTitle.text.toString()
            travelmark.description = binding.travelmarkDescription.text.toString()

            travelmark.category = when (binding.travelmarkCategory.checkedRadioButtonId) {
                R.id.option_see -> "Sight to see"
                R.id.option_do -> "Thing to do"
                R.id.option_eat -> "Food to eat"
                else -> "N/A"
            }

            if (travelmark.title.isEmpty()) {
                Snackbar
                    .make(it, R.string.warning_enterTitle, Snackbar.LENGTH_LONG)
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            travelmark.image = result.data!!.data!!
                            Picasso.get()
                                .load(travelmark.image)
                                .into(binding.travelmarkImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}