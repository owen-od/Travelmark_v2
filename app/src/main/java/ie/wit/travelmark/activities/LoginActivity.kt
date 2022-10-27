package ie.wit.travelmark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import ie.wit.travelmark.R
import ie.wit.travelmark.databinding.ActivityLoginBinding
import ie.wit.travelmark.main.MainApp
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerTravelmarkCallback()

        app = application as MainApp

        binding.login.setOnClickListener {
            var username = binding.username.text.toString()
            var password = binding.password.text.toString()
            if(app.users.login(username, password)) {
                val launcherIntent = Intent(this, TravelmarkListActivity::class.java)
                travelmarkIntentLauncher.launch(launcherIntent)
            } else {
                Snackbar
                    .make(it, R.string.warning_incorrect_credentials, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun registerTravelmarkCallback() {
        travelmarkIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}