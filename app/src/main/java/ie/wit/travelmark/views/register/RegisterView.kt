package ie.wit.travelmark.views.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.travelmark.databinding.ActivityRegisterBinding
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.UserModel

class RegisterView : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var app : MainApp
    lateinit var presenter: RegisterPresenter

    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        presenter = RegisterPresenter(this)

        binding.registerButton.setOnClickListener {
            user.username = binding.username.text.toString()
            if (user.username.length < 3) {
                user.username = ""
                binding.username.setError("Username must be 3 characters or more")
            }
            user.password = binding.password.text.toString()
            if (user.password.length < 3) {
                user.password = ""
                binding.password.setError("Password must be 3 characters or more")
            }
            if(presenter.doRegisterUser(user.copy())){
                Snackbar
                    .make(it, "User Created", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                Snackbar
                    .make(it, "User not created - fill all fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

}