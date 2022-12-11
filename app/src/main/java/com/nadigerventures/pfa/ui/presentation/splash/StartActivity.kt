package com.nadigerventures.pfa.ui.presentation.splash




import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope


import com.nadigerventures.pfa.MainActivity
import com.nadigerventures.pfa.componentFactory.ComponentInitializer
import com.nadigerventures.pfa.ui.presentation.welcome.WelcomeActivity

private val TAG = "StartActivity"

class StartActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //Log.i(TAG,"StartActivity called ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //Log.i(TAG,"StartActivity called Build.VERSION.SDK_INT is less than S")
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)
        val resourceProvider = ComponentInitializer(this)
        val startViewModel = resourceProvider.getStartViewModel()
        lifecycleScope.launchWhenCreated {
            var isOnBoardingCompleted = startViewModel.readOnBoardingState()
            //Log.i(TAG,"isOnBoardingCompleted  $isOnBoardingCompleted")
            if (isOnBoardingCompleted) navigateMainActivity() else navigateWelcomeActivity()

        }
    }

    private fun navigateMainActivity() {
        //Log.i(TAG,"StartActivity : navigateMainActivity ")
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateWelcomeActivity() {
        //Log.i(TAG,"StartActivity : navigateWelcomeActivity ")
        val intent = Intent(this@StartActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
