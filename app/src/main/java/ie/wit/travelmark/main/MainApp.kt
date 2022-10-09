package ie.wit.travelmark.main

import android.app.Application
import ie.wit.travelmark.models.TravelmarkMemStore
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val travelmarks = TravelmarkMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
        // travelmarks.create(TravelmarkModel("Dublin", "Spire", "Monument on O'Connell Street"))
        // travelmarks.create(TravelmarkModel("Dublin", "GPO", "Dublin post office"))
        // travelmarks.create(TravelmarkModel("Paris", "Eiffel Tower", "Famous paris tower"))
    }
}