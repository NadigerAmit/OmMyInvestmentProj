package com.amitnadiger.myinvestment.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun LicensePage(navController: NavHostController, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val intent = Intent(context, OssLicensesMenuActivity::class.java)
    context.startActivity(intent)
}

fun getScreenConfig4License():ScreenConfig {
    //Log.e("HomeScvreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        topAppBarTitle = "Licenses",
        bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}
