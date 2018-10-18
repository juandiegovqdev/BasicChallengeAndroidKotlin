package com.basic.challenge.kotlin.main

import android.app.Service
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.basic.challenge.kotlin.character_details.DetailsActivity
import com.basic.challenge.kotlin.interfaces.MockyService
import com.basic.challenge.kotlin.objects.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast
import com.basic.challenge.kotlin.Utils
import com.basic.challenge.kotlin.Utils.*
import okhttp3.internal.Util

class MainPresenter(var mainView: MainView?) {

    // With populateList(), we are calling the method from the presenter, so that we populate
    // a RecyclerView with the list of characters that we got from getCharacters()
    fun populateList(rv: RecyclerView, ctx: Context, characters: Call<List<Character>>, pb: ContentLoadingProgressBar, gestureDetector: GestureDetector) {
        val adp = CharactersAdapter(ctx)
        rv.layoutManager = LinearLayoutManager(ctx)
        rv.setHasFixedSize(true)
        rv.adapter = adp

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

                                var character = Character()
                                character.name = response.body()!![position].name
                                character.imageUrl = response.body()!![position].imageUrl
                                character.description = response.body()!![position].description
                                character.id = response.body()!![position].id
                                character.genre = response.body()!![position].genre

                                val intent = Intent(ctx, DetailsActivity::class.java)
                                intent.putExtra("character", character)
                                startActivity(ctx, intent, null)

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

    // With getCharacters(), we are initializing a Retrofit object, so that we get
    // a list populated with all characters.
    fun getCharacters(): Call<List<Character>> {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(MockyService::class.java)
        var characters = service.listCharacters()
        return characters
    }

    // With initializeGestureDetector(), we are initializing a gesture detector,
    // to handle the OnClick Method of the RecyclerView.
    fun initalizeGestureDetector(ctx: Context): GestureDetector {
        var gestureDetector = GestureDetector(ctx, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })
        return gestureDetector
    }

    fun onDestroy() {
        mainView = null
    }
}