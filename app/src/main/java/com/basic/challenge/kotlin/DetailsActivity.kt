package com.basic.challenge.kotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        // Lines nª 17, 18 and 19 enable a button to go back
        val actionBar = actionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Initialize UI Objects
        var character_image: ImageView? = findViewById(R.id.character_image) as ImageView
        var character_name_text: TextView? = findViewById(R.id.character_name_text) as TextView
        var character_description_text: TextView? = findViewById(R.id.character_description_text) as TextView


        //

    }

}
