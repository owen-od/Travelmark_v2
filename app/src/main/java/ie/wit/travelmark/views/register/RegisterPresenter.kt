package ie.wit.travelmark.views.register

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.UserModel
import ie.wit.travelmark.views.travelmarklist.TravelmarkListView

class RegisterPresenter (val view: RegisterView) {

    var app: MainApp
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerTravelmarkCallback()
    }

    fun doRegisterUser(user: UserModel): Boolean {
        return if (!user.username.isNullOrBlank() && !user.password.isNullOrBlank()) {
            app.users.createUser(user)
            val launcherIntent = Intent(view, TravelmarkListView::class.java)
            travelmarkIntentLauncher.launch(launcherIntent)
            true
        } else {
            false
        }
    }

    private fun registerTravelmarkCallback() {
        travelmarkIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}