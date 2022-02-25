package com.codeeyeq

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    private val spe: SharedPreferences.Editor = sp.edit()

    /*fun appTheme(state: String) {
        spe.putString("appTheme", state)
        spe.commit()
    }

    fun appTheme(): String {
        return sp.getString("appTheme", "").toString()
    }*/
}