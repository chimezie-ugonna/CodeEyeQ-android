package com.codeeyeq.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codeeyeq.*
import com.codeeyeq.models.*
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class GetStarted : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var fullNameHeader: TextView
    private lateinit var fullNameError: TextView
    private var fullNameFieldState: String = "normal"
    private lateinit var email: EditText
    private lateinit var emailHeader: TextView
    private lateinit var emailError: TextView
    private var emailFieldState: String = "normal"
    private lateinit var password: EditText
    private lateinit var passwordHeader: TextView
    private lateinit var passwordError: TextView
    private lateinit var passwordVisibility: ImageView
    private var passwordFieldState: String = "normal"
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private var requestCode: Int = 0
    private lateinit var account: GoogleSignInAccount
    private lateinit var fsl: FullScreenLoader
    private val onActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                try {
                    hideKeyboard()
                    if (InternetCheck(this, findViewById(R.id.parent)).status()) {
                        fsl.show()
                        account =
                            GoogleSignIn.getSignedInAccountFromIntent(result.data)
                                .getResult(ApiException::class.java)

                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        Firebase.auth.signInWithCredential(credential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    join(
                                        Firebase.auth.currentUser?.uid,
                                        Firebase.auth.currentUser?.displayName,
                                        Firebase.auth.currentUser?.email
                                    )
                                }
                            }.addOnFailureListener(
                                OnFailureListener(
                                    this,
                                    findViewById(R.id.parent),
                                    fsl
                                )
                            )
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                    when (e.statusCode) {
                        GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> {
                            CustomSnackBar(
                                this,
                                findViewById(R.id.parent),
                                getString(R.string.SIGN_IN_CANCELLED),
                                "error"
                            ).show()
                        }
                        GoogleSignInStatusCodes.SIGN_IN_FAILED -> {
                            CustomSnackBar(
                                this,
                                findViewById(R.id.parent),
                                getString(R.string.SIGN_IN_FAILED),
                                "error"
                            ).show()
                        }
                        GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS -> {
                            CustomSnackBar(
                                this,
                                findViewById(R.id.parent),
                                getString(R.string.SIGN_IN_CURRENTLY_IN_PROGRESS),
                                "error"
                            ).show()
                        }
                    }
                }
            }
        } else {
            CustomSnackBar(
                this,
                findViewById(R.id.parent),
                getString(R.string.SIGN_IN_FAILED),
                "error"
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SetAppTheme(this).set()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        fsl = FullScreenLoader(this)
        fullNameHeader = findViewById(R.id.fullNameHeader)
        fullNameError = findViewById(R.id.fullNameError)
        fullName = findViewById(R.id.fullName)
        fullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (fullNameFieldState == "error" || fullNameFieldState == "success") {
                    fullNameCheck()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        fullName.setOnFocusChangeListener { _, b ->
            if (b) {
                if (fullNameFieldState == "normal") {
                    fullNameHeader.setTextColor(getColorResCompat(R.attr.blue))
                    fullName.setBackgroundResource(R.drawable.snow_night_solid_blue_stroke)
                    fullName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.account_circle_active,
                        0,
                        0,
                        0
                    )
                    fullName.compoundDrawablePadding =
                        resources.getDimension(R.dimen.padding).toInt()
                    fullNameError.text = ""
                    fullNameError.visibility = View.GONE
                    fullNameFieldState = "active"
                }
            } else {
                if (fullNameFieldState == "active") {
                    fullNameHeader.setTextColor(getColorResCompat(R.attr.grey))
                    fullName.setBackgroundResource(R.drawable.snow_night_solid_grey_stroke)
                    fullName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.account_circle_normal,
                        0,
                        0,
                        0
                    )
                    fullName.compoundDrawablePadding =
                        resources.getDimension(R.dimen.padding).toInt()
                    fullNameError.text = ""
                    fullNameError.visibility = View.GONE
                    fullNameFieldState = "normal"
                }
            }
        }

        emailHeader = findViewById(R.id.emailHeader)
        emailError = findViewById(R.id.emailError)
        email = findViewById(R.id.email)
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (emailFieldState == "error" || emailFieldState == "success") {
                    emailCheck()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        email.setOnFocusChangeListener { _, b ->
            if (b) {
                if (emailFieldState == "normal") {
                    emailHeader.setTextColor(getColorResCompat(R.attr.blue))
                    email.setBackgroundResource(R.drawable.snow_night_solid_blue_stroke)
                    email.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.mail_outline_active,
                        0,
                        0,
                        0
                    )
                    email.compoundDrawablePadding =
                        resources.getDimension(R.dimen.padding).toInt()
                    emailError.text = ""
                    emailError.visibility = View.GONE
                    emailFieldState = "active"
                }
            } else {
                if (emailFieldState == "active") {
                    emailHeader.setTextColor(getColorResCompat(R.attr.grey))
                    email.setBackgroundResource(R.drawable.snow_night_solid_grey_stroke)
                    email.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.mail_outline_normal,
                        0,
                        0,
                        0
                    )
                    email.compoundDrawablePadding =
                        resources.getDimension(R.dimen.padding).toInt()
                    emailError.text = ""
                    emailError.visibility = View.GONE
                    emailFieldState = "normal"
                }
            }
        }

        passwordHeader = findViewById(R.id.passwordHeader)
        passwordError = findViewById(R.id.passwordError)
        passwordVisibility = findViewById(R.id.passwordVisibility)
        password = findViewById(R.id.password)
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (passwordFieldState == "error" || passwordFieldState == "success") {
                    passwordCheck()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        password.setOnFocusChangeListener { _, b ->
            if (b) {
                if (passwordFieldState == "normal") {
                    passwordHeader.setTextColor(getColorResCompat(R.attr.blue))
                    password.setBackgroundResource(R.drawable.snow_night_solid_blue_stroke)
                    password.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.lock_active,
                        0,
                        0,
                        0
                    )
                    password.compoundDrawablePadding =
                        resources.getDimension(R.dimen.padding).toInt()
                    if (passwordVisibility.tag == "invisible") {
                        passwordVisibility.setImageResource(R.drawable.invisibility_active)
                    } else {
                        passwordVisibility.setImageResource(R.drawable.visibility_active)
                    }
                    passwordError.text = ""
                    passwordError.visibility = View.GONE
                    passwordFieldState = "active"
                }
            } else {
                if (passwordFieldState == "active") {
                    passwordHeader.setTextColor(getColorResCompat(R.attr.grey))
                    password.setBackgroundResource(R.drawable.snow_night_solid_grey_stroke)
                    password.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.lock_normal,
                        0,
                        0,
                        0
                    )
                    password.compoundDrawablePadding =
                        resources.getDimension(R.dimen.padding).toInt()
                    if (passwordVisibility.tag == "invisible") {
                        passwordVisibility.setImageResource(R.drawable.invisibility_normal)
                    } else {
                        passwordVisibility.setImageResource(R.drawable.visibility_normal)
                    }
                    passwordError.text = ""
                    passwordError.visibility = View.GONE
                    passwordFieldState = "normal"
                }
            }
        }
        passwordVisibility.tag = "invisible"
        passwordVisibility.setOnClickListener {
            PasswordVisibility(
                password, passwordVisibility,
                passwordVisibility.tag as String, passwordFieldState
            ).change()
        }

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestIdToken(resources.getString(R.string.web_client_id))
                .build()
        gsc = GoogleSignIn.getClient(this, gso)

        findViewById<RelativeLayout>(R.id.back_con).setOnClickListener { finish() }
        findViewById<RelativeLayout>(R.id.googleSignUp).setOnClickListener {
            requestCode = 1
            onActivityResult.launch(gsc.signInIntent)
        }
        findViewById<RelativeLayout>(R.id.button).setOnClickListener {
            fullNameCheck()
            emailCheck()
            passwordCheck()
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty() && fullName.text.split(
                    " "
                ).size > 1 && fullName.text.split(" ")[0] != "" && fullName.text.split(
                    " "
                )[1] != ""
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email.text)
                    .matches() && password.text.length >= 8
            ) {
                hideKeyboard()
                if (InternetCheck(this, findViewById(R.id.parent)).status()) {
                    fsl.show()
                    Firebase.auth.createUserWithEmailAndPassword(
                        email.text.toString(),
                        password.text.toString()
                    )
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                join(
                                    Firebase.auth.currentUser?.uid,
                                    fullName.text.toString(),
                                    Firebase.auth.currentUser?.email,
                                )
                            }
                        }.addOnFailureListener(
                            OnFailureListener(
                                this,
                                findViewById(R.id.parent),
                                fsl
                            )
                        )
                }
            }
        }
    }

    private fun fullNameCheck() {
        if (fullName.text.split(" ").size > 1 && fullName.text.split(" ")[0] != "" && fullName.text.split(
                " "
            )[1] != ""
        ) {
            fullNameHeader.setTextColor(getColorResCompat(R.attr.darkGreen))
            fullName.setBackgroundResource(R.drawable.snow_night_solid_dark_green_stroke)
            fullName.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.account_circle_success,
                0,
                0,
                0
            )
            fullName.compoundDrawablePadding =
                resources.getDimension(R.dimen.padding).toInt()
            fullNameError.text = ""
            fullNameError.visibility = View.GONE
            fullNameFieldState = "success"
        } else {
            fullNameHeader.setTextColor(getColorResCompat(R.attr.errorRed))
            fullName.setBackgroundResource(R.drawable.snow_night_solid_error_red_stroke)
            fullName.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.account_circle_error,
                0,
                0,
                0
            )
            fullName.compoundDrawablePadding =
                resources.getDimension(R.dimen.padding).toInt()
            fullNameError.text = getString(R.string.full_name_error_message)
            fullNameError.visibility = View.VISIBLE
            fullNameFieldState = "error"
        }
    }

    private fun emailCheck() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            emailHeader.setTextColor(getColorResCompat(R.attr.darkGreen))
            email.setBackgroundResource(R.drawable.snow_night_solid_dark_green_stroke)
            email.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.mail_outline_success,
                0,
                0,
                0
            )
            email.compoundDrawablePadding =
                resources.getDimension(R.dimen.padding).toInt()
            emailError.text = ""
            emailError.visibility = View.GONE
            emailFieldState = "success"
        } else {
            emailHeader.setTextColor(getColorResCompat(R.attr.errorRed))
            email.setBackgroundResource(R.drawable.snow_night_solid_error_red_stroke)
            email.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.mail_outline_error,
                0,
                0,
                0
            )
            email.compoundDrawablePadding =
                resources.getDimension(R.dimen.padding).toInt()
            emailError.text = getString(R.string.email_error_message)
            emailError.visibility = View.VISIBLE
            emailFieldState = "error"
        }
    }

    private fun passwordCheck() {
        if (password.text.length >= 8) {
            passwordHeader.setTextColor(getColorResCompat(R.attr.darkGreen))
            password.setBackgroundResource(R.drawable.snow_night_solid_dark_green_stroke)
            password.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.lock_success,
                0,
                0,
                0
            )
            password.compoundDrawablePadding =
                resources.getDimension(R.dimen.padding).toInt()
            if (passwordVisibility.tag == "invisible") {
                passwordVisibility.setImageResource(R.drawable.invisibility_success)
            } else {
                passwordVisibility.setImageResource(R.drawable.visibility_success)
            }
            passwordError.text = ""
            passwordError.visibility = View.GONE
            passwordFieldState = "success"
        } else {
            passwordHeader.setTextColor(getColorResCompat(R.attr.errorRed))
            password.setBackgroundResource(R.drawable.snow_night_solid_error_red_stroke)
            password.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.lock_error,
                0,
                0,
                0
            )
            password.compoundDrawablePadding =
                resources.getDimension(R.dimen.padding).toInt()
            if (passwordVisibility.tag == "invisible") {
                passwordVisibility.setImageResource(R.drawable.invisibility_error)
            } else {
                passwordVisibility.setImageResource(R.drawable.visibility_error)
            }
            passwordError.text = getString(R.string.password_error_message)
            passwordError.visibility = View.VISIBLE
            passwordFieldState = "error"
        }
    }

    private fun join(
        user_id: String?,
        fullName: String?,
        email: String?
    ) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                DatabaseConnection(this).join(
                    user_id.toString(),
                    fullName.toString(),
                    email.toString(),
                    task.result,
                    Build.BRAND,
                    Build.MODEL
                )
            }
        }.addOnFailureListener(
            OnFailureListener(
                this,
                findViewById(R.id.parent),
                fsl
            )
        )
    }

    fun joined(l: Int) {
        if (l == 1) {
            startActivity(Intent(this, Home::class.java))
            finish()
        } else {
            gsc.signOut().addOnCompleteListener(this) {
                Firebase.auth.signOut()
            }

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

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }

    private fun getColorResCompat(id: Int): Int {
        val resolvedAttr = TypedValue()
        this.theme.resolveAttribute(id, resolvedAttr, true)
        val colorRes = resolvedAttr.run { if (resourceId != 0) resourceId else data }
        return ContextCompat.getColor(this, colorRes)
    }

    override fun onBackPressed() {
        if (!fsl.isShowing()) {
            finish()
        }
    }
}