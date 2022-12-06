package com.nadigerventures.pfa.componentFactory

import android.app.Application
import android.content.Context
import android.util.Log
import com.nadigerventures.pfa.repository.ThemeRepository
import com.nadigerventures.pfa.repository.WelcomeRepository
import com.nadigerventures.pfa.ui.presentation.theme.ReadTheme
import com.nadigerventures.pfa.ui.presentation.theme.SaveTheme
import com.nadigerventures.pfa.ui.presentation.welcome.onBoarding.ReadOnBoarding
import com.nadigerventures.pfa.ui.presentation.welcome.onBoarding.SaveOnBoarding
import com.nadigerventures.pfa.viewModel.*

private val TAG = "ComponentInitializer"

class ComponentInitializer(private val context: Context)
    :IcomponentInitializer {
    companion object {
        var Instance:IcomponentInitializer? = null
        operator fun invoke(context: Context):IcomponentInitializer {
            Log.e(TAG,"invoke()")
            if(Instance == null) {
                Log.e(TAG,"invoke(Instance == null)")
                Instance = ComponentInitializer(context.applicationContext)
            }
            return Instance!!
        }
    }

    private val welcomeRepository =  WelcomeRepository(context)
    private val readOnBoarding = ReadOnBoarding(welcomeRepository)
    private val saveOnBoarding = SaveOnBoarding(welcomeRepository)
    private val startViewModel = StartViewModel(readOnBoarding)
    private val onBoardingViewModel = OnBoardingViewModel(saveOnBoarding)
   // private val finProductViewModel =  FinProductViewModel(context.applicationContext as Application)



    override fun getStartViewModel(): StartViewModel {
        return startViewModel
    }

    override fun geOnBoardingViewModel(): OnBoardingViewModel {
        return onBoardingViewModel
    }


    private val themeRepository =  ThemeRepository(context)
    private val readTheme = ReadTheme(themeRepository)
    private val saveTheme = SaveTheme(themeRepository)
    private val themeViewModel = ThemeViewModel(readTheme,saveTheme)

    override fun geThemeViewModel(): ThemeViewModel {
        return themeViewModel
    }
/*
    override fun getFinProductViewModel(): FinProductViewModel? {
        Log.e(TAG,"getFinProductViewModel")
        if(finProductViewModel == null) {
            Log.e(TAG,"getFinProductViewModel == null")
            return null
        }
        Log.e(TAG,"getFinProductViewModel != null")
       return finProductViewModel
    }

 */
}