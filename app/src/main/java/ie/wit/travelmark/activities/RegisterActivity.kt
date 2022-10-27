package ie.wit.travelmark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import ie.wit.travelmark.R
import ie.wit.travelmark.databinding.ActivityLoginBinding
import ie.wit.travelmark.databinding.ActivityRegisterBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.UserModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var app : MainApp

    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerTravelmarkCallback()

        app = application as MainApp

        binding.registerButton.setOnClickListener {
            user.username = binding.username.text.toString()
            user.password = binding.password.text.toString()
            if(!user.username.isNullOrBlank() && !user.password.isNullOrBlank()){
                app.users.createUser(user.copy())
                Snackbar
                    .make(it, "User Created", Snackbar.LENGTH_LONG)
                    .show()
                val launcherIntent = Intent(this, TravelmarkListActivity::class.java)
                travelmarkIntentLauncher.launch(launcherIntent)
            } else {
                Snackbar
                    .make(it, "User not created - fill all fields", Snackbar.LENGTH_LONG)
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