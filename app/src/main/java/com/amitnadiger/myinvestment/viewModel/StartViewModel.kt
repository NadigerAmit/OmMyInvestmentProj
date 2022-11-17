package com.amitnadiger.myinvestment.viewModel


import com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding.ReadOnBoarding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

private val TAG = "StartViewModel"
class StartViewModel (
    private val readOnBoarding: ReadOnBoarding
) : MvvmViewModel() {

    private val _startWelcome = MutableStateFlow(false)
    init {
        readOnBoardingState()
    }

    fun readOnBoardingState():Boolean {
        runBlocking {
            _startWelcome.value = readOnBoarding.execute()
        }
        return _startWelcome.value
    }
}