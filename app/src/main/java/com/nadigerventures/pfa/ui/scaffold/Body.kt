package com.nadigerventures.pfa.ui.scaffold


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.ui.ScreenNavigation
import com.nadigerventures.pfa.viewModel.FinHistoryViewModel

import com.nadigerventures.pfa.viewModel.FinProductViewModel


@Composable
fun Body(navController: NavHostController,
         finProductViewModel: FinProductViewModel,
         finHistoryViewModel: FinHistoryViewModel,
         padding:PaddingValues) {
    ScreenNavigation(navController,finProductViewModel,finHistoryViewModel,padding)
}