package com.nadigerventures.pfa.ui.scaffold

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.nadigerventures.pfa.R
import kotlinx.coroutines.CoroutineScope

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import com.nadigerventures.pfa.base.launchActivity
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.utility.DataStoreConst
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.nadigerventures.pfa.ui.screens.setting.isProfileImageAvailable
import com.nadigerventures.pfa.ui.screens.setting.pickedImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val TAG = "RichDrawer"
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RichDrawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val dataStorageManager = DataStoreHolder.getDataStoreProvider(
        LocalContext.current,
        DataStoreConst.SECURE_DATASTORE,true)
    var fname:String? = null
    //scope.launch(Dispatchers.Main) {
    runBlocking {
        fname = dataStorageManager.getString(DataStoreConst.FULL_NAME).first()
        if(!isProfileImageAvailable.value ||
            pickedImage.value == null) {
            isProfileImageAvailable.value =
                dataStorageManager.getBool(DataStoreConst.IS_PROFILE_IMAGE_AVAILABLE).first() == true
            if(isProfileImageAvailable.value) {
                if(dataStorageManager.getBool(DataStoreConst.PROFILE_IMAGE_URL).first()!=null) {
                    try {
                        pickedImage.value = Uri.parse(dataStorageManager.getString(DataStoreConst.PROFILE_IMAGE_URL).first()!!)
                    } catch (e:Exception) {
                        Log.e(TAG,"pickedImage.value exception caught "+e.message)
                    }
                }
            }
        }
    }

    val settingItems = listOf(
        NavDrawerItem.Home,
        NavDrawerItem.History,
        NavDrawerItem.Settings,
        //NavDrawerItem.News,
        //NavDrawerItem.Tutorial,
        NavDrawerItem.License,
        NavDrawerItem.Privacy,
        NavDrawerItem.Faq,
        NavDrawerItem.About,
    )
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.teal_700))
    ) {
        // Header
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .fillMaxWidth()
                .padding(5.dp)
              //  .border(width = 2.dp, color = MaterialTheme.colors.primaryVariant)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                if(isProfileImageAvailable.value &&
                    pickedImage.value != null) {
                    val uri = pickedImage.value
                    Log.e(TAG,"Image url = $uri")
                    Image(
                        modifier = Modifier.height(90.dp).width(90.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray).clickable {
                                scope.launch {
                                    navController.navigate(NavRoutes.UserProfileSetting.route) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                    }
                                    scaffoldState.drawerState.close()
                                }
                            },

                        painter = rememberImagePainter(uri),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop
                    )

                } else {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "ProfileUpdate",
                        Modifier.height(90.dp).width(90.dp)
                            // Modifier.fillMaxSize()
                            .clickable {
                                scope.launch {
                                    navController.navigate(NavRoutes.UserProfileSetting.route) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                    }
                                    scaffoldState.drawerState.close()
                                }
                            })
                }

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
                var fullname = fname?:""
                Text(
                    text = fullname,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
            /*
            Spacer(Modifier.weight(1f,true))
                    //.height(15.dp)
            Image(
                painter = painterResource(id = R.drawab le.ic_app_logo),
                contentDescription = R.drawable.logo.toString(),
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
             */

        }

        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        LazyColumn(
            Modifier
                .fillMaxWidth()
        ) {
            items(settingItems) { items ->
                val context = LocalContext.current
                DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {
                    when(items.route) {
                        "license" -> {
                            //Log.i(TAG, "license is triggerred ")
                            // val intent = Intent(context, OssLicensesMenuActivity::class.java)
                            // context.startActivity(intent)
                            context.launchActivity<OssLicensesMenuActivity> { }
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
                                restoreState = true
                            }
                        }
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Nadiger Ventures ",
           // color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
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
           // .background(colorResource(id = background))
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
            fontSize = 20.sp,
          //  color = Color.White
        )
    }
}