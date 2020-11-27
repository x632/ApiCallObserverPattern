package com.poema.apicallobserverpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var theString : String by Delegates.observable("this is the string value before it has been set from the api, "){
            property, oldValue, newValue ->
        runOnUiThread {
            displayValues(oldValue,newValue)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataFromGoogle()

    }
    private fun displayValues(oldValue: String,newValue: String) {
        text_view1.text = oldValue
        textView2.text =  newValue          //theString
    }

    fun getDataFromGoogle() {

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"


        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                theString = "..and this is the string after the observable has set it to: ${response.substring(0, 200)}"
            },
            { error -> text_view1.text = "That didn't work! Error: ${error.message}" })

        queue.add(stringRequest)
    }
}