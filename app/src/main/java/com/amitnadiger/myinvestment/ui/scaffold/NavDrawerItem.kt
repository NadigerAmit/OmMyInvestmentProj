package com.amitnadiger.myinvestment.ui.scaffold

import com.amitnadiger.myinvestment.R
import android.R.drawable.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Home : NavDrawerItem("home",  R.drawable.ic_home,"Home")
    object History : NavDrawerItem("history",   R.drawable.ic_historynew,"History")
    object Profile : NavDrawerItem("profile",   R.drawable.ic_profile,"Profile")
    object Settings : NavDrawerItem("settings",  R.drawable.ic_settings,"Settings")
    object Tc : NavDrawerItem("TC",   R.drawable.ic_tc,"Terms and Conditions")
    object License : NavDrawerItem("license", R.drawable.ic_licensenew,"License")
    object Tutorial : NavDrawerItem("tutorial", R.drawable.ic_tutorils,"Tutorial")

}

