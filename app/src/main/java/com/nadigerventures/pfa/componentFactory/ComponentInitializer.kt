package com.nadigerventures.pfa.componentFactory

import android.content.Context
import com.nadigerventures.pfa.repository.ThemeRepository
import com.nadigerventures.pfa.repository.WelcomeRepository
import com.nadigerventures.pfa.ui.presentation.theme.ReadTheme
import com.nadigerventures.pfa.ui.presentation.theme.SaveTheme
import com.nadigerventures.pfa.ui.presentation.welcome.onBoarding.ReadOnBoarding
import com.nadigerventures.pfa.ui.presentation.welcome.onBoarding.SaveOnBoarding
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import com.nadigerventures.pfa.viewModel.OnBoardingViewModel

import com.nadigerventures.pfa.viewModel.StartViewModel
import com.nadigerventures.pfa.viewModel.ThemeViewModel

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
    //var finProductViewModel: FinProductViewModel?= null

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