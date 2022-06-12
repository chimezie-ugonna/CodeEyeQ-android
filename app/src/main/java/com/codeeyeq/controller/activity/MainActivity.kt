package com.codeeyeq.controller.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codeeyeq.R
import com.codeeyeq.model.SetAppTheme
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var titleSwitcher: TextSwitcher
    private lateinit var descriptionSwitcher: TextSwitcher
    private lateinit var title: TextView
    private lateinit var description: TextView
    private var shortAnimationDuration: Int = 0
    private var titles = ArrayList<String>()
    private var descriptions = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)

        shortAnimationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime)

        titles.add(resources.getString(R.string.on_boarding_title_1))
        titles.add(resources.getString(R.string.on_boarding_title_2))
        titles.add(resources.getString(R.string.on_boarding_title_3))

        titleSwitcher = findViewById(R.id.titleSwitcher)
        titleSwitcher.setFactory {
            title = TextView(this@MainActivity)
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.heading))
            title.text = titles[0]
            title.gravity = Gravity.CENTER
            title.setTypeface(null, Typeface.NORMAL)
            title.setTextColor(getColorResCompat(R.attr.black_white))
            title
        }

        titleSwitcher.setText(titles[0])
        titleSwitcher.inAnimation.duration = shortAnimationDuration.toLong()
        titleSwitcher.outAnimation.duration = shortAnimationDuration.toLong()

        descriptions.add(resources.getString(R.string.on_boarding_description_1))
        descriptions.add(resources.getString(R.string.on_boarding_description_2))
        descriptions.add(resources.getString(R.string.on_boarding_description_3))

        descriptionSwitcher = findViewById(R.id.descriptionSwitcher)
        descriptionSwitcher.setFactory {
            description = TextView(this@MainActivity)
            description.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.normal)
            )
            description.gravity = Gravity.CENTER
            description.setTypeface(null, Typeface.NORMAL)
            description.setTextColor(getColorResCompat(R.attr.grey))
            description
        }

        descriptionSwitcher.setText(descriptions[0])
        descriptionSwitcher.inAnimation.duration = shortAnimationDuration.toLong()
        descriptionSwitcher.outAnimation.duration = shortAnimationDuration.toLong()

        findViewById<RelativeLayout?>(R.id.getStarted).setOnClickListener {
            startActivity(Intent(this, GetStarted::class.java))
        }

        findViewById<RelativeLayout?>(R.id.login).setOnClickListener {
            startActivity(Intent(this, LogIn::class.java))
        }

        if (!isGooglePlayServicesAvailable()) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        }

        Handler(Looper.getMainLooper()).postDelayed({ change1() }, 3000)
    }

    override fun onResume() {
        super.onResume()
        if (!isGooglePlayServicesAvailable()) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        }
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }

    private fun change1() {
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

        titleSwitcher.setText(titles[1])
        descriptionSwitcher.setText(descriptions[1])

        Handler(Looper.getMainLooper()).postDelayed({ change2() }, 3000)
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

        titleSwitcher.setText(titles[2])
        descriptionSwitcher.setText(descriptions[2])

        Handler(Looper.getMainLooper()).postDelayed({ change3() }, 3000)
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

        titleSwitcher.setText(titles[0])
        descriptionSwitcher.setText(descriptions[0])

        Handler(Looper.getMainLooper()).postDelayed({ change1() }, 3000)
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS
    }

    private fun getColorResCompat(id: Int): Int {
        val resolvedAttr = TypedValue()
        this.theme.resolveAttribute(id, resolvedAttr, true)
        val colorRes = resolvedAttr.run { if (resourceId != 0) resourceId else data }
        return ContextCompat.getColor(this, colorRes)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}