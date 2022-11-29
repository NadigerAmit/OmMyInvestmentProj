package com.nadigerventures.pfa.ui.presentation.theme

import com.nadigerventures.pfa.repository.ThemeRepository

class ReadTheme(
    private val repository: ThemeRepository
)  {
    fun execute(): Boolean {
        return repository.readOnTheme()
    }
}