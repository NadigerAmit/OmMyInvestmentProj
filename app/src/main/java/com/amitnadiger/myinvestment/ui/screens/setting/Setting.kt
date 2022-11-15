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
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig

private val TAG = "SettingPage"

sealed class SettingItem(var route: String , var icon: Int, var title: String) {
    object UserProfileSetting : SettingItem("userSetting",   R.drawable.ic_profile,"User profile settings")
    object DisplaySettings : SettingItem("displaySetting",  R.drawable.ic_displaysetting,"DisplaySettings")
    object Notification : SettingItem("notificationSetting",   R.drawable.ic_notification,"Notifications setting")
    //object Language : SettingItem("profile", R.drawable.ic_language,"Language Settings\n")

}

@Composable
fun SettingPage(navController: NavHostController, paddingValues: PaddingValues) {
    val settingItems = listOf(
        SettingItem.UserProfileSetting,
        SettingItem.DisplaySettings,
        SettingItem.Notification,
   //     SettingItem.Language,
    )
    Column(
       // modifier = Modifier
           // .background(colorResource(id = R.color.white))
    ) {

        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black)
        ) {
            items(settingItems) { items ->
                Log.e(TAG,"SettingPage & items.route - ${items.route}")
                DrawerItem(item = items, selected = currentRoute == items.route, padding = paddingValues , onItemClick = {
                        Log.e(TAG,"SettingPage items.route == signUp")

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
            text = "This is user setting such as :\n " +
                    "User name , DOB Password and Passwd Hint",
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
        topAppBarStartPadding = 40.dp,
        topAppBarTitle = "Settings", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}