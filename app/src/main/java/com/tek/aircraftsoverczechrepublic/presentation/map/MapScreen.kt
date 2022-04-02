package com.tek.aircraftsoverczechrepublic.presentation.map

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.tek.aircraftsoverczechrepublic.R
import com.tek.aircraftsoverczechrepublic.common.vectorToBitmap
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen() {
    val viewModel = get<MapViewModel>()
    val state = viewModel.state.value
    val prague = LatLng(50.073658, 14.418540)
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val scope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(prague, 8f)
    }

    BottomSheetScaffold(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.airplane_bg),
                    contentDescription = "BottomSheetBackground",
                    contentScale = ContentScale.FillWidth,
                    alpha = .4f
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = viewModel.selectedMarker.value.code)
                    Text(text = viewModel.selectedMarker.value.country)
                    Text(text = "Velocity is ${viewModel.selectedMarker.value.velocity} m/s")
                    Text(text = "Geometric altitude is  ${if (viewModel.selectedMarker.value.geo_altitude != null) viewModel.selectedMarker.value.geo_altitude + " meters" else "N/A"} ")
                }
            }
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(mapStyleOptions = MapStyleOptions(MapStyle.json)),
                uiSettings = MapUiSettings(
                    mapToolbarEnabled = false,
                    myLocationButtonEnabled = false
                ),
                onMapClick = {
                    viewModel.onEvent(MapEvent.OnMapClicked)
                    scope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isExpanded)
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            ) {
                state.aircraftList.forEach { aircraft ->
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(
                                aircraft.lat.toDouble(),
                                aircraft.long.toDouble()
                            )
                        ),
                        icon = LocalContext.current.vectorToBitmap(
                            R.drawable.ic_aircraft,
                            if (aircraft.code == viewModel.selectedMarker.value.code) Color.parseColor(
                                "#ff9500"
                            ) else Color.parseColor("#000000")
                        ),
                        rotation = aircraft.truck.toFloat(),
                        onClick = {
                            if (aircraft.code == viewModel.selectedMarker.value.code)
                                return@Marker true
                            scope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                    viewModel.onEvent(MapEvent.OnMarkerSelected(aircraft))
                                }
                                viewModel.onEvent(MapEvent.OnMarkerSelected(aircraft))
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                            true
                        }
                    )
                }
            }
            if (state.error.isNotBlank())
                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(.9f)
                ) {
                    Text(
                        text = state.error,
                        modifier = Modifier
                            .padding(all = 16.dp)
                    )
                }

            if (state.isLoading)
                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.fetching),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
        }
    }
}