package com.example.zapplication.remote

import com.example.zapplication.entities.Posts
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getPostItems(): List<Posts>
}
