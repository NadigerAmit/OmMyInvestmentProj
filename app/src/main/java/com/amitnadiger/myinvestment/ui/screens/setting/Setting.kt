package com.amitnadiger.myinvestment.ui.screens.setting

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.amitnadiger.myinvestment.R

import com.amitnadiger.myinvestment.base.launchActivity
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.presentation.welcome.WelcomeActivity
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig

private val TAG = "SettingPage"

sealed class SettingItem(var route: String , var icon: Int, var title: String
    ,var desc:String) {
    object UserProfileSetting : SettingItem("userSetting",
        R.drawable.ic_profile,
        "UserProfileSettings","Setting such as :\n " +
                "UserName , DOB, Password & PasswdHint")

    object DisplaySettings : SettingItem("displaySetting",
        R.drawable.ic_displaysetting,"DisplaySettings",
        "Description about :\n " +
                "InvestmentRecords Fields,colorCode ,etc")
    object Notification : SettingItem("notificationSetting",
        R.drawable.ic_notification,"NotificationSetting",
        "Setting such as :\n " +
                "Notification ,AdvanceNotificationDays,etc")
    object Introduction : SettingItem("introduction",
        R.drawable.ic_introduction,"OnBoarding\n",
        "Brief description about :\n " +
                "AppUsage/On-boarding description screens")
    /*
    object RateApp : SettingItem("rateApp", R.drawable.ic_rate_app,"RateApp\n",
    "Feedback and Rate this app in play store ")
     */

}

@Composable
fun SettingPage(navController: NavHostController, paddingValues: PaddingValues) {
    val settingItems = listOf(
        SettingItem.UserProfileSetting,
        SettingItem.DisplaySettings,
        SettingItem.Notification,
        SettingItem.Introduction,
       // SettingItem.RateApp,
    )
    Column(
       // modifier = Modifier
           // .background(colorResource(id = R.color.white))
    ) {
        val context = LocalContext.current

        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .border(width = 1.dp, color = Color.Black)
        ) {
            items(settingItems) { items ->
                Log.e(TAG,"SettingPage & items.route - ${items.route}")
                DrawerItem(item = items, selected = currentRoute == items.route, padding = paddingValues , onItemClick = {
                        Log.e(TAG,"SettingPage items.route == signUp")

                    when(items.route) {
                        "introduction" -> {
                            context.launchActivity<WelcomeActivity> { }
                            //(context as WelcomeActivity).finish()
                            //context.getActivity()?.finish()

                        }
                        else -> {
                            navController.navigate(items.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                //restoreState = true
                            }
                        }
                    }
                })
            }
        }
    }
}

@Composable
fun DrawerItem(item: SettingItem, selected: Boolean, padding: PaddingValues,onItemClick: ( SettingItem) -> Unit) {
    //val background = if (selected) R.color.colorPrimaryDark else android.R.color.transparent
    val background =   android.R.color.transparent
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
           // .border(width = 1.dp, color = Color.LightGray)
    ) {
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onItemClick(item) })
                .height(45.dp)
                .background(colorResource(id = background))
                .padding(start = 10.dp)
               // .border(width = 1.dp, color = Color.LightGray)
        ) {

            Box(
                modifier = Modifier.size(30.dp).clip(CircleShape).background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.title,
                    colorFilter = ColorFilter.tint(Color.Black),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = item.title,
                fontSize = 25.sp,
               // color = Color.Black
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "ArrowLeft",
                modifier = Modifier
                    .padding(top = 20.dp)
            )
        }
        Text(
            text = item.desc,
            fontSize = 15.sp,
            //color = Color.Black
           )
    }
}

fun getScreenConfig4Setting(): ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Home.route,
        topAppBarStartPadding = 40.dp,
        topAppBarTitle = "Settings", bottomAppBarTitle = "",
        fabString = "",
        fabColor = Color.Red
    )
}