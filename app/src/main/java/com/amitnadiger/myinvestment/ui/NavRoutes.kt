package com.amitnadiger.myinvestment.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amitnadiger.myinvestment.ui.screens.*
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.SECURE_DATASTORE
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private const val TAG = "SecDtaStrProvider"
sealed class NavRoutes(val route:String) {

     object Login : NavRoutes("login")
     object SignUp : NavRoutes("signUp")
     object Home : NavRoutes("home")
     object AddProduct : NavRoutes("addProduct")
     object SearchProduct : NavRoutes("searchProduct")
     object ProductDetail : NavRoutes("productDetail")
     object History : NavRoutes("history")
}

fun getLaunchScreen(context:Context):String {
    Log.i(TAG,"==>  getLaunchScreen ")
    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,SECURE_DATASTORE,true)
    var launchScreen = NavRoutes.SignUp.route
    runBlocking {
        val isRegistrationComplete = dataStorageManager.getBool("IS_REG_COMPLETE").first()
        val isPasswordProtectionRequired = dataStorageManager.getBool("IS_PASS_PROTECTION_REQ")
            .first()


        if(isRegistrationComplete !=null && isRegistrationComplete == true ) {
            launchScreen = if(isPasswordProtectionRequired!=null && isPasswordProtectionRequired == true ) {
                NavRoutes.Login.route
            } else {
                NavRoutes.Home.route
            }
        }
    }
    Log.i(TAG,"getLaunchScreen ==>")
    return launchScreen
}

@Composable
fun ScreenNavigation(navController: NavHostController, finProductViewModel: FinProductViewModel,
                     finHistoryViewModel: FinHistoryViewModel,
                     padding: PaddingValues) {
    val context = LocalContext.current
    val launchScreen = getLaunchScreen(context)
    NavHost(navController = navController, startDestination = launchScreen) {
        composable(NavRoutes.Login.route) {
            LoginPage(navController = navController,padding)
        }

        composable(NavRoutes.SignUp.route) {
            SignUpScreen(navController = navController,padding)
        }

        composable(NavRoutes.Home.route) {
            Home(navController = navController, finProductViewModel,padding)
        }

        composable(NavRoutes.History.route) {
            History(navController = navController, finHistoryViewModel,padding)
        }

        composable(NavRoutes.AddProduct.route+ "/{id}" ) { navBackStack ->
            val accountId = navBackStack.arguments?.getString("id")
            AddProduct(navController = navController,
                finProductViewModel,
                padding, accountNumber = accountId!!)
        }

        composable(NavRoutes.ProductDetail.route + "/{id}") { navBackStack ->
            // Extracting the argument
            val accountId = navBackStack.arguments?.getString("id")
            Log.e("MainActvity", "In ScreenNavigation fun , accountId = $accountId ")
            ProductDetail(navController = navController,
                finProductViewModel,
                finHistoryViewModel,
                accountNumber = accountId!!)
        }

        composable(NavRoutes.SearchProduct.route) {
            SearchProduct(navController = navController,
                finProductViewModel,
                padding)
        }
    }
}