package com.amitnadiger.myinvestment.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amitnadiger.myinvestment.ui.screens.AddProduct
import com.amitnadiger.myinvestment.ui.screens.Home
import com.amitnadiger.myinvestment.ui.screens.ProductDetail
import com.amitnadiger.myinvestment.ui.screens.SearchProduct
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel

sealed class NavRoutes(val route:String) {
     object Home : NavRoutes("home")
     object AddProduct : NavRoutes("addProduct")
     object SearchProduct : NavRoutes("searchProduct")
     object ProductDetail : NavRoutes("productDetail")
}


@Composable
fun ScreenNavigation(navController: NavHostController, viewModel: FinProductViewModel, padding: PaddingValues) {
    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {

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