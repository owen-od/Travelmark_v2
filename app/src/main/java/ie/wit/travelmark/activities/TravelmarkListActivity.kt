package ie.wit.travelmark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.travelmark.R
import ie.wit.travelmark.adapters.TravelmarkAdapter
import ie.wit.travelmark.adapters.TravelmarkListener
import ie.wit.travelmark.databinding.ActivityTravelmarkListBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkModel

class TravelmarkListActivity : AppCompatActivity(), TravelmarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityTravelmarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TravelmarkAdapter(app.travelmarks.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, TravelmarkActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTravelmarkClick(travelmark: TravelmarkModel) {
        val launcherIntent = Intent(this, TravelmarkActivity::class.java)
        launcherIntent.putExtra("travelmark_edit", travelmark)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
