package com.basic.challenge.kotlin.character_details

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView
import android.widget.TextView
import com.basic.challenge.kotlin.R
import com.basic.challenge.kotlin.objects.Character

import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(), DetailsView {

    private var presenter = DetailsPresenter(this)

    lateinit var ivCharacter: ImageView
    lateinit var tvNameCharacter: TextView
    lateinit var tvDescriptionCharacter: TextView
    lateinit var character: Character
    var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize our font.
        val myCustomFont: Typeface? = ResourcesCompat.getFont(this, R.font.game_of_thrones)

        ivCharacter = findViewById(R.id.character_image) as ImageView
        tvNameCharacter = findViewById(R.id.character_name_text) as TextView
        tvDescriptionCharacter = findViewById(R.id.character_description_text) as TextView
        tvNameCharacter.setText(character.name)
        tvNameCharacter.typeface = myCustomFont
        tvDescriptionCharacter.setText(character.description)
        tvDescriptionCharacter.typeface = myCustomFont
        actionBar!!.setTitle(character.name)

        getCharacter()
        setImageCharacter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    // This method sets the character image to a ImageView.
    override fun setImageCharacter() {
        presenter.setImageCharacter(ivCharacter, character.imageUrl)
    }

    override fun getCharacter() {
        character = presenter.getCharacter(intent)
    }

}
