package com.amitnadiger.myinvestment.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amitnadiger.myinvestment.ui.screens.*
import com.amitnadiger.myinvestment.utility.DataStoreManager
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

sealed class NavRoutes(val route:String) {
     object Login : NavRoutes("login")
     object SignUp : NavRoutes("signUp")
     object Home : NavRoutes("home")
     object AddProduct : NavRoutes("addProduct")
     object SearchProduct : NavRoutes("searchProduct")
     object ProductDetail : NavRoutes("productDetail")
}


fun getLaunchScreen():String {
    val dataStorageManager = DataStoreManager.getLocalDataStoreManagerInstance()
    var launchScreen = NavRoutes.SignUp.route
    runBlocking {
        if(dataStorageManager.isRegistrationComplete.first()) {
            launchScreen = if(dataStorageManager.isPasswordProtectionRequired.first()) {
                NavRoutes.Login.route
            } else {
                NavRoutes.Home.route
            }
        }
    }
    return launchScreen
}

@Composable
fun ScreenNavigation(navController: NavHostController, viewModel: FinProductViewModel, padding: PaddingValues) {
    val launchScreen = getLaunchScreen()
    NavHost(navController = navController, startDestination = launchScreen) {
        composable(NavRoutes.Login.route) {
            LoginPage(navController = navController,padding)
        }

        composable(NavRoutes.SignUp.route) {
            SignUpScreen(navController = navController,padding)
        }

        composable(NavRoutes.Home.route) {
            Home(navController = navController, viewModel,padding)
        }

        composable(NavRoutes.AddProduct.route+ "/{id}" ) { navBackStack ->
            val accountId = navBackStack.arguments?.getString("id")
            AddProduct(navController = navController, viewModel,padding, accountNumber = accountId!!)
        }

        composable(NavRoutes.ProductDetail.route + "/{id}") { navBackStack ->
            // Extracting the argument
            val accountId = navBackStack.arguments?.getString("id")
            Log.e("MainActvity", "In ScreenNavigation fun , accountId = $accountId ")
            ProductDetail(navController = navController, viewModel, accountNumber = accountId!!)
        }

        composable(NavRoutes.SearchProduct.route) {
            SearchProduct(navController = navController, viewModel,padding)
        }
    }
}