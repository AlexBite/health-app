package com.example.healthapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.healthapp.HealthApplication
import com.example.healthapp.data.BooksRepository
import com.example.healthapp.data.PostsRepository
import com.example.healthapp.model.Book
import com.example.healthapp.model.Posts
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Состояния для UI на Домашнем экране

// изолированный интерфейс состояния для постов
sealed interface PostsUiState {
    data class Success(val posts: List<Posts>) : PostsUiState
    object Error : PostsUiState
    object Loading : PostsUiState
}

// изолированный интерфейс состояния для книг
sealed interface BooksUiState {
    data class Success(val books: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

// модель общих состояний UI
//data class HealthUiState(
//    val postExpanded: Boolean = false,
//    val menuExpanded: Boolean = false,
//)
/**
 * ViewModel containing the app data and method to retrieve the data + UI states
 */
class HealthViewModel(
    private val postsRepository: PostsRepository,
    private val booksRepository: BooksRepository
) : ViewModel() {

    // Общие состояния UI
//    private val _uiState = MutableStateFlow(HealthUiState())
//    val uiState: StateFlow<HealthUiState> = _uiState.asStateFlow()

    // изменяемое состояние инициированное объектом Loading для отображения постов
    var postsUiState: PostsUiState by mutableStateOf(PostsUiState.Loading)
        private set // состояние устанавливается только из класса

    // инициализация функции извлечения постов при создании ViewModel
    init {
        getPosts()
    }
    // функция изменения
    fun getPosts() {
        viewModelScope.launch {
            postsUiState = PostsUiState.Loading
            postsUiState = try {
                PostsUiState.Success(postsRepository.getPosts())
            } catch (e: IOException) { // ловим общие ошибки при GET-запросе
                PostsUiState.Error  // выводим сообщение об ошибке
            } catch (e: HttpException) { // ловим общие ошибки при GET-запросе
                PostsUiState.Error // выводим сообщение об ошибке
            }
        }
    }

    // изменяемое состояние инициированное объектом Loading для отображения книг
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set // состояние устанавливается только из класса

    // инициализация функции извлечения постов при создании ViewModel
    init {
        getBooks()
    }
    // функция изменения
    fun getBooks() {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
                BooksUiState.Success(booksRepository.getBooks(query = "vegan", maxResults = 20))
            } catch (e: IOException) { // ловим общие ошибки при GET-запросе
                BooksUiState.Error  // выводим сообщение об ошибке
            } catch (e: HttpException) { // ловим общие ошибки при GET-запросе
                BooksUiState.Error // выводим сообщение об ошибке
            }
        }
    }

    /**
     * Factory for HealthViewModel который берет HealthRepository как зависимость
     */
    // создаем объект Factory для передачи в конструктор ViewModel при создании, так как напрямую передавать значения нельзя
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as HealthApplication)
                val postsRepository = application.container.postsRepository
                val booksRepository = application.container.booksRepository
                HealthViewModel(postsRepository = postsRepository, booksRepository = booksRepository)
            }
        }
    }
}
