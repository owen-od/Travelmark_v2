package ie.wit.travelmark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.travelmark.databinding.ActivityTravelmarkBinding
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber
import timber.log.Timber.i

class TravelmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTravelmarkBinding
    var travelmark = TravelmarkModel()
    var travelmarks = ArrayList<TravelmarkModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravelmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Travelmark Activity started..")

        binding.btnAdd.setOnClickListener() {
            travelmark.title = binding.travelmarkTitle.text.toString()
            travelmark.description = binding.travelmarkDescription.toString()
            if (travelmark.title.isNotEmpty()) {
                travelmarks.add(travelmark.copy())
                i("add Button Pressed: ${travelmark.title}")
                for (i in travelmarks.indices)
                { i("Placemark[$i]:${this.travelmarks[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}