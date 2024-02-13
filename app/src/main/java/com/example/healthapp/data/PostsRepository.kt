package com.example.healthapp.data

import com.example.healthapp.model.Posts
import com.example.healthapp.network.HealthApiService

// интерфейс извлечения данных в Репозиторий типа List
interface PostsRepository {
    suspend fun getPosts(): List<Posts> // ассинхронный запрос к ресурсу
}
// реализация интерфейса извлечения данных в Репозиторий
class DefaultPostsRepository(
    private val healthApiService: HealthApiService
) : PostsRepository {
    override suspend fun getPosts(): List<Posts> = healthApiService.getPosts()
}
