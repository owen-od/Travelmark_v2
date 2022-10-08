package ie.wit.travelmark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.travelmark.R
import ie.wit.travelmark.main.MainApp

class TravelmarkListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travelmark_list)
        app = application as MainApp
    }
}