package com.amitnadiger.myinvestment.ui.presentation.theme

import com.amitnadiger.myinvestment.repository.ThemeRepository
import com.amitnadiger.myinvestment.repository.WelcomeRepository

class ReadTheme(
    private val repository: ThemeRepository
)  {
    fun execute(): Boolean {
        return repository.readOnTheme()
    }
}