package com.nadigerventures.pfa.ui.scaffold

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController

import com.nadigerventures.pfa.base.getActivity
import com.nadigerventures.pfa.componentFactory.ComponentInitializer

import com.nadigerventures.pfa.ui.screens.LabelledCheckbox
import com.nadigerventures.pfa.ui.screens.ScreenConfig

import com.nadigerventures.pfa.viewModel.ThemeViewModel
import kotlin.system.exitProcess


private val TAG ="BottomBar"
 private fun toggleTheme(context: Context,
                         isEnable:Boolean,
                         themeViewModel: ThemeViewModel) {
     themeViewModel.saveThemeState(isEnable)
 }

private var backPressed = 0L

private fun handleHomeScreenExit(context: Context) {
    //Log.i(TAG,"finish called")
    if (backPressed + 3000 > System.currentTimeMillis()) {
        //Log.i(TAG,"finish called ${context.getActivity().toString()}")
        context.getActivity()?.finishAndRemoveTask()
        context.getActivity()?.finishAffinity()
        exitProcess(0)
        backPressed = 0L
    } else {
        Toast.makeText(context, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
    }
    backPressed = System.currentTimeMillis()
}
@Composable
fun BottomBar(screenConfig:ScreenConfig,
              context: Context,
              navController: NavHostController
) {

    val resourceProvider = ComponentInitializer(context)
    val themeViewModel = resourceProvider.geThemeViewModel()

    val onDisplayModeChange = { isEnable : Boolean ->
        toggleTheme(context,isEnable,themeViewModel)
    }

    val onBackArrowPressed  = {
        Log.e(TAG,"screenConfig.screenOnBackPress  ${screenConfig.screenOnBackPress}")
        when(screenConfig.screenOnBackPress) {
            "ExitApp" -> {
                handleHomeScreenExit(context)
            }
            else -> {
                navController.navigate(screenConfig.screenOnBackPress) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                }
            }
        }
    }


    if(screenConfig.enableBottomAppBar) {
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(
                CornerSize(percent = 50)
            ),
        //    backgroundColor = Color.Unspecified,
            content = {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Exit",
                     modifier = Modifier.clickable(onClick = onBackArrowPressed)
                    )

                Spacer(Modifier.weight(1f,true))
                LabelledCheckbox("",
                    themeViewModel.isDarkMode.value,
                    onDisplayModeChange)
                /*
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "ModeUpdate",
                    Modifier.height(40.dp).width(40.dp)
                   // Modifier.fillMaxSize()
                        .clickable {
                            BaseApplication.toggleLightTheme(context)
                        })

                 */
            }
        )
    }

}