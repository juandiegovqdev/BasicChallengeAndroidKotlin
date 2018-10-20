package com.basic.challenge.kotlin.main

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import retrofit2.Call
import android.view.GestureDetector
import com.basic.challenge.kotlin.R
import com.basic.challenge.kotlin.objects.Character;
import android.view.Menu;
import android.view.MenuItem;

class MainActivity : AppCompatActivity(), MainView {

    private var presenter = MainPresenter(this)
    lateinit var rv: RecyclerView
    lateinit var characters: Call<List<Character>>
    lateinit var gestureDetector: GestureDetector
    lateinit var pb: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI Objects
        pb = findViewById<ContentLoadingProgressBar>(R.id.pb)
        rv = findViewById<RecyclerView>(R.id.rv)

        // To execute all MainView methods.
        askForPermissions()
        isNetworkAvailable()
        initalizeGestureDetector()
        getCharacters()
        populateList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    // Method to initialize GestureDetector, so that we can handle the
    // OnClick Listener.
    override fun initalizeGestureDetector() {
        gestureDetector = presenter.initalizeGestureDetector(this)
    }

    // Method to get a list with the characters.
    override fun getCharacters() {
        characters = presenter.getCharacters()
    }

    // Method to populate our ListView.
    override fun populateList() {
        presenter.populateList(rv, this, characters, pb, gestureDetector)
    }

    // Methods related to our Menu (MenuInflater).
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val mymenu = menuInflater
        mymenu.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.action_menu_inflater(this, item);
        return false
    }

    // Method to check if our device is connected to Internet.
    override fun isNetworkAvailable() {
        presenter.isNetworkAvailable(this)
    }

    // Method to check if permissions have been granted.
    override fun askForPermissions() {
        presenter.askPermissions(this, this as Activity)
    }

}