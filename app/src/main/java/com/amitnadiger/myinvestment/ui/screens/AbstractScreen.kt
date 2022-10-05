package com.amitnadiger.myinvestment.ui.screens

abstract class AbstractScreen {
    abstract fun getScreenConfig():ScreenConfig
    abstract fun onEntry()
}