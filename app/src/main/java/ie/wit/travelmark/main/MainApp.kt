package ie.wit.travelmark.main

import android.app.Application
import ie.wit.travelmark.models.TravelmarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val travelmarks = ArrayList<TravelmarkModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Travelmark started")
    }
}