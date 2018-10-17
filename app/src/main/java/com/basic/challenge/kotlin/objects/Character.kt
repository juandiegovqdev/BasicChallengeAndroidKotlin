package com.basic.challenge.kotlin.objects

import java.io.Serializable

class Character : Serializable {

    var id: String = ""
    var description: String = ""
    var imageUrl: String = ""
    var name: String = ""
    var genre: String = ""

    constructor() {}

    constructor(name: String, id: String,
                description: String, imageUrl: String,
                genre: String)

    override fun toString(): String {
        return "Character(id='$id', description='$description', imageUrl='$imageUrl', name='$name', genre='$genre')"
    }


}