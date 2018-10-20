package com.basic.challenge.kotlin.main

import android.content.Context

interface MainView {
    fun populateList()
    fun getCharacters()
    fun initalizeGestureDetector()
    fun isNetworkAvailable()
    fun askForPermissions()
}