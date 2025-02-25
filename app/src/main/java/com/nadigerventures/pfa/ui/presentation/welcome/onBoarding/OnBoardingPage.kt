package com.nadigerventures.pfa.ui.presentation.welcome.onBoarding

import androidx.annotation.DrawableRes
import com.nadigerventures.pfa.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.onboardingscreen1_home,
        title = "Home",
        description = "App helps to track investments which have maturityDates." +
                "Easy to track as records are sorted by maturityDate & color coded.")

    object Second : OnBoardingPage(
        image = R.drawable.onboardingscreen2_accountdetail,
        title = "DetailScreen",
        description = "Individual accountDetails can be viewed by pressing the record in homeScreen."
    )

    object Third : OnBoardingPage(
        image = R.drawable.onboardingscreen3_filter,
        title = "Search/Filter Items",
        description = "Filter by below  \n" +
                "1. Search by \n" +
                "2. Operation \n" +
                "3. Value "
    )

    object Forth : OnBoardingPage(
        image = R.drawable.onboardingscreen4_datasecurity,
        title = "Attention!",
        description = "DataSecurity\n" +
                "App store all enteredData securely with encryption within the Device, " +
                "so that even developers cant know them." +
                "The data is never sent out of the device "
    )
}