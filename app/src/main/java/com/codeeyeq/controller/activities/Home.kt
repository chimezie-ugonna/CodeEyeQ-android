package com.codeeyeq.controller.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.codeeyeq.R
import com.codeeyeq.controller.fragments.HomeFragmentCollaborate
import com.codeeyeq.controller.fragments.HomeFragmentHome
import com.codeeyeq.controller.fragments.HomeFragmentLearn
import com.codeeyeq.controller.fragments.HomeFragmentMore
import com.codeeyeq.model.*
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
    private lateinit var bottomNavigation: LinearLayout
    private lateinit var homeFragmentHome: HomeFragmentHome
    private lateinit var homeFragmentLearn: HomeFragmentLearn
    private lateinit var homeFragmentCollaborate: HomeFragmentCollaborate
    private lateinit var homeFragmentMore: HomeFragmentMore
    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fsl = FullScreenLoader(this)

        homeIcon = findViewById(R.id.home_icon)
        homeIconParent = findViewById(R.id.home_icon_parent)
        homeIconParent.setOnClickListener {
            replaceFragment(homeFragmentHome, it)
        }

        learnIcon = findViewById(R.id.learn_icon)
        learnIconParent = findViewById(R.id.learn_icon_parent)
        learnIconParent.setOnClickListener {
            replaceFragment(homeFragmentLearn, it)
        }

        collaborateIcon = findViewById(R.id.collaborate_icon)
        collaborateIconParent = findViewById(R.id.collaborate_icon_parent)
        collaborateIconParent.setOnClickListener {
            replaceFragment(homeFragmentCollaborate, it)
        }

        moreIcon = findViewById(R.id.more_icon)
        moreIconParent = findViewById(R.id.more_icon_parent)
        moreIconParent.setOnClickListener {
            replaceFragment(homeFragmentMore, it)
        }

        bottomNavigation = findViewById(R.id.bottom_navigation)

        homeFragmentHome = HomeFragmentHome()
        homeFragmentLearn = HomeFragmentLearn()
        homeFragmentCollaborate = HomeFragmentCollaborate()
        homeFragmentMore = HomeFragmentMore()
        replaceFragment(homeFragmentHome)
    }

    fun showBottomNavigation() {
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

    fun hideBottomNavigation() {
        val animate = TranslateAnimation(
            0f,
            0f,
            0f,
            bottomNavigation.height + resources.getDimension(R.dimen.padding)
        )

        animate.duration = 500
        animate.fillAfter = true
        bottomNavigation.startAnimation(animate)
    }

    fun logOut() {
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

    private fun replaceFragment(fragment: Fragment, view: View? = null) {
        val fragmentManager = supportFragmentManager.beginTransaction()

        when (fragment) {
            is HomeFragmentHome -> {
                if (homeFragmentHome.isAdded) {
                    fragmentManager.show(homeFragmentHome)
                } else {
                    fragmentManager.add(R.id.fragment_container, homeFragmentHome)
                }
                if (homeFragmentLearn.isAdded) {
                    fragmentManager.hide(homeFragmentLearn)
                }
                if (homeFragmentCollaborate.isAdded) {
                    fragmentManager.hide(homeFragmentCollaborate)
                }
                if (homeFragmentMore.isAdded) {
                    fragmentManager.hide(homeFragmentMore)
                }
                homeIcon.setImageResource(R.drawable.home)
                learnIcon.setImageResource(R.drawable.learn_outline)
                collaborateIcon.setImageResource(R.drawable.collaborate_outline)
                moreIcon.setImageResource(R.drawable.more_outline)
            }
            is HomeFragmentLearn -> {
                if (homeFragmentLearn.isAdded) {
                    fragmentManager.show(homeFragmentLearn)
                } else {
                    fragmentManager.add(R.id.fragment_container, homeFragmentLearn)
                }
                if (homeFragmentHome.isAdded) {
                    fragmentManager.hide(homeFragmentHome)
                }
                if (homeFragmentCollaborate.isAdded) {
                    fragmentManager.hide(homeFragmentCollaborate)
                }
                if (homeFragmentMore.isAdded) {
                    fragmentManager.hide(homeFragmentMore)
                }
                homeIcon.setImageResource(R.drawable.home_outline)
                learnIcon.setImageResource(R.drawable.learn)
                collaborateIcon.setImageResource(R.drawable.collaborate_outline)
                moreIcon.setImageResource(R.drawable.more_outline)
            }
            is HomeFragmentCollaborate -> {
                if (homeFragmentCollaborate.isAdded) {
                    fragmentManager.show(homeFragmentCollaborate)
                } else {
                    fragmentManager.add(R.id.fragment_container, homeFragmentCollaborate)
                }
                if (homeFragmentHome.isAdded) {
                    fragmentManager.hide(homeFragmentHome)
                }
                if (homeFragmentLearn.isAdded) {
                    fragmentManager.hide(homeFragmentLearn)
                }
                if (homeFragmentMore.isAdded) {
                    fragmentManager.hide(homeFragmentMore)
                }
                homeIcon.setImageResource(R.drawable.home_outline)
                learnIcon.setImageResource(R.drawable.learn_outline)
                collaborateIcon.setImageResource(R.drawable.collaborate)
                moreIcon.setImageResource(R.drawable.more_outline)
            }
            is HomeFragmentMore -> {
                if (homeFragmentMore.isAdded) {
                    fragmentManager.show(homeFragmentMore)
                } else {
                    fragmentManager.add(R.id.fragment_container, homeFragmentMore)
                }
                if (homeFragmentHome.isAdded) {
                    fragmentManager.hide(homeFragmentHome)
                }
                if (homeFragmentLearn.isAdded) {
                    fragmentManager.hide(homeFragmentLearn)
                }
                if (homeFragmentCollaborate.isAdded) {
                    fragmentManager.hide(homeFragmentCollaborate)
                }
                homeIcon.setImageResource(R.drawable.home_outline)
                learnIcon.setImageResource(R.drawable.learn_outline)
                collaborateIcon.setImageResource(R.drawable.collaborate_outline)
                moreIcon.setImageResource(R.drawable.more)
            }
        }

        fragmentManager.commit()

        view?.performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
        hideKeyboard()
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