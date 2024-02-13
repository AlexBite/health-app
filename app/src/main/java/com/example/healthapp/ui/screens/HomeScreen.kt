package com.example.healthapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healthapp.R

@Composable
fun HomeScreen (){
    LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                .padding(top = dimensionResource(R.dimen.padding_extralarge))
    ){ item {
        Card(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_large)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_large))
            ) {
                Text(
                    text = stringResource(R.string.hello_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_small)
                    ),
                )
                Text(
                    text = stringResource(R.string.hello_text),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_small),
                    ),
                )
            }
        }
    }
}
}
