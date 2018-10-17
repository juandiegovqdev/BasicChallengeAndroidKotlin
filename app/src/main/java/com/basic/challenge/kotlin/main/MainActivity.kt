package com.basic.challenge.kotlin.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.widget.Toast
import android.view.GestureDetector
import android.widget.ProgressBar
import com.basic.challenge.kotlin.R
import com.basic.challenge.kotlin.character_details.DetailsActivity
import com.basic.challenge.kotlin.interfaces.MockyService
import com.basic.challenge.kotlin.objects.Character;


class MainActivity : AppCompatActivity(), MainView {

    private var presenter = MainPresenter()
    lateinit var rv: RecyclerView
    lateinit var characters : Call<List<Character>>
    lateinit var gestureDetector: GestureDetector
    lateinit var pb : ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pb = findViewById<ContentLoadingProgressBar>(R.id.pb)
        rv = findViewById<RecyclerView>(R.id.rv)

        initalizeGestureDetector()
        getCharacters()
        populateList()
    }

    override fun initalizeGestureDetector() {
        gestureDetector = presenter.initalizeGestureDetector(this)
    }

    override fun getCharacters() {
        characters = presenter.getCharacters()
    }

    override fun populateList() {
        presenter.populateList(rv, this, characters, pb, gestureDetector)
    }

}