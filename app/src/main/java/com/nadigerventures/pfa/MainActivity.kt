package com.nadigerventures.pfa

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
import androidx.compose.runtime.R

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview

import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nadigerventures.pfa.componentFactory.ComponentInitializer
import com.nadigerventures.pfa.ui.theme.MyInvestmentTheme

import com.nadigerventures.pfa.ui.scaffold.ScaffoldImpl
import com.nadigerventures.pfa.ui.screens.*
import com.nadigerventures.pfa.ui.screens.Faq.getScreenConfig4Faq
import com.nadigerventures.pfa.ui.screens.setting.getScreenConfig4DisplaySetting
import com.nadigerventures.pfa.ui.screens.setting.getScreenConfig4NotificationSetting
import com.nadigerventures.pfa.ui.screens.setting.getScreenConfig4Setting
import com.nadigerventures.pfa.ui.screens.setting.getScreenConfig4UserSetting
import com.nadigerventures.pfa.ui.screens.setting.termandpolicy.getScreenConfig4TCAndPrivacy
import com.nadigerventures.pfa.viewModel.FinHistoryViewModel
import com.nadigerventures.pfa.viewModel.FinHistoryViewModelFactory
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import com.nadigerventures.pfa.viewModel.FinProductViewModelFactory

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
            //Toast.makeText(this, getString(R.string.app_exit_label), Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContent {

            val resourceProvider = ComponentInitializer(context)
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
        Log.i(TAG,"destination = $destination")
        if (destination == "home") {
            BackHandler { finish() }
        }


        screenConfig = when (currentBackStackEntryAsState?.destination?.route) {
            "login" -> {
                getScreenConfig4Login()
            }
            "signUp" -> {
                getScreenConfig4SignUpScreen()
            }
            "signUp/{id}" -> {
                getScreenConfig4SignUpScreen()
            }
            "home" -> {
                getScreenConfig4Home()
            }
            "history" -> {
                getScreenConfig4History()
            }
            "addProduct/{id}/{id1}" -> {
                getScreenConfig4AddProduct()
            }
            "searchProduct" -> {
                getScreenConfig4SearchScreen()
            }
            "productDetail/{id}/{id1}" -> {
                getScreenConfig4ProductDetail()
            }
            "historyDetail/{id}" -> {
                getScreenConfig4HistoryProductDetail()
            }
            "setting" -> {
                getScreenConfig4Setting()
            }
            "privacy" -> {
                getScreenConfig4TCAndPrivacy()
            }
            "faq" -> {
                getScreenConfig4Faq()
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
            else -> {
                getScreenConfig4Home()
            }
        }
        ScaffoldImpl(navController = navController,
            productViewModel,historyViewModel,
            screenConfig,LocalContext.current)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}