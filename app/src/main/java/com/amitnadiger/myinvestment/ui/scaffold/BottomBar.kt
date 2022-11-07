package com.amitnadiger.myinvestment.ui.scaffold

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amitnadiger.myinvestment.BaseApplication
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.IS_DARK_MODE
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.SECURE_DATASTORE
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.UNSECURE_DATASTORE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun BottomBar(screenConfig:ScreenConfig,context: Context) {
    // BottomAppBar Composable


    if(screenConfig.enableBottomAppBar) {
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(
                CornerSize(percent = 50)
            ),
        //    backgroundColor = Color.Unspecified,
            content = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Exit")
                Spacer(Modifier.weight(1f,true))
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "ModeUpdate",
                    Modifier.height(40.dp).width(40.dp)
                   // Modifier.fillMaxSize()
                        .clickable {
                            BaseApplication.toggleLightTheme(context)
                        })
            }
        )
    }

}