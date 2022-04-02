package com.tek.aircraftsoverczechrepublic.presentation

sealed class Screen(val route: String) {
    object MapScreen:Screen("map_screen")
}