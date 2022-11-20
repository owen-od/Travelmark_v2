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
import ie.wit.travelmark.models.UserModel
import ie.wit.travelmark.views.travelmarklist.TravelmarkListView

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var registerIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var app : MainApp

    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerTravelmarkCallback()
        registerRegisterCallback()

        app = application as MainApp

        binding.login.setOnClickListener {
            var username = binding.username.text.toString()
            if (username.length < 3) {
                username = ""
                binding.username.setError("Username must be 3 characters or more")
            }
            var password = binding.password.text.toString()
            if (password.length < 3) {
                password = ""
                binding.password.setError("Password must be 3 characters or more")
            }
            if(app.users.login(username, password)) {
                val launcherIntent = Intent(this, TravelmarkListView::class.java)
                travelmarkIntentLauncher.launch(launcherIntent)
            } else {
                Snackbar
                    .make(it, R.string.warning_incorrect_credentials, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.registerMessage.setOnClickListener {
            val launcherIntent = Intent(this, RegisterActivity::class.java)
            registerIntentLauncher.launch(launcherIntent)
        }
    }

    private fun registerTravelmarkCallback() {
        travelmarkIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
    private fun registerRegisterCallback() {
        registerIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

}