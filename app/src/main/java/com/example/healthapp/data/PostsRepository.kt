package com.example.healthapp.data

import com.example.healthapp.model.Posts
import com.example.healthapp.model.Book
import com.example.healthapp.model.books.BookShelf
import com.example.healthapp.network.BooksApiService
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

interface BooksRepository {
    suspend fun getBooks(query: String, maxResults: Int) : List<Book>
}
class DefaultBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String, maxResults: Int) : List<Book>
    = booksApiService.bookSearch(query, maxResults).items.map { items ->
        Book(
            title = items.volumeInfo?.title,
            previewLink = items.volumeInfo?.previewLink,
            imageLink = items.volumeInfo?.imageLinks?.thumbnail
        )
    }
}
