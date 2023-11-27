package com.example.healthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.healthapp.ui.theme.HealthAppTheme

data class FeedItem(val text: String, val imageUrl: String)

class MainActivity : ComponentActivity() {
    private val feedItems = listOf(
        FeedItem("Hello Android", "https://plus.unsplash.com/premium_photo-1683535508528-9228dcc8fa4c?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
        FeedItem("Hello Compose", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpixabay.com%2Fimages%2Fsearch%2Furl%2F&psig=AOvVaw3-xwPyZSPr32EoyK8Vh29C&ust=1701204392908000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIiNmrCG5YIDFQAAAAAdAAAAABAE")
        // Add more items here
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Feed(feedItems)
                }
            }
        }
    }
}

@Composable
fun Feed(feedItems: List<FeedItem>) {
    LazyColumn {
        items(feedItems) { item ->
            FeedItemView(item)
        }
    }
}

@Composable
fun FeedItemView(item: FeedItem) {
    Row(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = rememberImagePainter(data = item.imageUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(text = item.text)
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    HealthAppTheme {
        Feed(listOf(FeedItem("Hello Android", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpixabay.com%2Fimages%2Fsearch%2Furl%2F&psig=AOvVaw3-xwPyZSPr32EoyK8Vh29C&ust=1701204392908000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIiNmrCG5YIDFQAAAAAdAAAAABAE")))
    }
}
