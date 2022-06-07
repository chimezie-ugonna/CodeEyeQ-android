package com.codeeyeq.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.codeeyeq.R
import com.codeeyeq.models.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject


class Home : AppCompatActivity() {
    private lateinit var fsl: FullScreenLoader
    private lateinit var homeIcon: ImageView
    private lateinit var learnIcon: ImageView
    private lateinit var collaborateIcon: ImageView
    private lateinit var moreIcon: ImageView
    private lateinit var homeIconParent: FrameLayout
    private lateinit var learnIconParent: FrameLayout
    private lateinit var collaborateIconParent: FrameLayout
    private lateinit var moreIconParent: FrameLayout
    private lateinit var homeLayout: LinearLayout
    private lateinit var learnLayout: LinearLayout
    private lateinit var collaborateLayout: LinearLayout
    private lateinit var moreLayout: LinearLayout
    private lateinit var bottomNavigation: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fsl = FullScreenLoader(this)

        homeLayout = findViewById(R.id.home_layout)
        homeIcon = findViewById(R.id.home_icon)
        homeIconParent = findViewById(R.id.home_icon_parent)
        homeIconParent.setOnClickListener {
            homeLayout.visibility = View.VISIBLE
            homeIcon.setImageResource(R.drawable.home)
            learnLayout.visibility = View.GONE
            learnIcon.setImageResource(R.drawable.learn_outline)
            collaborateLayout.visibility = View.GONE
            collaborateIcon.setImageResource(R.drawable.collaborate_outline)
            moreLayout.visibility = View.GONE
            moreIcon.setImageResource(R.drawable.more_outline)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

        learnLayout = findViewById(R.id.learn_layout)
        learnIcon = findViewById(R.id.learn_icon)
        learnIconParent = findViewById(R.id.learn_icon_parent)
        learnIconParent.setOnClickListener {
            homeLayout.visibility = View.GONE
            homeIcon.setImageResource(R.drawable.home_outline)
            learnLayout.visibility = View.VISIBLE
            learnIcon.setImageResource(R.drawable.learn)
            collaborateLayout.visibility = View.GONE
            collaborateIcon.setImageResource(R.drawable.collaborate_outline)
            moreLayout.visibility = View.GONE
            moreIcon.setImageResource(R.drawable.more_outline)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

        collaborateLayout = findViewById(R.id.collaborate_layout)
        collaborateIcon = findViewById(R.id.collaborate_icon)
        collaborateIconParent = findViewById(R.id.collaborate_icon_parent)
        collaborateIconParent.setOnClickListener {
            homeLayout.visibility = View.GONE
            homeIcon.setImageResource(R.drawable.home_outline)
            learnLayout.visibility = View.GONE
            learnIcon.setImageResource(R.drawable.learn_outline)
            collaborateLayout.visibility = View.VISIBLE
            collaborateIcon.setImageResource(R.drawable.collaborate)
            moreLayout.visibility = View.GONE
            moreIcon.setImageResource(R.drawable.more_outline)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

        moreLayout = findViewById(R.id.more_layout)
        moreIcon = findViewById(R.id.more_icon)
        moreIconParent = findViewById(R.id.more_icon_parent)
        moreIconParent.setOnClickListener {
            homeLayout.visibility = View.GONE
            homeIcon.setImageResource(R.drawable.home_outline)
            learnLayout.visibility = View.GONE
            learnIcon.setImageResource(R.drawable.learn_outline)
            collaborateLayout.visibility = View.GONE
            collaborateIcon.setImageResource(R.drawable.collaborate_outline)
            moreLayout.visibility = View.VISIBLE
            moreIcon.setImageResource(R.drawable.more)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

        bottomNavigation = findViewById(R.id.bottom_navigation)

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
                    ServerConnection(
                        this,
                        "logOut",
                        Request.Method.DELETE,
                        "logins/delete",
                        JSONObject()
                    )
                }
            }
        }
    }

    /*private fun showBottomNavigation() {
        bottomNavigation.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,
            0f,
            bottomNavigation.height.toFloat(),
            0f
        )

        animate.duration = 500
        animate.fillAfter = true
        bottomNavigation.startAnimation(animate)
    }

    private fun hideBottomNavigation() {
        val animate = TranslateAnimation(
            0f,
            0f,
            0f,
            bottomNavigation.height + resources.getDimension(R.dimen.padding)
        )

        animate.duration = 500
        animate.fillAfter = true
        bottomNavigation.startAnimation(animate)
    }*/

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
            )
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