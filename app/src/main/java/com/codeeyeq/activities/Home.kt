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
import com.codeeyeq.R
import com.codeeyeq.models.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    private lateinit var fsl: FullScreenLoader
    private lateinit var homeIcon: ImageView
    private lateinit var learnIcon: ImageView
    private lateinit var interactIcon: ImageView
    private lateinit var boardIcon: ImageView
    private lateinit var homeIconParent: FrameLayout
    private lateinit var learnIconParent: FrameLayout
    private lateinit var interactIconParent: FrameLayout
    private lateinit var boardIconParent: FrameLayout
    private lateinit var homeLayout: LinearLayout
    private lateinit var learnLayout: LinearLayout
    private lateinit var interactLayout: LinearLayout
    private lateinit var boardLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this).set()
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
            interactLayout.visibility = View.GONE
            interactIcon.setImageResource(R.drawable.interact_outline)
            boardLayout.visibility = View.GONE
            boardIcon.setImageResource(R.drawable.board_outline)

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
            interactLayout.visibility = View.GONE
            interactIcon.setImageResource(R.drawable.interact_outline)
            boardLayout.visibility = View.GONE
            boardIcon.setImageResource(R.drawable.board_outline)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

        interactLayout = findViewById(R.id.interact_layout)
        interactIcon = findViewById(R.id.interact_icon)
        interactIconParent = findViewById(R.id.interact_icon_parent)
        interactIconParent.setOnClickListener {
            homeLayout.visibility = View.GONE
            homeIcon.setImageResource(R.drawable.home_outline)
            learnLayout.visibility = View.GONE
            learnIcon.setImageResource(R.drawable.learn_outline)
            interactLayout.visibility = View.VISIBLE
            interactIcon.setImageResource(R.drawable.interact)
            boardLayout.visibility = View.GONE
            boardIcon.setImageResource(R.drawable.board_outline)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

        boardLayout = findViewById(R.id.board_layout)
        boardIcon = findViewById(R.id.board_icon)
        boardIconParent = findViewById(R.id.board_icon_parent)
        boardIconParent.setOnClickListener {
            homeLayout.visibility = View.GONE
            homeIcon.setImageResource(R.drawable.home_outline)
            learnLayout.visibility = View.GONE
            learnIcon.setImageResource(R.drawable.learn_outline)
            interactLayout.visibility = View.GONE
            interactIcon.setImageResource(R.drawable.interact_outline)
            boardLayout.visibility = View.VISIBLE
            boardIcon.setImageResource(R.drawable.board)

            it.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }

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