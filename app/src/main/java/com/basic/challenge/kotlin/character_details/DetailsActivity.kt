package com.basic.challenge.kotlin.character_details

import android.os.Bundle
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

        // Both lines of code set a back button to go back
        // to MainActivity.
        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize UI Objects
        ivCharacter = findViewById(R.id.character_image) as ImageView
        tvNameCharacter = findViewById(R.id.character_name_text) as TextView
        tvDescriptionCharacter = findViewById(R.id.character_description_text) as TextView

        getCharacter()
        setTitleActionbar()
        setImageCharacter()
        setTextName()
        setTextDescription()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setTextDescription() {
        presenter.setTextDescription(tvDescriptionCharacter, character.description, this)
    }

    override fun setTextName() {
        presenter.setTextName(tvNameCharacter, character.name, this)
    }

    // This method sets the character image to a ImageView.
    override fun setImageCharacter() {
        presenter.setImageCharacter(ivCharacter, character.imageUrl)
    }

    override fun getCharacter() {
        character = presenter.getCharacter(intent)
    }

    override fun setTitleActionbar() {
        presenter.setTitleActionbar(actionBar, character.name)
    }

}
