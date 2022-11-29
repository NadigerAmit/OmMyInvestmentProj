package com.nadigerventures.pfa.ui.presentation.welcome.onBoarding


import com.nadigerventures.pfa.repository.WelcomeRepository



class ReadOnBoarding (
    private val repository: WelcomeRepository
)  {
    fun execute(): Boolean {
        return repository.readOnBoardingState()
    }
}