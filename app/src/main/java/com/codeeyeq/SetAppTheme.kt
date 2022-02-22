package com.codeeyeq

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

class SetAppTheme(private val context: Context) {
    fun set() {
        when {
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        context.setTheme(R.style.Theme_CodeEyeQ)
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        context.setTheme(R.style.Theme_CodeEyeQ_Night)
                    }
                }
            }
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES -> {
                context.setTheme(R.style.Theme_CodeEyeQ_Night)
            }
            else -> {
                context.setTheme(R.style.Theme_CodeEyeQ)
            }
        }
    }
}