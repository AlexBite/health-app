package com.example.healthapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.healthapp.R

@Composable
fun BookDetailScreen(
    detailUiState: HealthUiState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // системная кнопка возврата
    BackHandler {
        onBackPressed()
    }
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                .padding(top = dimensionResource(R.dimen.padding_large))
        ) {
            item {
                BookDetailScreenTopBar(
                    onBackButtonClicked = onBackPressed,
                    detailUiState = detailUiState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(R.dimen.padding_large))
                )
                BookDetailCard(
                    modifier = Modifier,
                    detailUiState = detailUiState
                )
            }
        }
    }
}

//шапка с кнопкой вернуться и обложкой книги
@Composable
private fun BookDetailScreenTopBar(
    onBackButtonClicked: () -> Unit,
    detailUiState: HealthUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackButtonClicked,
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_large))
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back)
            )
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(R.dimen.detail_subject_padding_end))
        ) {
            Spacer(modifier = Modifier.weight(2f))

            // Альтернативная реализация шапки
//            detailUiState.currentSelectedBook.title?.let {
//                Text(
//                    text = it,
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//               )
//            }
            AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                    ,model = ImageRequest.Builder(context = LocalContext.current)
                        .data(detailUiState.currentSelectedBook.imageLink?.replace("http", "https"))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img)
                )
        }
    }
}

// Карточка с описанием книги
@Composable
private fun BookDetailCard(
    modifier: Modifier = Modifier,
    detailUiState: HealthUiState,
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.detail_card_inner_padding)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            detailUiState.currentSelectedBook.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
        }
            detailUiState.currentSelectedBook.description?.let {Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
        }
    }


