package com.codeeyeq

import android.content.Context
import android.view.View
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuthException

class OnFailureListener(val context: Context, val parent: View, private val fsl: FullScreenLoader) :
    OnFailureListener {
    override fun onFailure(p0: Exception) {
        p0.printStackTrace()
        fsl.hide()
        if (p0 is FirebaseAuthException) {
            when (p0.errorCode) {
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL),
                        "error"
                    ).show()
                }
                "ERROR_WEAK_PASSWORD" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_WEAK_PASSWORD),
                        "error"
                    ).show()
                }
                "ERROR_EMAIL_ALREADY_IN_USE" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_EMAIL_ALREADY_IN_USE),
                        "error"
                    ).show()
                }
                "ERROR_CREDENTIAL_ALREADY_IN_USE" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_CREDENTIAL_ALREADY_IN_USE),
                        "error"
                    ).show()
                }
                "ERROR_USER_DISABLED" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_USER_DISABLED),
                        "error"
                    ).show()
                }
                "ERROR_USER_NOT_FOUND" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_USER_NOT_FOUND),
                        "error"
                    ).show()
                }
                "ERROR_USER_TOKEN_EXPIRED" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_USER_TOKEN_EXPIRED),
                        "error"
                    ).show()
                }
                "ERROR_INVALID_CREDENTIAL" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_INVALID_CREDENTIAL),
                        "error"
                    ).show()
                }
                "ERROR_WRONG_PASSWORD" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_WRONG_PASSWORD),
                        "error"
                    ).show()
                }
                "ERROR_OPERATION_NOT_ALLOWED" -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_OPERATION_NOT_ALLOWED),
                        "error"
                    ).show()
                }
                else -> {
                    CustomSnackBar(
                        context,
                        parent,
                        context.resources.getString(R.string.ERROR_UNKNOWN),
                        "error"
                    ).show()
                }
            }
        } else {
            CustomSnackBar(
                context,
                parent,
                context.resources.getString(R.string.ERROR_UNKNOWN),
                "error"
            ).show()
        }
    }

}