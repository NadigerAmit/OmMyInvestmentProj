package com.amitnadiger.myinvestment

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview

import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amitnadiger.myinvestment.componentFactory.ComponentInitializer
import com.amitnadiger.myinvestment.ui.theme.MyInvestmentTheme

import com.amitnadiger.myinvestment.ui.scaffold.ScaffoldImpl
import com.amitnadiger.myinvestment.ui.screens.*
import com.amitnadiger.myinvestment.ui.screens.setting.getScreenConfig4DisplaySetting
import com.amitnadiger.myinvestment.ui.screens.setting.getScreenConfig4NotificationSetting
import com.amitnadiger.myinvestment.ui.screens.setting.getScreenConfig4Setting
import com.amitnadiger.myinvestment.ui.screens.setting.getScreenConfig4UserSetting
import com.amitnadiger.myinvestment.ui.screens.setting.termandpolicy.getScreenConfig4TCAndPrivacy
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModelFactory
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModelFactory
import kotlin.system.exitProcess

@SuppressLint("StaticFieldLeak")

private val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"

    private var backPressed = 0L
    private val finish: () -> Unit = {

        if (backPressed + 3000 > System.currentTimeMillis()) {
            finishAndRemoveTask()
            finishAffinity()
            exitProcess(0)
        } else {
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //DataStoreHolder.initializeDataStoreManager(this@MainActivity)
        setContent {

            val resourceProvider = ComponentInitializer(this)
            val themeViewModel = resourceProvider.geThemeViewModel()
            MyInvestmentTheme(darkTheme = themeViewModel.isDarkMode.value) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    setupMainScreen(finish = finish)
                }
            }
        }
    }
}

@Composable
fun setupMainScreen(finish: () -> Unit) {
    val owner = LocalViewModelStoreOwner.current
    owner?.let {
        val productViewModel: FinProductViewModel = viewModel(
            it,
            "FinProductViewModel",
            FinProductViewModelFactory(
                LocalContext.current.applicationContext
                        as Application
            )
        )

        val historyViewModel: FinHistoryViewModel = viewModel(
            it,
            "FinHistoryViewModel",
            FinHistoryViewModelFactory(
                LocalContext.current.applicationContext
                        as Application
            )
        )


        val navController = rememberNavController()
        //ScreenNavigation(navController,viewModel,padding)
        // pass map of configuration of scaffold items
       // var showFabButton by rememberSaveable { mutableStateOf(false) }
        var screenConfig by remember { mutableStateOf(
            ScreenConfig(
                enableTopAppBar = true,
                enableBottomAppBar = true,
                enableDrawer = true,
                enableFab = true,
                screenOnBackPress = "Home",
                topAppBarTitle = "",
                bottomAppBarTitle = "",
                fabString = ""
            )) }
        val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()

        val destination = currentBackStackEntryAsState?.destination?.route
            ?: "home"
         Log.e(TAG,"destination = $destination")
        if (destination == "home") {
            BackHandler { finish() }
        }


        screenConfig = when (currentBackStackEntryAsState?.destination?.route) {
            "login" -> {
                Log.e("MainActivity"," Login ->screen config")
                getScreenConfig4Login()
            }
            "signUp" -> {
                Log.e("MainActivity"," SignUp ->screen config")
                getScreenConfig4SignUpScreen()
            }
            "signUp/{id}" -> {
                Log.e("MainActivity"," SignUp ->screen config")
                getScreenConfig4SignUpScreen()
            }
            "home" -> {
                Log.e("MainActivity"," Home ->screen config")
                getScreenConfig4Home()
            }
            "history" -> {
                Log.e("MainActivity"," History ->screen config")
                getScreenConfig4History()
            }
            "addProduct/{id}" -> {
                Log.e("MainActivity"," addProduct ->screen config")
                getScreenConfig4AddProduct()
            }
            "searchProduct" -> {
                Log.e("MainActivity"," searchProduct ->screen config")
                getScreenConfig4SearchScreen()
            }
            "productDetail/{id}" -> {
                Log.e("MainActivity"," productDetail/{id} ->screen config")
                getScreenConfig4ProductDetail()
            }
            "historyDetail/{id}" -> {
                Log.e("MainActivity"," historyDetail/{id} ->screen config")
                getScreenConfig4HistoryProductDetail()
            }
            "setting" -> {
                Log.e("MainActivity"," setting ->screen config")
                getScreenConfig4Setting()
            }
            "privacy" -> {
                Log.e("MainActivity"," tc ->screen config")
                getScreenConfig4TCAndPrivacy()
            }

            "tutorial" -> {
                Log.e("MainActivity"," tutorial ->screen config")
                getScreenConfig4Tutorial()
            }
            "userSetting" ->{
                getScreenConfig4UserSetting()
            }
            "displaySetting" ->{
                getScreenConfig4DisplaySetting()
            }
            "notificationSetting" ->{
                getScreenConfig4NotificationSetting()
            }
            "aboutScreen" ->{
                getScreenConfig4About()
            }



            //"searchProduct" -> getScreenConfig4Sea() todo
            else -> {
                Log.e("MainActivity"," Else  ->screen config")
                Log.e("MainActivity"," screen config${currentBackStackEntryAsState?.destination?.route}")
                getScreenConfig4Home()
            }

        }
        ScaffoldImpl(navController = navController,
            productViewModel,historyViewModel,
            screenConfig,LocalContext.current)
    }
}

fun getDefaultScreenConfig():ScreenConfig {
    Log.e("MainScreen","getDefaultScreenConfig");
    return ScreenConfig(
        enableTopAppBar = false,
        enableBottomAppBar = false,
        enableDrawer = false,
        enableFab = false,
        topAppBarTitle = "", bottomAppBarTitle = "",
        fabString = "",
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}