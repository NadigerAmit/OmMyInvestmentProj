package com.amitnadiger.myinvestment.componentFactory

import com.amitnadiger.myinvestment.viewModel.MvvmViewModel
import com.amitnadiger.myinvestment.viewModel.OnBoardingViewModel
import com.amitnadiger.myinvestment.viewModel.StartViewModel
import com.amitnadiger.myinvestment.viewModel.ThemeViewModel

interface IcomponentInitializer {

    fun getStartViewModel(): StartViewModel
    fun geOnBoardingViewModel(): OnBoardingViewModel
    fun geThemeViewModel(): ThemeViewModel
}