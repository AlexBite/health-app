package com.example.healthapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.healthapp.R
import com.example.healthapp.ui.HealthAppScreen

// Хедер
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar (
    currentScreen: HealthAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavHostController,
    //modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //if (currentScreen == HealthAppScreen.Start) {
                    Image(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.image_size))
                            .padding(dimensionResource(R.dimen.padding_small)),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null
                    )
                //}
                Text(
                    stringResource(currentScreen.title),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {expanded = true}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Main menu"
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false},
                    modifier = Modifier
                ){
                    DropdownMenuItem (
                        text = {
                            Text(
                                text = "Статьи",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.padding_medium))
                                    .fillMaxSize()
                            ) },
                        onClick = {
                            navController.navigate(HealthAppScreen.Posts.name)
                            expanded = false
                        }
                    )
                    Divider()
                    DropdownMenuItem (
                        text = {
                            Text(
                                text = "Книги",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.padding_medium))
                                    .fillMaxSize()
                            ) },
                        onClick = {
                            navController.navigate(HealthAppScreen.Books.name)
                            expanded = false
                        }
                    )
//                    Divider()
//                    DropdownMenuItem (
//                        text = {
//                            Text(
//                                text = "Видео",
//                                style = MaterialTheme.typography.titleLarge,
//                                fontWeight = FontWeight.Normal,
//                                textAlign = TextAlign.Start,
//                                modifier = Modifier
//                                    .padding(dimensionResource(R.dimen.padding_medium))
//                                    .fillMaxSize()
//                            )},
//                        onClick = {expanded = false}
//                    )
                }
            }
        }
    )
}