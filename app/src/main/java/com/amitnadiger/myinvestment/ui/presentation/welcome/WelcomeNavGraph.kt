package com.amitnadiger.myinvestment.ui.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding.OnBoardingScreen

@Composable
fun WelcomeNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WelcomeScreen.OnBoarding.route
    ) {
        composable(route = WelcomeScreen.OnBoarding.route) {
            OnBoardingScreen()
        }
    }
}