package com.nadigerventures.pfa.viewModel

import androidx.lifecycle.viewModelScope

import com.nadigerventures.pfa.ui.presentation.welcome.onBoarding.SaveOnBoarding
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val saveOnBoarding: SaveOnBoarding,
) : MvvmViewModel() {

    fun saveOnBoardingState(completed: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveOnBoarding.Params(completed)
        call(saveOnBoarding(params))
    }
}