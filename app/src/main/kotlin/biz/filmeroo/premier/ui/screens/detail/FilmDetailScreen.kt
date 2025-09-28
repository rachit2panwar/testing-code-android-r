package biz.filmeroo.premier.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.FilmService
import biz.filmeroo.premier.detail.FilmDetailViewModel
import biz.filmeroo.premier.ui.components.ErrorMessage
import biz.filmeroo.premier.ui.components.LoadingIndicator
import biz.filmeroo.premier.ui.components.SimilarMovieCard
import biz.filmeroo.premier.ui.theme.AccentPurple
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailScreen(
    filmId: Long,
    onBackClick: () -> Unit,
    onSimilarFilmClick: (Long) -> Unit,
    viewModel: FilmDetailViewModel = hiltViewModel()
) {
    val filmDetailState by viewModel.filmDetailState.observeAsState()
    val similarFilmState by viewModel.filmSimilarState.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.top_movies),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AccentPurple
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        when (val detailState = filmDetailState) {
            is FilmDetailViewModel.FilmState.Success -> {
                val film = detailState.data
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Backdrop Image
                    film.backdropPath?.let { backdropPath ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(FilmService.buildImageUrl(backdropPath))
                                .crossfade(true)
                                .build(),
                            contentDescription = "Movie backdrop for ${film.title}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Movie Title
                    Text(
                        text = film.title,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Movie Overview
                    Text(
                        text = film.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Similar Movies Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Similar movies",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 24.sp
                            )
                        )
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "View all",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = AccentPurple,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "View all",
                                tint = AccentPurple,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Similar Movies List
                    when (val similarState = similarFilmState) {
                        is FilmDetailViewModel.FilmState.Success -> {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp)
                            ) {
                                items(
                                    items = similarState.data.results,
                                    key = { movie -> movie.id }
                                ) { movie ->
                                    SimilarMovieCard(
                                        movie = movie,
                                        onClick = onSimilarFilmClick
                                    )
                                }
                            }
                        }
                        
                        is FilmDetailViewModel.FilmState.Error -> {
                            Text(
                                text = "Failed to load similar movies",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        
                        null -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            is FilmDetailViewModel.FilmState.Error -> {
                ErrorMessage(
                    message = stringResource(R.string.connection_error)
                )
            }
            
            null -> {
                LoadingIndicator()
            }
        }
    }
}