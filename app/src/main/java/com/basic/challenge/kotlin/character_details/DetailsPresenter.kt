package com.basic.challenge.kotlin.character_details

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_female.view.*

class DetailsPresenter(var detailsView: DetailsView?) {

    fun setTextDescription(textView: TextView, description: String) {

    }

    fun setTextName(textView: TextView, name: String) {

    }

    fun setImage(imageUrl: String, imageView: ImageView) {
        Picasso.get().load(imageUrl).into(imageView)
    }

    fun getCharacter(intent : Intent) {

    }

    fun setImageCharacter(){

    }

    fun setNameCharacter(){

    }

    fun setDescriptionCharacter(){

    }

    fun setTitleActionbar(){

    }

    fun onDestroy() {
        detailsView = null
    }
}