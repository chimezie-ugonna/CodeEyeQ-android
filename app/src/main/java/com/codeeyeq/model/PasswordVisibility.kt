package com.codeeyeq.model

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import com.codeeyeq.R

class PasswordVisibility(
    box: EditText,
    img: ImageView,
    status: String,
    state: String
) {
    init {
        if (status == "visible") {
            when (state) {
                "normal" -> {
                    img.setImageResource(R.drawable.invisibility_normal)
                }
                "active" -> {
                    img.setImageResource(R.drawable.invisibility_active)
                }
                "error" -> {
                    img.setImageResource(R.drawable.invisibility_error)
                }
                "success" -> {
                    img.setImageResource(R.drawable.invisibility_success)
                }
            }
            img.tag = "invisible"
            box.transformationMethod = PasswordTransformationMethod()
        } else {
            when (state) {
                "normal" -> {
                    img.setImageResource(R.drawable.visibility_normal)
                }
                "active" -> {
                    img.setImageResource(R.drawable.visibility_active)
                }
                "error" -> {
                    img.setImageResource(R.drawable.visibility_error)
                }
                "success" -> {
                    img.setImageResource(R.drawable.visibility_success)
                }
            }
            img.tag = "visible"
            box.transformationMethod = HideReturnsTransformationMethod()
        }
        box.setSelection(box.text.length)
    }
}