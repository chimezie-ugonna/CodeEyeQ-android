package com.codeeyeq.model

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.codeeyeq.R
import com.codeeyeq.controller.activity.Home

class SetAppTheme(context: Context) {
    init {
        when (Session(context).appTheme()) {
            "system" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        when {
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        if (context is Home) {
                            context.setTheme(R.style.Theme_CodeEyeQ_Home)
                        } else {
                            context.setTheme(R.style.Theme_CodeEyeQ)
                        }
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
                if (context is Home) {
                    context.setTheme(R.style.Theme_CodeEyeQ_Home)
                } else {
                    context.setTheme(R.style.Theme_CodeEyeQ)
                }
            }
        }
    }
}