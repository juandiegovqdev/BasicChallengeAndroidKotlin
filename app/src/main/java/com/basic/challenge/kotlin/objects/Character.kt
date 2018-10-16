package com.basic.challenge.kotlin.objects

import java.io.FileDescriptor

/*
class Character(
        val id: String,
        val description: String,
        val imageUrl: String,
        val name: String,
        val genre: String)
*/

class Character {

    val id: String = ""
    val description: String = ""
    val imageUrl: String = ""
    val name: String = ""
    val genre: String = ""

    constructor() {}

    constructor(name: String, id: String,
                description: String, imageUrl: String,
                genre: String)

    override fun toString(): String {
        return "Character(id='$id', description='$description', imageUrl='$imageUrl', name='$name', genre='$genre')"
    }


}