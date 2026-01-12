package ru.ae.watchme.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.ae.watchme.R

@Composable
fun MovieCard(
    movieModel: MovieModel,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = movieModel.image,
                contentDescription = "Постер фильма",
                modifier = Modifier
                    .size(125.dp, 175.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.movie_poster_placeholder)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = movieModel.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Рейтинг: ${movieModel.rating}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(80.dp))
                SuggestionChip(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { /* TODO: Закешировать */ },
                    label = { Text(text = "Хочу посмотреть") }
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(
        movieModel = MovieModel(
            id = 1,
            title = "Гарри Поттер и что-то там",
            image = "...",
            rating = 5.0
        ),
        onClick = {}
    )
}

// TODO: Вынести в отдельный пакет
data class MovieModel(
    val id: Int,
    val title: String,
    val image: String,
    val rating: Double
)