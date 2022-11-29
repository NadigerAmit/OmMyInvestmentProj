package com.nadigerventures.pfa.ui.presentation.theme

import com.nadigerventures.pfa.base.LocalUseCase
import com.nadigerventures.pfa.repository.ThemeRepository
import kotlinx.coroutines.flow.FlowCollector

class SaveTheme (
    internal val repository: ThemeRepository
) : LocalUseCase<SaveTheme.Params, Unit>() {

    data class Params(
        val isDarkMode: Boolean
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        repository.saveTheme(params.isDarkMode)
        emit(Unit)
    }
}