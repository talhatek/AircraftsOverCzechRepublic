package com.tek.aircraftsoverczechrepublic.presentation.map

import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.tek.aircraftsoverczechrepublic.MainActivity
import com.tek.aircraftsoverczechrepublic.mock.MapSuccess
import com.tek.aircraftsoverczechrepublic.presentation.Screen
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MapScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockWebServer = MockWebServer()


    @Before
    fun setUp() {
        mockWebServer.start(8080)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MapSuccess.json)
        )
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.MapScreen.route
            ) {
                composable(Screen.MapScreen.route) {
                    MapScreen()
                }
            }
        }

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun isBottomSheetDisplayedAfterClickMarker(): Unit = runBlocking {
        val device = UiDevice.getInstance(getInstrumentation())
        val marker = device.findObject(UiSelector().descriptionContains("885176"))
        marker.click()
        composeRule.onNodeWithTag("BottomSheetBox").assertIsDisplayed()
    }

}