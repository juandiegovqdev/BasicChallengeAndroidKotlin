package com.basic.challenge.kotlin.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basic.challenge.kotlin.R
import com.basic.challenge.kotlin.objects.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_female.view.*
import kotlinx.android.synthetic.main.item_male.view.*

class CharactersAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_GENRE_MALE = 0
        const val VIEW_TYPE_GENRE_FEMALE = 1
        const val GENRE_MALE = "MALE"
    }

    private var characters = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {
                VIEW_TYPE_GENRE_MALE -> MaleViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_male, parent, false))
                else -> FemaleViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_female, parent, false))
            }

    override fun getItemCount() = characters.size

    override fun getItemViewType(position: Int) =
            when (characters[position].genre) {
                GENRE_MALE -> VIEW_TYPE_GENRE_MALE
                else -> VIEW_TYPE_GENRE_FEMALE
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_GENRE_MALE -> {
                val maleViewHolder = holder as MaleViewHolder
                maleViewHolder.bindView(characters[position])
            }
            else -> {
                val femaleViewHolder = holder as FemaleViewHolder
                femaleViewHolder.bindView(characters[position])
            }
        }
    }

    fun addAll(collection: List<Character>) {
        characters.clear()
        characters.addAll(collection)
        notifyDataSetChanged()
    }

    private class MaleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(character: Character) {
            itemView.genre_male.text = character.name
            Picasso.get().load(character.imageUrl).into(itemView.image_male)
        }
    }

    private class FemaleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(character: Character) {
            itemView.genre_female.text = character.name
            Picasso.get().load(character.imageUrl).into(itemView.image_female)
        }
    }
}