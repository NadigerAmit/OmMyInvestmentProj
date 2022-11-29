package com.nadigerventures.pfa.ui.presentation.welcome.onBoarding


import com.nadigerventures.pfa.base.LocalUseCase
import com.nadigerventures.pfa.repository.WelcomeRepository
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