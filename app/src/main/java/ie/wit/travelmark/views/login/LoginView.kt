package ie.wit.travelmark.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import ie.wit.travelmark.R
import ie.wit.travelmark.databinding.ActivityLoginBinding
import ie.wit.travelmark.main.MainApp

class LoginView : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var app : MainApp
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        presenter = LoginPresenter(this)

        binding.progressBar.visibility = View.GONE

        binding.login.setOnClickListener {
            var username = binding.username.text.toString()
            var password = binding.password.text.toString()
            if (username == "" || password == "") {
                showSnackBar(R.string.warning_enter_credentials.toString())
            }
            else {
                presenter.doLogin(username,password)
            }
        }

        binding.registerMessage.setOnClickListener {
            presenter.doRegister()
        }
    }

    fun showSnackBar(message: CharSequence){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }
}