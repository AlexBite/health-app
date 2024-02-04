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
import com.example.healthapp.data.PostsRepository
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

/**
 * ViewModel containing the app data and method to retrieve the data
 */
class HealthViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    // изменяемое состояние инициированное объектом Loading
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
                HealthViewModel(postsRepository = postsRepository)
            }
        }
    }
}
