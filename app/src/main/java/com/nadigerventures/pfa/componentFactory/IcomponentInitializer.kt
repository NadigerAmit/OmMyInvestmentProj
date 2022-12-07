package com.nadigerventures.pfa.componentFactory

import com.nadigerventures.pfa.viewModel.OnBoardingViewModel
import com.nadigerventures.pfa.viewModel.StartViewModel
import com.nadigerventures.pfa.viewModel.ThemeViewModel

interface IcomponentInitializer {

    fun getStartViewModel(): StartViewModel
    fun geOnBoardingViewModel(): OnBoardingViewModel
    fun geThemeViewModel(): ThemeViewModel
}