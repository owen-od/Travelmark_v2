package ie.wit.travelmark.views.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import ie.wit.travelmark.views.register.RegisterView
import ie.wit.travelmark.main.MainApp
import ie.wit.travelmark.models.TravelmarkFireStore
import ie.wit.travelmark.views.travelmarklist.TravelmarkListView

class LoginPresenter(private val view: LoginView) {

    var app: MainApp = view.application as MainApp
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: TravelmarkFireStore? = null
    private lateinit var travelmarkIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var registerIntentLauncher : ActivityResultLauncher<Intent>

    init{
        registerTravelmarkCallback()
        registerRegisterCallback()
        if (app.travelmarks is TravelmarkFireStore) {
            fireStore = app.travelmarks as TravelmarkFireStore
        }
    }

    fun doLogin(username: String, password: String) {
        view.showProgress()
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchTravelmarks {
                        view.hideProgress()
                        val launcherIntent = Intent(view, TravelmarkListView::class.java)
                        travelmarkIntentLauncher.launch(launcherIntent)
                    }
            } else {
                    view.hideProgress()
                    val launcherIntent = Intent(view, TravelmarkListView::class.java)
                    travelmarkIntentLauncher.launch(launcherIntent)
                }
            } else {
                view.hideProgress()
                view.showSnackBar("Login failed: ${task.exception?.message}")
            }
            view.hideProgress()
        }
    }

    fun doRegister() {
        val launcherIntent = Intent(view, RegisterView::class.java)
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