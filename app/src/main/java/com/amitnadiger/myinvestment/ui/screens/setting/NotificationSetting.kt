package com.amitnadiger.myinvestment.ui.screens.setting

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.*
import com.amitnadiger.myinvestment.utility.CustomTextField
import com.amitnadiger.myinvestment.utility.DataStoreConst
import com.amitnadiger.myinvestment.utility.DropDownBox
import com.amitnadiger.myinvestment.utility.nod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun NotificationSetting(navController: NavHostController,
                        padding: PaddingValues) {

    val context = LocalContext.current

    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,
        DataStoreConst.SECURE_DATASTORE,true)

    var isNotificationEnable:Boolean? = null


    runBlocking {
        isNotificationEnable = dataStorageManager.getBool(DataStoreConst.IS_NOTIFICATION_ENABLED).first()?:false
        nod.value = dataStorageManager.getString(DataStoreConst.NOTIFICATION_DAYS).first()?:"30"
    }
    var isNotificationEnabled by remember { mutableStateOf(isNotificationEnable?:false) }

    var numOfDays by remember { mutableStateOf(nod.value) }

    val onNotificationEnableFieldChange = { isEnable : Boolean ->
        isNotificationEnabled = isEnable
    }

    val onNumberOfDaysFieldChange = { numberOfDays : String ->
        numOfDays = numberOfDays
        nod.value = numberOfDays
    }

    val displayItemList = listOf(
       // "DisplayImage",
        "uIForNotificationCheckBox",
        "uIForAdvanceDaysBeforeMaturity",
        "uIForSave",
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                //.background(colorResource(id = background))
                // .border(width = 1.dp, color = Color.LightGray)
            ){
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "DisplaySetting",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                    )
                }

            }
        Spacer(modifier = Modifier.width(30.dp).height(30.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            items(displayItemList) { items ->
                when(items) {
                    "DisplayImage"-> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                            //.background(colorResource(id = background))
                            // .border(width = 1.dp, color = Color.LightGray)
                        ){
                            Box(
                                modifier = Modifier.size(110.dp).clip(CircleShape).background(Color.White)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_notification),
                                    contentDescription = "DisplaySetting",
                                    colorFilter = ColorFilter.tint(Color.Black),
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(100.dp)
                                )
                            }

                        }
                        Spacer(modifier = Modifier.width(30.dp).height(30.dp))
                    }
                    "uIForNotificationCheckBox" -> {
                        LabelledCheckboxForNotificationSetting("Enable notification for Matured Records",
                            isNotificationEnabled,
                            onNotificationEnableFieldChange)
                    }
                    "uIForAdvanceDaysBeforeMaturity" -> {
                        NotificationDaysRow("Set advance notifications \n" +
                                "for to-be matured records" +
                                "\nDefault -> 30 days ",numOfDays,onNumberOfDaysFieldChange)
                    }
                    "uIForSave"-> {
                        Spacer(modifier = Modifier
                            .width(40.dp)
                            .height(40.dp))
                        Button(onClick = {
                            saveNotificationInfoInDataStore(context,
                                isNotificationEnabled,
                                numOfDays)
                            navController.navigate(NavRoutes.Setting.route)  {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                            }
                        }) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationDaysRow(FirstColumn: String,numOfDays:String ,onChangeNotification: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Text(FirstColumn.toString(), modifier = Modifier
            .weight(0.2f))

        CustomTextField(modifier = Modifier .width(100.dp)
            .clickable {},
            text = numOfDays,
            placeholder = "Days",
            onChange = onChangeNotification,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next)
    }
}

fun saveNotificationInfoInDataStore(context: Context, isNotificationEnabled:Boolean,
                              NotificationDays:String) {

    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
        context,
        DataStoreConst.SECURE_DATASTORE, true
    )
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    coroutineScope.launch {
        dataStoreProvider.putBool(DataStoreConst.IS_NOTIFICATION_ENABLED, isNotificationEnabled)
        dataStoreProvider.putString(DataStoreConst.NOTIFICATION_DAYS, NotificationDays)
    }
}

@Composable
fun LabelledCheckboxForNotificationSetting(textString:String,
                     isChecked:Boolean,
                     onChange: (Boolean) -> Unit = {},startPaddingForCheckBox: Dp =0.dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        val isChecked = remember { mutableStateOf(isChecked?:false) }

        Text(text = textString, modifier = Modifier.padding(top = 12.dp).weight(0.2f))
        Checkbox(
            modifier = Modifier.padding(end = 40.dp),
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it
                onChange(it)},
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )
        //Text(text = "Enable Password Protection On AppRestart")
    }
}

fun getScreenConfig4NotificationSetting(): ScreenConfig {

    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Setting.route,
        topAppBarStartPadding =0.dp,
        topAppBarTitle = "NotifySettings", bottomAppBarTitle = "",
        fabString = "",
        fabColor = Color.Red
    )
}