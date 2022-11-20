package ie.wit.travelmark.views.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.travelmark.activities.RegisterActivity
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.views.travelmarklist.TravelmarkListView

class LoginPresenter(private val view: LoginView) {

    var app: MainApp
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var registerIntentLauncher : ActivityResultLauncher<Intent>

    init{
        app = view.application as MainApp
        registerTravelmarkCallback()
        registerRegisterCallback()
    }

    fun doLogin(username: String, password: String): Boolean {
        return if (app.users.login(username, password)) {
            val launcherIntent = Intent(view, TravelmarkListView::class.java)
            travelmarkIntentLauncher.launch(launcherIntent)
            true
        } else {
            false
        }
    }

    fun doRegister() {
        val launcherIntent = Intent(view, RegisterActivity::class.java)
        registerIntentLauncher.launch(launcherIntent)
    }

    private fun registerTravelmarkCallback() {
        travelmarkIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
    private fun registerRegisterCallback() {
        registerIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

}