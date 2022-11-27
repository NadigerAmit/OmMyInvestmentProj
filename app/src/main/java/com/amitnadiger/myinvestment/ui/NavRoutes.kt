package com.amitnadiger.myinvestment.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amitnadiger.myinvestment.ui.screens.*
import com.amitnadiger.myinvestment.ui.screens.setting.SettingPage
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.ui.presentation.welcome.WelcomeActivity
import com.amitnadiger.myinvestment.ui.scaffold.NavDrawerItem
import com.amitnadiger.myinvestment.ui.screens.setting.DisplaySetting
import com.amitnadiger.myinvestment.ui.screens.setting.NotificationSetting
import com.amitnadiger.myinvestment.ui.screens.setting.UserProfileSetting
import com.amitnadiger.myinvestment.ui.screens.setting.termandpolicy.TermsAndPrivacyScreen
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.SECURE_DATASTORE
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private const val TAG = "NavRoutes"
sealed class NavRoutes(val route:String) {

     object Login : NavRoutes("login")
     object SignUp : NavRoutes("signUp")
     object Home : NavRoutes("home")
     object AddProduct : NavRoutes("addProduct")
     object SearchProduct : NavRoutes("searchProduct")
     object ProductDetail : NavRoutes("productDetail")
     object History : NavRoutes("history")
     object HistoryProductDetail : NavRoutes("historyDetail")
     object Setting : NavRoutes("setting")
     object Privacy : NavRoutes("privacy")
     object Tutorial : NavRoutes("tutorial")
     object UserProfileSetting : NavRoutes("userSetting")
     object DisplaySetting : NavRoutes("displaySetting")
     object NotificationSetting : NavRoutes("notificationSetting")
     object AboutScreen : NavRoutes("aboutScreen")


}

private var launchScreenCache:String? = null
var isLogInDone:Boolean = false

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

     if(isLogInDone) {
         launchScreenCache = NavRoutes.Home.route
     } else if(launchScreenCache == null || launchScreenCache == NavRoutes.SignUp.route) {
        launchScreenCache = getLaunchScreen(context)
    }

    Log.e(TAG,"Launch screen = $launchScreenCache")
    NavHost(navController = navController, startDestination = launchScreenCache!!) {
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
                accountNumber = accountId!!,padding)
        }

        composable(NavRoutes.HistoryProductDetail.route + "/{id}") { navBackStack ->
            // Extracting the argument
            val accountId = navBackStack.arguments?.getString("id")
            Log.e("MainActvity", "In ScreenNavigation fun , accountId = $accountId ")
            HistoryProductDetail(navController = navController,
                finProductViewModel,
                finHistoryViewModel,
                accountNumber = accountId!!,
                padding)
        }

        composable(NavRoutes.SearchProduct.route) {
            SearchProduct(navController = navController,
                finProductViewModel,
                padding)
        }

        composable(NavRoutes.Setting.route) {
            SettingPage(navController = navController,padding)
        }

        composable(NavRoutes.AboutScreen.route) {
            AboutScreen(navController = navController,padding)
        }

        composable(NavRoutes.Privacy.route) {
            TermsAndPrivacyScreen(navController,padding)
        }


/*
        composable(NavRoutes.License.route) {
            LicensePage(navController = navController,padding)
        }

 */

        composable(NavRoutes.Tutorial.route) {
            Tutorial(navController = navController,padding)
        }

        composable(NavRoutes.UserProfileSetting.route) {
            UserProfileSetting(navController = navController,padding)
        }

        composable(NavRoutes.DisplaySetting.route) {
            DisplaySetting(navController = navController,padding)
        }

        composable(NavRoutes.NotificationSetting.route) {
            NotificationSetting(navController = navController,padding)
        }
    }
}
