package com.tek.aircraftsoverczechrepublic.presentation.map

import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import androidx.compose.ui.test.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.tek.aircraftsoverczechrepublic.common.base.BaseScreenTest
import com.tek.aircraftsoverczechrepublic.mock.MapSuccess
import com.tek.aircraftsoverczechrepublic.presentation.Screen
import okhttp3.mockwebserver.MockResponse
import org.junit.Test


@LargeTest
class MapScreenTest : BaseScreenTest(Screen.MapScreen) {

    @Test
    fun isBottomSheetDisplayedAfterClickMarker() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MapSuccess.json)
        )
        setContent()
        val device = UiDevice.getInstance(getInstrumentation())
        val marker = device.findObject(UiSelector().descriptionContains("885176"))
        marker.click()
        composeRule.onNodeWithTag("BottomSheetBox").assertIsDisplayed()
    }

    @Test
    fun isErrorCardVisible() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500)
        )
        setContent()
        composeRule.onNodeWithTag("errorCard").assertIsDisplayed()
    }


}