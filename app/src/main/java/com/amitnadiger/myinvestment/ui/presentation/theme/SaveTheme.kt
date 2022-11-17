package com.amitnadiger.myinvestment.ui.presentation.theme

import com.amitnadiger.myinvestment.base.LocalUseCase
import com.amitnadiger.myinvestment.repository.ThemeRepository
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