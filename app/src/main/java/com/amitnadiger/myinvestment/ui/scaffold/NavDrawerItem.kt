package com.amitnadiger.myinvestment.ui.scaffold

import com.amitnadiger.myinvestment.R
import android.R.drawable.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Home : NavDrawerItem("home",  R.drawable.ic_home,"Home")
    object History : NavDrawerItem("history",   R.drawable.ic_history,"DeletedHistory")
    //object News : NavDrawerItem("news",   R.drawable.ic_news,"FintechTwitterNews")
    object Settings : NavDrawerItem("setting",  R.drawable.ic_settings,"Settings")
    object Faq : NavDrawerItem("faq",   R.drawable.ic_faq,"FAQ")
    object License : NavDrawerItem("license", R.drawable.ic_licensenew,"License")
    object Privacy : NavDrawerItem("privacy", R.drawable.ic_privacy,"Privacy")
    object About : NavDrawerItem("aboutScreen", R.drawable.ic_about,"About")
    //object Tutorial : NavDrawerItem("tutorial", R.drawable.ic_tutorils,"Tutorial")

}

