package com.basic.challenge.kotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_female.view.*
import kotlinx.android.synthetic.main.item_male.view.*
import android.widget.Toast
import android.view.GestureDetector
import com.google.gson.annotations.SerializedName


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pb = findViewById<ContentLoadingProgressBar>(R.id.pb)
        val rv = findViewById<RecyclerView>(R.id.rv)
        val adp = CharactersAdapter(this)

        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        rv.adapter = adp

        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(MockyService::class.java)

        val characters = service.listCharacters()

        characters.enqueue(object : Callback<List<Character>> {
            override fun onResponse(call: Call<List<Character>>, response: Response<List<Character>>) {
                adp.addAll(response.body()!!.toMutableList())
                pb.hide()

                rv.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                    override fun onTouchEvent(rv: RecyclerView?, motionEvent: MotionEvent?) {}

                    override fun onInterceptTouchEvent(rv: RecyclerView?, motionEvent: MotionEvent?): Boolean {
                        try {
                            val child = rv!!.findChildViewUnder(motionEvent!!.x, motionEvent.y)

                            if (child != null && gestureDetector.onTouchEvent(motionEvent)) {
                                val position = rv.getChildAdapterPosition(child)
                                Toast.makeText(this@MainActivity, "The Item Clicked is: ${response.body()!![position].name}", Toast.LENGTH_SHORT).show()

                                return true
                            }
                        } catch (e: Exception) {
                            println("Error ${e.printStackTrace()}")
                        }

                        return false
                    }

                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

                })
            }

            override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                println("Error $t")
                pb.hide()
            }
        })
    }
}

class Character(
        val id: String,
        val description: String,
        val imageUrl: String,
        val name: String,
        val genre: String)

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

interface MockyService {
    @GET("/v2/5bc44e0c3000006600758841")
    fun listCharacters(): Call<List<Character>>
}