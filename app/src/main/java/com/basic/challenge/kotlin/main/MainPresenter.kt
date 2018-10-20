package com.basic.challenge.kotlin.main

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import com.basic.challenge.kotlin.character_details.DetailsActivity
import com.basic.challenge.kotlin.interfaces.MockyService
import com.basic.challenge.kotlin.objects.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.MenuItem
import com.basic.challenge.kotlin.R
import com.basic.challenge.kotlin.Utils.*
import com.basic.challenge.kotlin.adapters.CharactersAdapter
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings.Global.getString
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.marcoscg.licenser.License
import com.marcoscg.licenser.Library
import com.marcoscg.licenser.LicenserDialog


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

                                var intent = Intent(ctx, DetailsActivity::class.java)
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

    // It handles what the app does when we click any of the menu buttons.
    fun action_menu_inflater(ctx: Context, item: MenuItem) {
        when (item.itemId) {
            R.id.action_menu_licenses -> {
                // showToast(ctx, "Testing our menu")
                showLicenseDialog(ctx)
            }
        }
    }

    fun showLicenseDialog(ctx: Context) {
        LicenserDialog(ctx)
                .setTitle("Licenses")
                // .setCustomNoticeTitle("Notices for files:")
                .setBackgroundColor(Color.WHITE) // Optional
                .setLibrary(Library("Android Support Libraries",
                        "https://developer.android.com/topic/libraries/support-library/index.html",
                        License.APACHE))
                .setLibrary(Library("Example Library",
                        "https://github.com/marcoscgdev",
                        License.APACHE))
                .setLibrary(Library("Licenser",
                        "https://github.com/marcoscgdev/Licenser",
                        License.MIT))
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    // TODO: 11/02/2018
                }
                .show()
    }

    fun isNetworkAvailable(ctx: Context) {
        var cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        var available : Boolean = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        when {
            available == false -> {
                val builder = AlertDialog.Builder(ctx)
                // Set the alert dialog title
                builder.setTitle(ctx.getString(R.string.app_name))
                // Display a message on alert dialog
                builder.setMessage("Internet connection is not available. Please, close the app and connect it.")
                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("YES") { dialog, which ->
                    // Do something when user press the positive button
                    System.exit(0)
                }
                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()
                // Display the alert dialog on app interface
                dialog.show()
            }
        }
    }

    private fun askForPermission(permission: String, requestCode: Int?, ctx: Context, activity: Activity) {
        if (ContextCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode!!)
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode!!)
            }
        } else {
        }
    }

    fun askPermissions(ctx: Context, activity: Activity) {
        askForPermission(Manifest.permission.INTERNET, 1, ctx, activity)
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