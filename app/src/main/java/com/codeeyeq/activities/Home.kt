package com.codeeyeq.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codeeyeq.R
import com.codeeyeq.models.SetAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this).set()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<TextView>(R.id.log_out).setOnClickListener {
            GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                    .requestIdToken(resources.getString(R.string.web_client_id))
                    .build()
            ).signOut().addOnCompleteListener(this) {
                Firebase.auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.moveTaskToBack(true)
    }
}