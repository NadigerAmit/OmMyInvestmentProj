package com.amitnadiger.myinvestment.ui.presentation.welcome

sealed class WelcomeScreen(val route: String) {
    object OnBoarding : WelcomeScreen(route = "onBoarding_screen")
    object Environment : WelcomeScreen(route = "environment_screen")
}