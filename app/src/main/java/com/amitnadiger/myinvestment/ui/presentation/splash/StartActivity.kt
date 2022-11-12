package com.amitnadiger.myinvestment.ui.presentation.splash




import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope


import com.amitnadiger.myinvestment.MainActivity
import com.amitnadiger.myinvestment.ui.presentation.welcome.WelcomeActivity
import kotlinx.coroutines.delay

private val TAG = "StartActivity"

class StartActivity : FragmentActivity() {

    // private val viewModel by viewModels<StartViewModel>()
    private var isOnBoardingCompleted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG,"StartActivity called ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.e(TAG,"StartActivity called Build.VERSION.SDK_INT is less than S")
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            Log.e(TAG,"StartActivity more than S calling below code ")
            delay(3000)
            if (isOnBoardingCompleted) navigateMainActivity() else navigateWelcomeActivity()
        }
    }

    private fun navigateMainActivity() {
        Log.e(TAG,"StartActivity : navigateMainActivity ")
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateWelcomeActivity() {
        Log.e(TAG,"StartActivity : navigateWelcomeActivity ")
        val intent = Intent(this@StartActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
