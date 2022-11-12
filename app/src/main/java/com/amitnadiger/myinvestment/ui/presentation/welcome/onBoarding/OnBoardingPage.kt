package com.amitnadiger.myinvestment.ui.presentation.welcome.onBoarding

import androidx.annotation.DrawableRes
import com.amitnadiger.myinvestment.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.ic_notification,
        title = "Home",
        description = "You can check the list of records which are sorted based on the maturity date " +
                "Matured items are displayed in Red  " +
                "To be matured items within 30days are displayed in Magenta ." +
                "To be matured items beyond 30days are displayed in Black or White color "
    )

    object Second : OnBoardingPage(
        image = R.drawable.ic_profilesetting,
        title = "AddInvestment",
        description = "You add the investments pressing the Red colored button with + symbol ." +
                "Can add investments details which will be displayed in Home color"
    )

    object Third : OnBoardingPage(
        image = R.drawable.ic_displaysetting,
        title = "Search/Filter Items",
        description = "You can filter investments based on many criteria .based on below 3 fields such as \n" +
                "1. Search by - Field \n" +
                "2. Operation \n" +
                "3. Value "
    )
}