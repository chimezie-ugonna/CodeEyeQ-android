package com.codeeyeq.model

import android.content.Context
import android.view.View
import com.codeeyeq.R
import com.codeeyeq.controller.GetStarted
import com.codeeyeq.controller.LogIn
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OnFailureListener(val context: Context, val parent: View, private val fsl: FullScreenLoader) :
    OnFailureListener {
    override fun onFailure(p0: Exception) {
        p0.printStackTrace()
        if (context is GetStarted) {
            context.gsc.signOut().addOnCompleteListener(context) {
                Firebase.auth.signOut()
            }
        } else if (context is LogIn) {
            context.gsc.signOut().addOnCompleteListener(context) {
                Firebase.auth.signOut()
            }
        }
        fsl.hide()
        if (p0 is FirebaseAuthException) {
            when (p0.errorCode) {
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL),
                        "error"
                    )
                }
                "ERROR_WEAK_PASSWORD" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_WEAK_PASSWORD),
                        "error"
                    )
                }
                "ERROR_EMAIL_ALREADY_IN_USE" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_EMAIL_ALREADY_IN_USE),
                        "error"
                    )
                }
                "ERROR_CREDENTIAL_ALREADY_IN_USE" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_CREDENTIAL_ALREADY_IN_USE),
                        "error"
                    )
                }
                "ERROR_USER_DISABLED" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_USER_DISABLED),
                        "error"
                    )
                }
                "ERROR_USER_NOT_FOUND" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_USER_NOT_FOUND),
                        "error"
                    )
                }
                "ERROR_USER_TOKEN_EXPIRED" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_USER_TOKEN_EXPIRED),
                        "error"
                    )
                }
                "ERROR_INVALID_CREDENTIAL" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_INVALID_CREDENTIAL),
                        "error"
                    )
                }
                "ERROR_WRONG_PASSWORD" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_WRONG_PASSWORD),
                        "error"
                    )
                }
                "ERROR_OPERATION_NOT_ALLOWED" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_OPERATION_NOT_ALLOWED),
                        "error"
                    )
                }
                else -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_UNKNOWN),
                        "error"
                    )
                }
            }
        } else {
            CustomSnackBar(
                context,
                parent,
                context.resources.getString(R.string.ERROR_UNKNOWN),
                "error"
            )
        }
    }
}