package com.amitnadiger.myinvestment.ui.scaffold


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.ScreenNavigation

import com.amitnadiger.myinvestment.viewModel.FinProductViewModel


@Composable
fun Body(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    ScreenNavigation(navController,viewModel,padding)
}