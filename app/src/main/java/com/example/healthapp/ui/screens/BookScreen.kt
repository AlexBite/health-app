package com.example.healthapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.healthapp.R
import com.example.healthapp.model.Book
import com.example.healthapp.ui.theme.HealthAppTheme

// функция отображения Книг для веганов
@Composable
fun BookScreen(
    booksUiState: BooksUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (booksUiState) {
        is BooksUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is BooksUiState.Success ->
            BooksListScreen(
                books = booksUiState.books,
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_small),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small)
                    ),
                contentPadding = contentPadding
            )
        else -> ErrorScreen(retryAction, modifier)
    }
}

// функция отображения каждого поста в карточке
@Composable
fun BookCard(book: Book, modifier: Modifier = Modifier) {
    // временное состояние кнопки Развернуть
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            book.title?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleMedium,
                    //fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // отображение фото
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageLink?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,  // попробовать Crop
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )

            Spacer(modifier = Modifier.weight(1f))
            // Кнопка развернуть/свернуть
            ExpandButton(
                expanded = expanded,
                onClick = { expanded = !expanded },
            )
            if (expanded) {
                book.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
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
}

// функция отображения постов в колонке по требованию
@Composable
private fun BooksListScreen(
    books: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(books) { _, book ->
            BookCard(book = book, modifier)
        }
    }

//    LazyColumn(
//        modifier = modifier,
//        contentPadding = contentPadding,
//        verticalArrangement = Arrangement.spacedBy(24.dp)
//    ) {
//        items(
//            items = books,
//            key = { book ->
//                book.name
//            }
//        ) { book ->
//            BookCard(book = book, modifier = Modifier.fillMaxSize())
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun BooksListScreenPreview() {
    HealthAppTheme {
        val mockData = List(10) {
            Book(
                "Заголовок - $it",
                "",
                "",
                "Описание книги. Описание книги. Описание книги. Описание книги."
            )
        }
        BooksListScreen(mockData, Modifier.fillMaxSize())
    }
}
