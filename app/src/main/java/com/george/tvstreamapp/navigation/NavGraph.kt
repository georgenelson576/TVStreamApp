package com.george.tvstreamapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.george.tvstreamapp.ui.screens.HomeScreen
import com.george.tvstreamapp.ui.screens.PlayerScreen
import com.george.tvstreamapp.ui.screens.SplashScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {

            SplashScreen(

                onFinished = {

                    navController.navigate(Routes.HOME) {

                        popUpTo(Routes.SPLASH) {

                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {

            HomeScreen(navController)
        }

        composable(

            route = Routes.PLAYER,

            arguments = listOf(

                navArgument("videoUrl") {

                    type = NavType.StringType
                }
            )

        ) { backStackEntry ->

            val videoUrl =
                backStackEntry.arguments
                    ?.getString("videoUrl")
                    ?: ""

            PlayerScreen(

                videoUrl = videoUrl,

                onBack = {

                    navController.popBackStack()
                }
            )
        }
    }
}