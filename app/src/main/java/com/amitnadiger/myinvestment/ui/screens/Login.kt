package com.amitnadiger.myinvestment.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.theme.Purple700
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.PASSWORD
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.SECURE_DATASTORE
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.UNSECURE_DATASTORE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun LoginPage(navController: NavHostController,paddingValues: PaddingValues) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Sign up here"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
                var triggeringScreen:String? = null
                var launchScreen = NavRoutes.SignUp.route+ "/$triggeringScreen"
                navController.navigate(launchScreen)
            },
            style = TextStyle(
                fontSize = 14.sp,
                //fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple700
            )
        )
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val password = remember { mutableStateOf(TextFieldValue()) }

        //Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))
       // Text(text = "Welcome to MyInvestment",style = TextStyle(fontSize = 25.sp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Login", style = TextStyle(fontSize = 40.sp))

        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })


        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if(isLoginAllowed(password.value.text,context)) {
                        navController.navigate(NavRoutes.Home.route)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp,
                //fontFamily = FontFamily.Default
            )
        )
    }
}

fun isLoginAllowed(passwd:String,context: Context):Boolean {
    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,
        SECURE_DATASTORE,true)
    var isLoginOk = false
    runBlocking {
        Log.e("LogIn","passwd from user = $passwd")
        Log.e("LogIn","passwd from DataStore = ${dataStorageManager.getString(PASSWORD).first()}")
        if(dataStorageManager.getString(PASSWORD).first() == passwd)
            isLoginOk = true
    }
    return isLoginOk
}

fun getScreenConfig4Login():ScreenConfig {
    Log.e("Login","getScreenConfig4Login");
    return ScreenConfig(false,
        false,
        false,
        false,
        "","",
        "",
    )
}