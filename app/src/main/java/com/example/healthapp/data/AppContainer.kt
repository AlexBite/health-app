package com.example.healthapp.data

import com.example.healthapp.network.BooksApiService
import com.example.healthapp.network.HealthApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Контейнер для зависимостей на уровне приложения, чтобы передавать в ViewModel

// Интерфейс контейнера
interface AppContainer {
    val postsRepository: PostsRepository
    val booksRepository: BooksRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */

// Реализация контейнера по умолчанию
class DefaultAppContainer : AppContainer {

    private val BASE_URL = "http://89.111.169.216/"
    private val BASE_URL1 = "https://www.googleapis.com/books/v1/"

    // создаем объект Retrofit для HTTP запросов
    private val retrofit: Retrofit = Retrofit.Builder()
        // добавляем конвертор сериализации для конвертации JSON в Kotlin
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()
    private val retrofit1: Retrofit = Retrofit.Builder()
        // добавляем конвертор сериализации для конвертации JSON в Kotlin
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL1)
        .build()

    // создаем объект для api-запросов (в нашем случае это GET-запрос)
    private val retrofitService: HealthApiService by lazy { // используем создание по требованию
        retrofit.create(HealthApiService::class.java)
    }
    private val retrofitService1: BooksApiService by lazy { // используем создание по требованию
        retrofit1.create(BooksApiService::class.java)
    }

    // реализация интерфейса контейнера
    override val postsRepository: PostsRepository by lazy { // используем создание по требованию
        DefaultPostsRepository(retrofitService) // передаем данные в контейнер
    }
    override val booksRepository: BooksRepository by lazy { // используем создание по требованию
        DefaultBooksRepository(retrofitService1) // передаем данные в контейнер
    }
}