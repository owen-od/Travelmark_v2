package ie.wit.travelmark.main

import android.app.Application
import ie.wit.travelmark.models.TravelmarkJSONStore
import ie.wit.travelmark.models.TravelmarkMemStore
import ie.wit.travelmark.models.TravelmarkModel
import ie.wit.travelmark.models.TravelmarkStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var travelmarks: TravelmarkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Travelmark started")
        travelmarks = TravelmarkJSONStore(applicationContext)
        //travelmarks = TravelmarkMemStore()
    }
}