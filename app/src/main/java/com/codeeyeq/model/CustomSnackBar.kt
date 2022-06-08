package com.codeeyeq.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.codeeyeq.R
import com.google.android.material.snackbar.Snackbar

@SuppressLint("InflateParams")
class CustomSnackBar(
    context: Context,
    view: View,
    message: String,
    type: String
) {
    init {
        val snack = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
        val layout = LayoutInflater.from(context).inflate(R.layout.custom_snack_bar, null, false)
        snack.view.setBackgroundColor(Color.TRANSPARENT)
        val snackLayout = snack.view as Snackbar.SnackbarLayout
        snackLayout.setPadding(0, 0, 0, 0)
        val parent = layout.findViewById<RelativeLayout>(R.id.parent)
        val img = layout.findViewById<ImageView>(R.id.img)
        val text = layout.findViewById<TextView>(R.id.text)
        if (type == "error") {
            parent.setBackgroundResource(R.drawable.error_red_solid)
            img.setImageResource(R.mipmap.error_clear)
            img.setOnClickListener { snack.dismiss() }
        } else {
            parent.setBackgroundResource(R.drawable.success_green_solid)
            img.setImageResource(R.mipmap.success_check)
        }
        text.text = message
        snackLayout.addView(layout, 0)
        snack.show()
    }
}