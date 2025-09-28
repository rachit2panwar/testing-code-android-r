package biz.filmeroo.premier.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import biz.filmeroo.premier.api.FilmService
import biz.filmeroo.premier.api.SimilarMovie
import biz.filmeroo.premier.ui.theme.RatingBackground
import biz.filmeroo.premier.ui.theme.StarColor
import biz.filmeroo.premier.ui.theme.TextTertiary
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SimilarMovieCard(
    movie: SimilarMovie,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(144.dp)
            .clickable { onClick(movie.id) }
            .padding(10.dp)
    ) {
        // Movie Poster
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(FilmService.buildImageUrl(movie.posterPath))
                .crossfade(true)
                .build(),
            contentDescription = "Similar movie poster for ${movie.title}",
            modifier = Modifier
                .width(144.dp)
                .height(212.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(10.dp))
        
        // Movie Title
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(144.dp)
        )
        
        // Movie Type (placeholder)
        Text(
            text = "Movie type",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = TextTertiary
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(144.dp)
        )
        
        Spacer(modifier = Modifier.height(5.dp))
        
        // Rating
        Row(
            modifier = Modifier
                .background(
                    color = RatingBackground,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = StarColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = String.format("%.1f", movie.averageVote),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        }
    }
}