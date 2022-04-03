package com.tek.aircraftsoverczechrepublic.common.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tek.aircraftsoverczechrepublic.MainActivity
import com.tek.aircraftsoverczechrepublic.presentation.Screen
import com.tek.aircraftsoverczechrepublic.presentation.map.MapScreen
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseScreenTest(private val screen: Screen)  {

    @get:Rule
    val composeRule by lazy { createAndroidComposeRule<MainActivity>() }

   protected val mockWebServer by lazy { MockWebServer() }

    @Before
    fun setUp() {
        mockWebServer.start(8080)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    fun setContent() {
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = screen.route
            ) {
                composable(Screen.MapScreen.route) {
                    MapScreen()
                }
            }
        }
    }

}