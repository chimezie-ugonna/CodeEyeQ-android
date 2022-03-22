package com.codeeyeq.models

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.codeeyeq.R
import com.codeeyeq.activities.GetStarted
import com.codeeyeq.activities.LogIn
import org.json.JSONException
import org.json.JSONObject

class DatabaseConnection(var context: Context) {
    private var requestQue: RequestQueue = Volley.newRequestQueue(context)
    private var url: String = "https://codeeyeq.herokuapp.com/"
    fun join(
        user_id: String,
        full_name: String,
        email: String,
        device_token: String,
        device_brand: String,
        device_model: String
    ) {
        val request = object : StringRequest(Method.POST, "${url}join.php", { r ->
            try {
                val jsonObject = JSONObject(r)
                val response = jsonObject.getString("response")
                if (response == "Done") {
                    if (context is GetStarted) {
                        (context as GetStarted).joined(1)
                    } else if (context is LogIn) {
                        (context as LogIn).joined(1)
                    }
                } else {
                    if (context is GetStarted) {
                        (context as GetStarted).joined(-1)
                    } else if (context is LogIn) {
                        (context as LogIn).joined(-1)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                if (context is GetStarted) {
                    (context as GetStarted).joined(-1)
                } else if (context is LogIn) {
                    (context as LogIn).joined(-1)
                }
            }
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            if (context is GetStarted) {
                (context as GetStarted).joined(-1)
            } else if (context is LogIn) {
                (context as LogIn).joined(-1)
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
}