package com.example.healthapp.network

import com.example.healthapp.model.books.BookShelf
import retrofit2.http.GET
import retrofit2.http.Query

// запрос поиска книг с GoogleBooks
interface BooksApiService {
    @GET("volumes")
    suspend fun bookSearch(
        @Query("q") searchQuery: String,
        @Query("maxResults") maxResults: Int
    ): BookShelf
}