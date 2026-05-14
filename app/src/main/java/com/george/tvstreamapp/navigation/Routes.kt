package com.george.tvstreamapp.navigation

object Routes {

    const val SPLASH = "splash"
    const val HOME = "home"

    const val PLAYER = "player/{videoUrl}"

    fun playerRoute(videoUrl: String): String {

        return "player/$videoUrl"
    }
}