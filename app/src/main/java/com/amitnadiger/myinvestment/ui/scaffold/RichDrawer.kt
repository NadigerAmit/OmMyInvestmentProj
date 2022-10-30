package com.amitnadiger.myinvestment.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.navigation.NavController
import com.amitnadiger.myinvestment.R
import kotlinx.coroutines.CoroutineScope

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.Home
import kotlinx.coroutines.launch

@Composable
fun RichDrawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val items = listOf(
        NavDrawerItem.Home,
        NavDrawerItem.History,
        NavDrawerItem.License,
        NavDrawerItem.Tc,
        NavDrawerItem.Profile,
        NavDrawerItem.Settings,
        NavDrawerItem.Tutorial
    )
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.teal_700))
    ) {
        // Header
        Image(
            painter = painterResource(id = R.drawable.logosample),
            contentDescription = R.drawable.logo.toString(),
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
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
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Nadiger Ventures ",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    //val background = if (selected) R.color.colorPrimaryDark else android.R.color.transparent
    val background =   android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.White),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )

        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 30.sp,
            color = Color.White
        )
    }
}
/*
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavDrawerItem.Home.route) {
        composable(NavDrawerItem.Home.route) {
            navController.navigate(NavRoutes.Home.route)
        }
        composable(NavDrawerItem.History.route) {
            navController.navigate(NavRoutes.History.route)
        }
        composable(NavDrawerItem.Tc.route) {
            navController.navigate(NavRoutes.Home.route)
        }
        composable(NavDrawerItem.License.route) {
            navController.navigate(NavRoutes.Home.route)
        }
        composable(NavDrawerItem.Profile.route) {
            navController.navigate(NavRoutes.Home.route)
        }
        composable(NavDrawerItem.Settings.route) {
            navController.navigate(NavRoutes.Home.route)
        }
        composable(NavDrawerItem.Tutorial.route) {
            navController.navigate(NavRoutes.Home.route)
        }
    }
}

 */