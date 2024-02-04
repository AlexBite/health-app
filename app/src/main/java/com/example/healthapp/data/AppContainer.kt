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
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */

// Реализация контейнера по умолчанию
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/" // путь к данным

    // создаем объект Retrofit для HTTP запросов
    private val retrofit: Retrofit = Retrofit.Builder()
        // добавляем конвертор сериализации для конвертации JSON в Kotlin
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    // создаем объект для api-запросов (в нашем случае это GET-запрос)
    private val retrofitService: HealthApiService by lazy { // используем создание по требованию
        retrofit.create(HealthApiService::class.java)
    }

    // реализация интерфейса контейнера
    override val postsRepository: PostsRepository by lazy { // используем создание по требованию
        DefaultPostsRepository(retrofitService) // передаем данные в контейнер
    }
}

//class BooksAppContainer : AppContainer {
//    private val BASE_URL = "https://www.googleapis.com/books/v1/" // путь к данным
//
//    // создаем объект Retrofit для HTTP запросов
//    private val retrofit: Retrofit = Retrofit.Builder()
//        // добавляем конвертор сериализации для конвертации JSON в Kotlin
//        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
//        .baseUrl(BASE_URL)
//        .build()
//
//    // создаем объект для api-запросов (в нашем случае это GET-запрос)
//    private val retrofitService: BooksApiService by lazy { // используем создание по требованию
//        retrofit.create(BooksApiService::class.java)
//    }
//
//    // реализация интерфейса контейнера
//    override val booksRepository: BooksRepository by lazy { // используем создание по требованию
//        DefaultBooksRepository(retrofitService) // передаем данные в контейнер
//    }
//}