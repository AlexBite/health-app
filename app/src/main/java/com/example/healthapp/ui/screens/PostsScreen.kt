package com.example.healthapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.healthapp.R
import com.example.healthapp.model.Posts
import com.example.healthapp.ui.theme.HealthAppTheme

// функция отображения начального экрана с постами
@Composable
fun PostsScreen(
    postsUiState: PostsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (postsUiState) {
        is PostsUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is PostsUiState.Success ->
            PostsListScreen(
                posts = postsUiState.posts,
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                contentPadding = contentPadding
            )

        else -> ErrorScreen(retryAction, modifier)
    }
}

// функция отображения экрана до загрузки
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

// функция отображения ошибки при отсутствии данных для отображения с кнопкой перезапуска
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}


// функция отображения каждого поста в карточке
@Composable
fun PostCard(post: Posts, modifier: Modifier = Modifier) {
    // временное состояние кнопки Развернуть
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.post_title, post.name, post.type),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            // отображение фото
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(post.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            // Кнопка развернуть/свернуть
            ExpandButton(
                expanded = expanded,
                onClick = { expanded = !expanded },
            )
            if (expanded) {
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

/**
 * Composable that displays a button that is clickable and displays an expand more or an expand less
 * icon.
 *
 * @param expanded represents whether the expand more or expand less icon is visible
 * @param onClick is the action that happens when the button is clicked
 * @param modifier modifiers to set to this composable
 */
@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = if (expanded) stringResource(R.string.expand_post) else stringResource(R.string.hide_post),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                // .padding(dimensionResource(R.dimen.padding_medium))
                .align(alignment = Alignment.CenterVertically)
        )
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = stringResource(R.string.expand_button_content_description),
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
        }
    }
}

// функция отображения постов в колонке по требованию
@Composable
private fun PostsListScreen(
    posts: List<Posts>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = posts,
            key = { post ->
                post.name
            }
        ) { post ->
            PostCard(post = post, modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    HealthAppTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    HealthAppTheme {
        ErrorScreen({}, Modifier.fillMaxSize())
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PostsListScreenPreview() {
//    HealthAppTheme {
//        val mockData = List(10) {
//            Posts(
//                "Заголовок - $it",
//                "$it",
//                "Какой-то интересный пост. Какой-то интересный пост. " +
//                        " Какой-то интересный пост. Какой-то интересный пост. Какой-то интересный пост. " +
//                        " Какой-то интересный пост. Какой-то интересный пост. " +
//                        " Какой-то интересный пост. А может быть и нет.",
//                imgSrc = ""
//            )
//        }
//        PostsListScreen(mockData, Modifier.fillMaxSize())
//    }
//}
