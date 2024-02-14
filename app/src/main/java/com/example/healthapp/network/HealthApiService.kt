package com.example.healthapp.network

import com.example.healthapp.model.Posts
import retrofit2.http.GET

// интерфейс HTTP запросов
interface HealthApiService {
     @GET("api/v1/feed/all")
    suspend fun getPosts(): List<Posts>
}

