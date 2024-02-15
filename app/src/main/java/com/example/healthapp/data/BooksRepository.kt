package com.example.healthapp.data

import com.example.healthapp.model.Book
import com.example.healthapp.network.BooksApiService

// интерфейс извлечения данных в Репозиторий типа List
interface BooksRepository {
    suspend fun getBooks(query: String, orderBy: String, maxResults: Int) : List<Book>

}

class DefaultBooksRepository(
    private val bookApiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(
        query: String,
        orderBy: String,
        maxResults: Int
    ): List<Book> = bookApiService.bookSearch(query, orderBy, maxResults).items.map { items ->
        Book(
            //id = items?.id,
            title = items.volumeInfo?.title,
            previewLink = items.volumeInfo?.previewLink,
            imageLink = items.volumeInfo?.imageLinks?.thumbnail,
            description = items.volumeInfo?.description
        )
    }
}
