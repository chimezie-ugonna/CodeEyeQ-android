package com.codeeyeq

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        SetAppTheme(this).set()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button?>(R.id.get_started).setOnClickListener {
            Toast.makeText(
                this,
                "Get started now",
                Toast.LENGTH_LONG
            ).show()
        }
        findViewById<Button?>(R.id.login).setOnClickListener {
            Toast.makeText(
                this,
                "Login",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}