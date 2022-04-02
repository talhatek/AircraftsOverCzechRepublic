package com.tek.aircraftsoverczechrepublic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tek.aircraftsoverczechrepublic.presentation.Screen
import com.tek.aircraftsoverczechrepublic.presentation.map.MapScreen
import com.tek.aircraftsoverczechrepublic.presentation.ui.theme.AircraftsOverCzechRepublicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AircraftsOverCzechRepublicTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MapScreen.route
                    ) {
                       composable(route = Screen.MapScreen.route){
                           MapScreen()
                       }
                    }
                }

            }
        }
    }
}
