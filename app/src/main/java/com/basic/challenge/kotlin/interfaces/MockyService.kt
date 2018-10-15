package com.basic.challenge.kotlin.interfaces

import com.basic.challenge.kotlin.objects.Character
import retrofit2.Call
import retrofit2.http.GET

interface MockyService {
    @GET("/v2/5bc44e0c3000006600758841")
    fun listCharacters(): Call<List<Character>>
}