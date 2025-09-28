package biz.filmeroo.premier.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Main : Screen("main")
    
    data object FilmDetail : Screen(
        route = "film_detail/{filmId}",
        arguments = listOf(
            navArgument("filmId") {
                type = NavType.LongType
            }
        )
    ) {
        fun createRoute(filmId: Long): String {
            return "film_detail/$filmId"
        }
    }
}