package com.codeeyeq

import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        SetAppTheme(this).set()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dot1: TextView = findViewById(R.id.dot1)
        val dot2: TextView = findViewById(R.id.dot2)
        val dot3: TextView = findViewById(R.id.dot3)

        findViewById<Button?>(R.id.get_started).setOnClickListener {
            Toast.makeText(
                this,
                "Get started here",
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

        val llm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val recyclerView: RecyclerView = findViewById(R.id.onboarding_slide)
        PagerSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = llm
        recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            when (llm.findLastCompletelyVisibleItemPosition()) {
                0 -> {
                    dot1.setTextColor(
                        getColorResCompat(R.attr.current_dot_indicator_color)
                    )
                    dot1.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.large)
                    )
                    dot2.setTextColor(
                        getColorResCompat(R.attr.small_text_color)
                    )
                    dot2.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.big)
                    )
                    dot3.setTextColor(
                        getColorResCompat(R.attr.small_text_color)
                    )
                    dot3.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.big)
                    )
                }
                1 -> {
                    dot1.setTextColor(
                        getColorResCompat(R.attr.small_text_color)
                    )
                    dot1.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.big)
                    )
                    dot2.setTextColor(
                        getColorResCompat(R.attr.current_dot_indicator_color)
                    )
                    dot2.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.large)
                    )
                    dot3.setTextColor(
                        getColorResCompat(R.attr.small_text_color)
                    )
                    dot3.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.big)
                    )
                }
                2 -> {
                    dot1.setTextColor(
                        getColorResCompat(R.attr.small_text_color)
                    )
                    dot1.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.big)
                    )
                    dot2.setTextColor(
                        getColorResCompat(R.attr.small_text_color)
                    )
                    dot2.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.big)
                    )
                    dot3.setTextColor(
                        getColorResCompat(R.attr.current_dot_indicator_color)
                    )
                    dot3.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.large)
                    )
                }
            }
        }

        val illustrations = ArrayList<Int>()
        illustrations.add(R.drawable.illustration_1)
        illustrations.add(R.drawable.illustration_2)
        illustrations.add(R.drawable.illustration_3)

        val titles = ArrayList<String>()
        titles.add(resources.getString(R.string.onboarding_title_1))
        titles.add(resources.getString(R.string.onboarding_title_2))
        titles.add(resources.getString(R.string.onboarding_title_3))

        val descriptions = ArrayList<String>()
        descriptions.add(resources.getString(R.string.onboarding_description_1))
        descriptions.add(resources.getString(R.string.onboarding_description_2))
        descriptions.add(resources.getString(R.string.onboarding_description_3))

        recyclerView.adapter = OnBoadingSlideAdapter(this, illustrations, titles, descriptions)
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