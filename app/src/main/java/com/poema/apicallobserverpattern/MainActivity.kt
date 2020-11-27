package com.poema.apicallobserverpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var myString = ""

    var theString : String by Delegates.observable("this is the string value before it has been set from the api, "){
            property, oldValue, newValue ->
        runOnUiThread {
            displayOldValueToo(oldValue)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataFromGoogle()

    }
    private fun displayOldValueToo(oldValue: String) {
        text_view1.text = oldValue
    }

    fun getDataFromGoogle() {

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"


        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                theString = "..and this is the string after the observable has set it to: ${response.substring(0, 200)}"
                textView2.text = theString
            },
            { error -> text_view1.text = "That didn't work! Error: ${error.message}" })

        queue.add(stringRequest)
    }
}