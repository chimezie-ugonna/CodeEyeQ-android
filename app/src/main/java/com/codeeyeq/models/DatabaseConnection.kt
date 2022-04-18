package com.codeeyeq.models

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.codeeyeq.R
import com.codeeyeq.activities.GetStarted
import com.codeeyeq.activities.Home
import com.codeeyeq.activities.LogIn
import org.json.JSONException
import org.json.JSONObject

class DatabaseConnection(var context: Context) {
    private var requestQue: RequestQueue = Volley.newRequestQueue(context)
    private var url: String = "https://codeeyeq.herokuapp.com/api/v1/"

    fun createAccount(
        user_id: String,
        full_name: String,
        email: String,
        device_token: String,
        device_brand: String,
        device_model: String
    ) {
        val request = object : StringRequest(Method.POST, "${url}create_account", { r ->
            try {
                val jsonObject = JSONObject(r)
                val status = jsonObject.getInt("status")
                if (status in 200..299) {
                    if (context is GetStarted) {
                        (context as GetStarted).created(1)
                    } else if (context is LogIn) {
                        (context as LogIn).loggedIn(1)
                    }
                } else {
                    if (context is GetStarted) {
                        (context as GetStarted).created(-1)
                    } else if (context is LogIn) {
                        (context as LogIn).loggedIn(-1)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                if (context is GetStarted) {
                    (context as GetStarted).created(-1)
                } else if (context is LogIn) {
                    (context as LogIn).loggedIn(-1)
                }
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            if (context is GetStarted) {
                (context as GetStarted).created(-1)
            } else if (context is LogIn) {
                (context as LogIn).loggedIn(-1)
            }
        }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["user_id"] = user_id
                params["full_name"] = full_name
                params["email"] = email
                params["device_token"] = device_token
                params["device_brand"] = device_brand
                params["device_model"] = device_model
                params["app_version"] = context.resources.getString(R.string.app_version)
                params["os_version"] = android.os.Build.VERSION.RELEASE
                return params
            }
        }
        requestQue.add(request)
    }

    fun logIn(
        user_id: String,
        device_token: String,
        device_brand: String,
        device_model: String
    ) {
        val request = object : StringRequest(Method.POST, "${url}log_in", { r ->
            try {
                val jsonObject = JSONObject(r)
                val status = jsonObject.getInt("status")
                if (status in 200..299) {
                    if (context is LogIn) {
                        (context as LogIn).loggedIn(1)
                    }
                } else {
                    if (context is LogIn) {
                        (context as LogIn).loggedIn(-1)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                if (context is LogIn) {
                    (context as LogIn).loggedIn(-1)
                }
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            if (context is LogIn) {
                (context as LogIn).loggedIn(-1)
            }
        }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["user_id"] = user_id
                params["device_token"] = device_token
                params["device_brand"] = device_brand
                params["device_model"] = device_model
                params["app_version"] = context.resources.getString(R.string.app_version)
                params["os_version"] = android.os.Build.VERSION.RELEASE
                return params
            }
        }
        requestQue.add(request)
    }

    fun logOut(
        user_id: String
    ) {
        val request = object : StringRequest(Method.GET, "${url}log_out?user_id=${user_id}", { r ->
            try {
                val jsonObject = JSONObject(r)
                val status = jsonObject.getInt("status")
                if (status in 200..299) {
                    if (context is Home) {
                        (context as Home).loggedOut(1)
                    }
                } else {
                    if (context is Home) {
                        (context as Home).loggedOut(-1)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                if (context is Home) {
                    (context as Home).loggedOut(-1)
                }
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            if (context is Home) {
                (context as Home).loggedOut(-1)
            }
        }
        ) {

        }
        requestQue.add(request)
    }
}