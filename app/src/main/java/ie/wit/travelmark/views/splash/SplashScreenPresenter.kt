package ie.wit.travelmark.views.splash

import android.content.Intent
import android.os.Handler
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.views.login.LoginView

class SplashScreenPresenter(private val view: SplashScreenView) {

    var app: MainApp
    private lateinit var LoginIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerLoginCallback()
    }

    fun doSplashScreen() {
        Handler().postDelayed({
            val launcherIntent = Intent(view, LoginView::class.java)
            LoginIntentLauncher.launch(launcherIntent)
            view.finish()
        }, 3000)
    }

    private fun registerLoginCallback() {
        LoginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}