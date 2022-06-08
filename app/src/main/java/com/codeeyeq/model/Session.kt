package com.codeeyeq.model

import android.content.Context
import android.content.SharedPreferences
import com.codeeyeq.R

class Session(context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    private val spe: SharedPreferences.Editor = sp.edit()

    fun appTheme(state: String) {
        spe.putString("appTheme", state)
        spe.commit()
    }

    fun appTheme(): String? {
        return sp.getString("appTheme", "system")
    }

    fun deviceToken(data: String) {
        spe.putString("deviceToken", data)
        spe.commit()
    }

    fun deviceToken(): String? {
        return sp.getString("deviceToken", "")
    }

    fun encryptedTokenIv(data: String) {
        spe.putString("encryptedTokenIv", data)
        spe.commit()
    }

    fun encryptedTokenIv(): String? {
        return sp.getString("encryptedTokenIv", "")
    }

    fun encryptedToken(data: String) {
        spe.putString("encryptedToken", data)
        spe.commit()
    }

    fun encryptedToken(): String? {
        return sp.getString("encryptedToken", "")
    }
}