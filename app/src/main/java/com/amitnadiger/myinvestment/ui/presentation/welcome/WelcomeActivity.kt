
package com.amitnadiger.myinvestment.ui.presentation.welcome

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.fragment.app.FragmentActivity
import com.amitnadiger.myinvestment.BaseApplication


import com.amitnadiger.myinvestment.ui.theme.MyInvestmentTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val TAG = "WelcomeActivity"
//@AndroidEntryPoint
class WelcomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate() ")
        setContent {
            WelcomeRoot()
        }
    }
}

@Composable
fun SetupSystemUi(
    systemUiController: SystemUiController,
    systemColor: Color
) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemColor)
    }
}

@Composable
private fun WelcomeRoot() {
    MyInvestmentTheme(darkTheme = BaseApplication.isDark.value) {
        SetupSystemUi(rememberSystemUiController(), MaterialTheme.colors.primary)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            WelcomeNavGraph()
        }
    }
}
