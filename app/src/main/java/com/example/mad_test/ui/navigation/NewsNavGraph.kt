package com.example.mad_test.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mad_test.data.model.Article
import com.example.mad_test.ui.detail.DetailScreen
import com.example.mad_test.ui.home.HomeScreen
import com.example.mad_test.ui.splash.SplashScreen
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsNavGraph() {
    val navController = rememberNavController()
    val gson = remember { Gson() }

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onTimeout = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }
        composable(Routes.HOME) {
            HomeScreen(onArticleClick = { article ->
                val json = URLEncoder.encode(gson.toJson(article), StandardCharsets.UTF_8.name())
                navController.navigate("${Routes.DETAIL}/$json")
            })
        }
        composable(
            route = "${Routes.DETAIL}/{${Routes.ARG_ARTICLE}}",
            arguments = listOf(navArgument(Routes.ARG_ARTICLE) { type = NavType.StringType })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString(Routes.ARG_ARTICLE).orEmpty()
            val json = URLDecoder.decode(encoded, StandardCharsets.UTF_8.name())
            val article = gson.fromJson(json, Article::class.java)
            DetailScreen(article = article, onBack = { navController.popBackStack() })
        }
    }
}
