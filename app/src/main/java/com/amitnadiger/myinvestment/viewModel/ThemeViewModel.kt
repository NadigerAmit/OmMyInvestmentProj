package com.amitnadiger.myinvestment.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amitnadiger.myinvestment.ui.presentation.theme.ReadTheme
import com.amitnadiger.myinvestment.ui.presentation.theme.SaveTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ThemeViewModel(
    private val readTheme: ReadTheme,
    private val saveTheme: SaveTheme
) : MvvmViewModel() {

    val isDarkMode = mutableStateOf(false)

    init {
        readThemeState()
    }

    fun readThemeState():Boolean {
        runBlocking {
            isDarkMode.value = readTheme.execute()
        }
        return isDarkMode.value

    }

    fun saveThemeState(isDarkMode: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveTheme.Params(isDarkMode)
        call(saveTheme(params))
        this@ThemeViewModel.isDarkMode.value = isDarkMode
    }

}