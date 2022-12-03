package ie.wit.travelmark.main

import android.app.Application
import ie.wit.travelmark.room.TravelmarkStoreRoom
import ie.wit.travelmark.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var travelmarks: TravelmarkStore
    // val users = UserMemStore()
    // lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Travelmark started")
        // travelmarks = TravelmarkJSONStore(applicationContext)
        // users = UserJSONStore(applicationContext)
        //travelmarks = TravelmarkMemStore()
        travelmarks = TravelmarkStoreRoom(applicationContext)
    }
}