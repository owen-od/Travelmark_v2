package ie.wit.travelmark.views.register

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.views.travelmarklist.TravelmarkListView

class RegisterPresenter (val view: RegisterView) {

    var app: MainApp
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerTravelmarkCallback()
    }
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doRegisterUser(email: String, password: String) {
        view.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                val launcherIntent = Intent(view, TravelmarkListView::class.java)
                travelmarkIntentLauncher.launch(launcherIntent)
            } else {
                view.showSnackBar("Login failed: ${task.exception?.message}")
            }
            view.hideProgress()
        }
    }

    private fun registerTravelmarkCallback() {
        travelmarkIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}