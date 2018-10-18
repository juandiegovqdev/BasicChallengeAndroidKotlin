package com.basic.challenge.kotlin.character_details

import android.content.Intent
import android.support.v7.app.ActionBar
import android.widget.ImageView
import android.widget.TextView
import com.basic.challenge.kotlin.objects.Character
import com.squareup.picasso.Picasso

class DetailsPresenter(var detailsView: DetailsView?) {

    // This method sets the character description to a TextView.
    fun setTextDescription(textView: TextView, description: String) {
        textView.setText(description)
    }

    // This method sets the character name to a TextView.
    fun setTextName(textView: TextView, name: String) {
        textView.setText(name)
    }

    // This method gets the character from an Intent.
    // The character that it gets is the one that
    // MainActivity sends when we click on the ListView.
    fun getCharacter(intent: Intent): Character {
        lateinit var character: com.basic.challenge.kotlin.objects.Character
        character = intent.getSerializableExtra("character") as com.basic.challenge.kotlin.objects.Character
        return character
    }

    // This method sets the character image to a ImageView.
    fun setImageCharacter(ivCharacter: ImageView, imageUrl: String) {
        Picasso.get()
                .load(imageUrl)
                .into(ivCharacter)
    }

    // This method sets the character name as the actionBar
    // title.
    fun setTitleActionbar(actionBar: ActionBar?, text: String) {
        actionBar!!.setTitle(text)
    }

    fun onDestroy() {
        detailsView = null
    }
}