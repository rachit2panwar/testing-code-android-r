package biz.filmeroo.premier.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import biz.filmeroo.premier.R
import biz.filmeroo.premier.main.FilmViewModel
import biz.filmeroo.premier.ui.components.ErrorMessage
import biz.filmeroo.premier.ui.components.FilmCard
import biz.filmeroo.premier.ui.components.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onFilmClick: (Long) -> Unit,
    viewModel: FilmViewModel = hiltViewModel()
) {
    val filmState by viewModel.filmState.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.top_movies),
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = filmState) {
                is FilmViewModel.FilmState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(
                            items = state.films,
                            key = { film -> film.id }
                        ) { film ->
                            FilmCard(
                                film = film,
                                onClick = onFilmClick
                            )
                        }
                    }
                }
                
                is FilmViewModel.FilmState.Error -> {
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
}