package com.basic.challenge.kotlin.character_details

import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView
import android.widget.TextView
import com.basic.challenge.kotlin.R

import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var imageCharacter: ImageView
    lateinit var nameCharacter: TextView
    lateinit var descriptionCharacter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        // Lines nª 17, 18 and 19 enable a button to go back
        val actionBar = actionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Initialize UI Objects
        imageCharacter = findViewById(R.id.character_image) as ImageView
        nameCharacter = findViewById(R.id.character_name_text) as TextView
        descriptionCharacter = findViewById(R.id.character_description_text) as TextView

    }
}
