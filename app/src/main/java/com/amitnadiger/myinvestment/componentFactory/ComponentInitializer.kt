package com.amitnadiger.myinvestment.componentFactory

import android.content.Context
import com.amitnadiger.myinvestment.repository.ThemeRepository
import com.amitnadiger.myinvestment.repository.WelcomeRepository
import com.amitnadiger.myinvestment.ui.presentation.theme.ReadTheme
import com.amitnadiger.myinvestment.ui.presentation.theme.SaveTheme
import com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding.ReadOnBoarding
import com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding.SaveOnBoarding
import com.amitnadiger.myinvestment.viewModel.OnBoardingViewModel

import com.amitnadiger.myinvestment.viewModel.StartViewModel
import com.amitnadiger.myinvestment.viewModel.ThemeViewModel

class ComponentInitializer private constructor(private val context: Context)
    :IcomponentInitializer {
    companion object {
        var Instance:IcomponentInitializer? = null
        operator fun invoke(context: Context):IcomponentInitializer {
            if(Instance == null) {
                Instance = ComponentInitializer(context)
            }
            return Instance!!
        }
    }

    private val welcomeRepository =  WelcomeRepository(context)
    private val readOnBoarding = ReadOnBoarding(welcomeRepository)
    private val saveOnBoarding = SaveOnBoarding(welcomeRepository)
    private val startViewModel = StartViewModel(readOnBoarding)
    private val onBoardingViewModel = OnBoardingViewModel(saveOnBoarding)

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

}