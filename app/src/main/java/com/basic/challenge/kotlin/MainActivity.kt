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
import com.basic.challenge.kotlin.adapters.CharactersAdapter
import com.basic.challenge.kotlin.interfaces.MockyService
import com.basic.challenge.kotlin.objects.Character;


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
                                Toast.makeText(this@MainActivity, "The Item Description is: ${response.body()!![position].description}", Toast.LENGTH_SHORT).show()

                                var name : String = response.body()!![position].name
                                var id: String = response.body()!![position].id
                                var desc: String = response.body()!![position].description
                                var imageUrl: String = response.body()!![position].imageUrl
                                var genre: String = response.body()!![position].genre
                                var character : Character = Character(name, name, name, name, name)
                                // character.name = response.body()!![position].name

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