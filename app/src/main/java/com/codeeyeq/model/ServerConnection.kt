package com.codeeyeq.model

import android.content.Context
import android.os.Build
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.codeeyeq.R
import com.codeeyeq.controller.GetStarted
import com.codeeyeq.controller.Home
import com.codeeyeq.controller.LogIn
import org.json.JSONException
import org.json.JSONObject

class ServerConnection(
    var context: Context, var operation: String,
    var method: Int,
    var extension: String,
    var jsonObject: JSONObject,
    var url: String = context.resources.getString(R.string.server_url)
) {

    init {
        val requestQue = Volley.newRequestQueue(context)
        val request = object : JsonObjectRequest(method, "$url$extension", jsonObject, {
            try {
                val status = it.getBoolean("status")
                when (operation) {
                    "createAccount" -> {
                        if (status) {
                            KeyStore(context).encryptData(
                                it.getJSONObject("data").getString("token")
                            )
                            Session(context).appTheme(it.getJSONObject("data").getString("theme"))
                            if (context is GetStarted) {
                                (context as GetStarted).created(1)
                            } else {
                                (context as LogIn).loggedIn(1)
                            }
                        } else {
                            if (context is GetStarted) {
                                (context as GetStarted).created(-1)
                            } else {
                                (context as LogIn).loggedIn(-1)
                            }
                        }
                    }
                    "logIn" -> {
                        if (status) {
                            KeyStore(context).encryptData(
                                it.getJSONObject("data").getString("token")
                            )
                            Session(context).appTheme(it.getJSONObject("data").getString("theme"))
                            if (context is LogIn) {
                                (context as LogIn).loggedIn(1)
                            }
                        } else {
                            if (context is LogIn) {
                                (context as LogIn).loggedIn(-1)
                            }
                        }
                    }
                    "logOut" -> {
                        if (status) {
                            KeyStore(context).deleteKey()
                            if (context is Home) {
                                (context as Home).loggedOut(1)
                            }
                        } else {
                            if (context is Home) {
                                (context as Home).loggedOut(-1)
                            }
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                when (operation) {
                    "createAccount" -> {
                        if (context is GetStarted) {
                            (context as GetStarted).created(-1)
                        } else {
                            (context as LogIn).loggedIn(-1)
                        }
                    }
                    "logIn" -> {
                        if (context is LogIn) {
                            (context as LogIn).loggedIn(-1)
                        }
                    }
                    "logOut" -> {
                        if (context is Home) {
                            (context as Home).loggedOut(-1)
                        }
                    }
                }
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            when (operation) {
                "createAccount" -> {
                    if (context is GetStarted) {
                        (context as GetStarted).created(-1)
                    } else {
                        (context as LogIn).loggedIn(-1)
                    }
                }
                "logIn" -> {
                    if (context is LogIn) {
                        (context as LogIn).loggedIn(-1)
                    }
                }
                "logOut" -> {
                    if (context is Home) {
                        (context as Home).loggedOut(-1)
                    }
                }
            }
        }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()
                if (operation != "createAccount" && operation != "logIn" && Session(context).encryptedTokenIv() != "" && Session(
                        context
                    ).encryptedToken() != ""
                ) {
                    val decryptedData = KeyStore(context).decryptData()
                    header["Authorization"] = "Bearer $decryptedData"
                }
                header["Accept"] = "application/json"
                header["device-token"] = Session(context).deviceToken().toString()
                header["device-brand"] = Build.BRAND
                header["device-model"] = Build.MODEL
                header["app-version"] = context.resources.getString(R.string.app_version)
                header["os-version"] = Build.VERSION.RELEASE
                return header
            }
        }
        requestQue.add(request)
    }
}