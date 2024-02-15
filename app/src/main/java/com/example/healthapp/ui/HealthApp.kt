// Собираем UI

package com.example.healthapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthapp.R
import com.example.healthapp.ui.screens.BookScreen
import com.example.healthapp.ui.screens.HealthViewModel
import com.example.healthapp.ui.screens.HomeScreen
import com.example.healthapp.ui.screens.TopBar
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthapp.model.Book
import com.example.healthapp.ui.screens.PostsScreen

// перечень экранов
enum class HealthAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Posts(title = R.string.posts),
    Books(title = R.string.books),
    //Video(title = R.string.video),
}

// Отображение страниц приложения
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = HealthAppScreen.valueOf(
        backStackEntry?.destination?.route ?: HealthAppScreen.Start.name
    )
    val healthViewModel: HealthViewModel =
        viewModel(factory = HealthViewModel.Factory)

    // для хранения состояния Деталей
    val detailUiState = healthViewModel.uiState.collectAsState().value

    Scaffold(
        modifier = Modifier
            //.nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = { TopBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            navController = navController
        ) }
    ) { innerPadding ->
        Surface( // основной контейнер для других элементов
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = HealthAppScreen.Start.name,
                modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(innerPadding)
            ) {
                composable(route = HealthAppScreen.Start.name,) {
                    HomeScreen()
                }
                composable(route = HealthAppScreen.Posts.name,) {
                    PostsScreen(
                        postsUiState = healthViewModel.postsUiState,
                        retryAction = healthViewModel::getPosts,
                        modifier = Modifier.fillMaxSize(),
                        //contentPadding = innerPadding
                    )
                }
                composable(route = HealthAppScreen.Books.name) {
                    BookScreen(
                        booksUiState = healthViewModel.booksUiState,
                        retryAction = healthViewModel::getBooks,
                        modifier = Modifier.fillMaxSize(),
                        //contentPadding = innerPadding
                        detailUiState = detailUiState,
                        onBookCardPressed = { book: Book ->
                            healthViewModel.updateDetailScreenStates(
                                book = book
                            )
                        },
                        onDetailScreenBackPressed = {
                            healthViewModel.resetBookScreenStates()
                        },
                    )
                }
            }
        }
    }
}

