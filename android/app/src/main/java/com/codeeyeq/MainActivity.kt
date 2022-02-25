package com.codeeyeq

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        SetAppTheme(this).set()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)

        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        Handler(Looper.getMainLooper()).postDelayed({ change() }, 5000)

        findViewById<Button?>(R.id.getStarted).setOnClickListener {
            Toast.makeText(
                this,
                "Get started",
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

    private fun change() {
        img2.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        img1.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    img1.visibility = View.GONE
                }
            })

        title.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_up
            )
        )
        description.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_up
            )
        )

        title.text = resources.getString(R.string.onboarding_title_2)
        description.text = resources.getString(R.string.onboarding_description_2)

        Handler(Looper.getMainLooper()).postDelayed({ change2() }, 5000)
    }

    private fun change2() {
        img3.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        img2.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    img2.visibility = View.GONE
                }
            })

        title.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_up
            )
        )
        description.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_up
            )
        )

        title.text = resources.getString(R.string.onboarding_title_3)
        description.text = resources.getString(R.string.onboarding_description_3)

        Handler(Looper.getMainLooper()).postDelayed({ change3() }, 5000)
    }

    private fun change3() {
        img1.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        img3.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    img3.visibility = View.GONE
                }
            })

        title.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_up
            )
        )
        description.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_up
            )
        )

        title.text = resources.getString(R.string.onboarding_title_1)
        description.text = resources.getString(R.string.onboarding_description_1)

        Handler(Looper.getMainLooper()).postDelayed({ change() }, 5000)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}