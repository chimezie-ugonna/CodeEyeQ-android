package com.codeeyeq

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class SetAppTheme(private val context: Context) {
    fun set() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            context.setTheme(R.style.Theme_CodeEyeQ_Night)
        } else {
            context.setTheme(R.style.Theme_CodeEyeQ)
        }
    }
}