package com.basic.challenge.kotlin.character_details

import android.media.Image
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView
import android.widget.TextView
import com.basic.challenge.kotlin.R
import com.basic.challenge.kotlin.main.MainPresenter
import com.basic.challenge.kotlin.objects.Character
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_female.view.*

class DetailsActivity : AppCompatActivity(), DetailsView {

    private var presenter = DetailsPresenter(this)

    lateinit var imageCharacter: ImageView
    lateinit var nameCharacter: TextView
    lateinit var descriptionCharacter: TextView
    lateinit var character: Character
    var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        character = intent.getSerializableExtra("character") as Character

        // Lines nª 17, 18 and 19 enable a button to go back
        // val actionBar = actionBar
        // actionBar!!.setHomeButtonEnabled(true)
        // actionBar.setDisplayHomeAsUpEnabled(true)

        // Initialize UI Objects
        imageCharacter = findViewById(R.id.character_image) as ImageView
        nameCharacter = findViewById(R.id.character_name_text) as TextView
        descriptionCharacter = findViewById(R.id.character_description_text) as TextView

        actionBar!!.setTitle(character.name)

        Picasso.get()
                .load(character.imageUrl)
                .into(imageCharacter)


        nameCharacter.setText(character.name)
        descriptionCharacter.setText(character.description)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setTextDescription() {

    }

    override fun setTextName() {

    }

    override fun setImage() {

    }

    override fun getCharacter() {

    }
}
