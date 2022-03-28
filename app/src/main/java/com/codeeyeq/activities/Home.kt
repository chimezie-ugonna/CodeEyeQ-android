package com.codeeyeq.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codeeyeq.R
import com.codeeyeq.models.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    private lateinit var fsl: FullScreenLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this).set()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fsl = FullScreenLoader(this)
        findViewById<TextView>(R.id.log_out).setOnClickListener {
            GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                    .requestIdToken(resources.getString(R.string.web_client_id))
                    .build()
            ).signOut().addOnCompleteListener(this) {
                hideKeyboard()
                if (InternetCheck(this, findViewById(R.id.parent)).status()) {
                    fsl.show()
                    DatabaseConnection(this).logOut(
                        Firebase.auth.currentUser?.uid.toString()
                    )
                }
            }
        }
    }

    fun loggedOut(l: Int) {
        if (l == 1) {
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            CustomSnackBar(
                this,
                findViewById(R.id.parent),
                getString(R.string.server_error_message),
                "error"
            ).show()
        }
        Handler(Looper.getMainLooper()).postDelayed({ fsl.hide() }, 1000)
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        if (!fsl.isShowing()) {
            this.moveTaskToBack(true)
        }
    }
}