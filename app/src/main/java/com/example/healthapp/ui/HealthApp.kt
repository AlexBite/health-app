// Собираем UI

package com.example.healthapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthapp.R
import com.example.healthapp.ui.screens.HealthViewModel
import com.example.healthapp.ui.screens.HomeScreen

// Отображение начальной страницы приложения
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthApp() {
    Scaffold( // слой для шапки профиля
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar( // шапка профиля
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    ) {
        Surface( // основной контейнер для других элементов
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val healthViewModel: HealthViewModel =
                viewModel(factory = HealthViewModel.Factory)
            HomeScreen(
                postsUiState = healthViewModel.postsUiState,
                retryAction = healthViewModel::getPosts,
                modifier = Modifier.fillMaxSize(),
                contentPadding = it
            )
        }
    }
}
