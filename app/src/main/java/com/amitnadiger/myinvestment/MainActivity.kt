package com.amitnadiger.myinvestment

import android.app.Application
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview

import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amitnadiger.myinvestment.ui.theme.MyInvestmentTheme

import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.scaffold.ScaffoldImpl
import com.amitnadiger.myinvestment.ui.screens.*
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModelFactory


class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyInvestmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    setupMainScreen()
                }
            }
        }
    }
}

@Composable
fun setupMainScreen() {
    val owner = LocalViewModelStoreOwner.current
    owner?.let {
        val viewModel: FinProductViewModel = viewModel(
            it,
            "FinProductViewModel",
            FinProductViewModelFactory(
                LocalContext.current.applicationContext
                        as Application
            )
        )

        val navController = rememberNavController()
        //ScreenNavigation(navController,viewModel,padding)
        // pass map of configuration of scaffold items
        var showFabButton by rememberSaveable { mutableStateOf(false) }
        var screenConfig by remember { mutableStateOf(
            ScreenConfig(true,
            true,
                true,
                true,
                "",
                "",
                "")) }
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        screenConfig = when (navBackStackEntry?.destination?.route) {
            "home" -> getScreenConfig4Home()
            "addProduct" -> getScreenConfig4AddProduct()
            "searchProduct" -> getScreenConfig4SearchScreen()
            //"searchProduct" -> getScreenConfig4Sea() todo
            else -> {
                Log.e("MainActivity","${navBackStackEntry?.destination?.route}")
                getScreenConfig4ProductDetail()
            }

        }
        ScaffoldImpl(navController = navController,
            viewModel,showFabButton,screenConfig)
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}