package biz.filmeroo.premier.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.filmeroo.premier.ui.screens.detail.FilmDetailScreen
import biz.filmeroo.premier.ui.screens.main.MainScreen

@Composable
fun PremierNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onFilmClick = { filmId ->
                    navController.navigate(Screen.FilmDetail.createRoute(filmId))
                }
            )
        }
        
        composable(
            route = Screen.FilmDetail.route,
            arguments = Screen.FilmDetail.arguments
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getLong("filmId") ?: 0L
            FilmDetailScreen(
                filmId = filmId,
                onBackClick = {
                    navController.popBackStack()
                },
                onSimilarFilmClick = { similarFilmId ->
                    navController.navigate(Screen.FilmDetail.createRoute(similarFilmId))
                }
            )
        }
    }
}