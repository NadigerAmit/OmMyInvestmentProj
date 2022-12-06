package com.nadigerventures.pfa.ui.presentation.splash




import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel


import com.nadigerventures.pfa.MainActivity
import com.nadigerventures.pfa.componentFactory.ComponentInitializer
import com.nadigerventures.pfa.ui.presentation.welcome.WelcomeActivity
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import com.nadigerventures.pfa.viewModel.FinProductViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = "StartActivity"

class StartActivity : FragmentActivity() {

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG,"StartActivity called ")
        super.onCreate(savedInstanceState)
        val context = this
        coroutineScope.launch(Dispatchers.Default) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.e(TAG,"StartActivity called Build.VERSION.SDK_INT is less than S")
                val splashScreen = installSplashScreen()
                splashScreen.setKeepOnScreenCondition { true }
            }
            val resourceProvider = ComponentInitializer(context)
            val startViewModel = resourceProvider.getStartViewModel()
            lifecycleScope.launchWhenCreated {
                var isOnBoardingCompleted = startViewModel.readOnBoardingState()
                Log.e(TAG,"isOnBoardingCompleted  $isOnBoardingCompleted")
                if (isOnBoardingCompleted) navigateMainActivity() else navigateWelcomeActivity()
            }
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
