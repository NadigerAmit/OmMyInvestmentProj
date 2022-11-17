package com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding


import com.amitnadiger.myinvestment.base.LocalUseCase
import com.amitnadiger.myinvestment.repository.WelcomeRepository
import kotlinx.coroutines.flow.FlowCollector

class SaveOnBoarding (
    internal val repository: WelcomeRepository
) : LocalUseCase<SaveOnBoarding.Params, Unit>() {

    data class Params(
        val completed: Boolean
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        repository.saveOnBoardingState(params.completed)
        emit(Unit)
    }
}