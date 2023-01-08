package com.nadigerventures.pfa.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.fragment.ProductListFragment
import com.nadigerventures.pfa.ui.screens.fragment.nod
import com.nadigerventures.pfa.utility.DataStoreConst
import com.nadigerventures.pfa.viewModel.FinHistoryViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun History(navController: NavHostController, viewModel: FinHistoryViewModel, padding: PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())

    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
        LocalContext.current,
        DataStoreConst.SECURE_DATASTORE, true
    )
    runBlocking {
        nod.value = dataStoreProvider.getString(DataStoreConst.NOTIFICATION_DAYS).first() ?: "30"
    }
    var advanceNotifyDays by remember { mutableStateOf(nod.value.toInt()) }

    //val searchResults by viewModel.searchResults.observeAsState(listOf())
    val displayItemListOfBlankHistory = listOf(
        //  "DisplayImage",
        "statementTextForHistory",
        "GuideToDeleteAllItemsFromHistory",
      //  "ImageToDeleteAllItemsFromHistory"
    )
    var totalInvestmentAmount = 0.0
    var totalMaturityAmount = 0.0

    if(allProducts.isNotEmpty()) {
        for(i in allProducts) {
            totalInvestmentAmount +=i.investmentAmount
            totalMaturityAmount +=i.maturityAmount
        }
    } else {
        HomeDisplaySettingFragment(displayItemListOfBlankHistory)
    }

    ProductListFragment(
        navController,
        allProducts = allProducts,
        null,
        totalInvestmentAmount,
        totalMaturityAmount,
        padding,
        "History",
        null,
        advanceNotifyDays
    )
}

fun getScreenConfig4History():ScreenConfig = //Log.e("Home screen","getScreenConfig4Home");
    ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        screenOnBackPress = NavRoutes.Home.route,
        enableFab = true,
        enableFilter = false,
        enableSort= false,
        topAppBarStartPadding = 20.dp,
        topAppBarTitle = "Recycle Bin", bottomAppBarTitle = "",
        fabString = "Clear all",
    )