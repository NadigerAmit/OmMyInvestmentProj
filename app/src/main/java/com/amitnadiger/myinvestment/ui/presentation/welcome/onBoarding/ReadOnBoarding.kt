package com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding


import com.amitnadiger.myinvestment.repository.WelcomeRepository



class ReadOnBoarding (
    private val repository: WelcomeRepository
)  {
    fun execute(): Boolean {
        return repository.readOnBoardingState()
    }
}