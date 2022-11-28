package ie.wit.travelmark.views.travelmark

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import ie.wit.travelmark.R
import ie.wit.travelmark.views.editlocation.EditLocationView
import ie.wit.travelmark.databinding.ActivityTravelmarkBinding
import ie.wit.travelmark.helpers.showImagePicker
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.Location
import ie.wit.travelmark.models.TravelmarkModel
import ie.wit.travelmark.views.editlocation.checkLocationPermissions
import timber.log.Timber

class TravelmarkPresenter (private val view: TravelmarkView) {

    var travelmark = TravelmarkModel()
    var binding: ActivityTravelmarkBinding = ActivityTravelmarkBinding.inflate(view.layoutInflater)
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    val location = Location(52.245696, -7.139102, 15f)

    var edit = false
    var app: MainApp = view.application as MainApp

    init {

        doPermissionLauncher()
        registerImagePickerCallback()
        registerMapCallback()

        if (view.intent.hasExtra("travelmark_edit")) {
            edit = true
            travelmark = view.intent.extras?.getParcelable("travelmark_edit")!!
            view.showTravelmark(travelmark)
            view.showEditView()
        }
        else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
            travelmark.lat = location.lat
            travelmark.lng = location.lng
        }
        doPermissionLauncher()
        registerImagePickerCallback()
        registerMapCallback()
    }

    suspend fun doAddorUpdate(location: String, title: String, description: String, rating: Float, category: Int  ) {
        travelmark.location = location
        travelmark.title = title
        travelmark.description = description
        travelmark.rating = rating
        travelmark.category = when (category) {
            R.id.option_see -> "Sight to see"
            R.id.option_do -> "Thing to do"
            R.id.option_eat -> "Food to eat"
            else -> "N/A"
        }
        if (edit) {
            app.travelmarks.update(travelmark.copy())
        } else {
            app.travelmarks.create(travelmark.copy())
        }
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        if (travelmark.zoom != 0f) {
            location.lat = travelmark.lat
            location.lng = travelmark.lng
            location.zoom = travelmark.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheTravelmark (location: String, title: String, description: String, rating: Float, category: Int) {
        travelmark.location = location
        travelmark.title = title
        travelmark.description = description
        travelmark.rating = rating
        travelmark.category = when (category) {
            R.id.option_see -> "Sight to see"
            R.id.option_do -> "Thing to do"
            R.id.option_eat -> "Food to eat"
            else -> "N/A"
        }
    }

    suspend fun doDelete() {
        app.travelmarks.delete(travelmark)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doHome() {
        view.finish()
    }

    fun locationUpdate(lat: Double, lng: Double) {
        travelmark.lat = lat
        travelmark.lng = lng
        travelmark.zoom = 15f
        // view.showTravelmark(travelmark)
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        Timber.i("setting location from doSetLocation")
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            travelmark.image = result.data!!.data!!
                            view.updateImage(travelmark.image)
                            Picasso.get()
                                .load(travelmark.image)
                                .into(binding.travelmarkImage)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            travelmark.lat = location.lat
                            travelmark.lng = location.lng
                            travelmark.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun doPermissionLauncher() {
        Timber.i("permission check called")
        requestPermissionLauncher =
            view.registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    doSetCurrentLocation()
                } else {
                    locationUpdate(location.lat, location.lng)
                }
            }
    }
}