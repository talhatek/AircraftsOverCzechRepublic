package com.tek.aircraftsoverczechrepublic.presentation.map

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import com.tek.aircraftsoverczechrepublic.common.Resource
import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft
import com.tek.aircraftsoverczechrepublic.domain.model.Polygon
import com.tek.aircraftsoverczechrepublic.domain.use_case.get_aircrafts.GetAircraftUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
class MapViewModel(private val aircraftUseCase: GetAircraftUseCase) : ViewModel() {
    private val _state = mutableStateOf(AircraftListState())
    val state: State<AircraftListState> = _state
    val selectedMarker = mutableStateOf(Aircraft("", "", "", "", "", "", "", ""))
    val polygonPoints = mutableStateOf(mutableListOf<LatLng>())

    init {
        viewModelScope.launch {
            while (this.isActive) {
                getAircraftList()
                delay(10_000L)
            }
        }
        extractPolygon()

    }
    private fun extractPolygon(){
        val gson = Gson()
        val paths = gson.fromJson(CzechiaPolygon.json, Polygon::class.java)
        paths.coordinates[0].forEach {
            polygonPoints.value.add(LatLng(it[1], it[0]))
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnMarkerSelected -> {
                selectedMarker.value = event.marker
            }
            is MapEvent.OnMapClicked -> {
                selectedMarker.value = Aircraft("", "", "", "", "", "", "", "")
            }
        }
    }

    private fun getAircraftList() {
        aircraftUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val tmpData = arrayListOf<Aircraft>()
                    result.data?.forEach {
                        if (PolyUtil.containsLocation(LatLng(it.lat.toDouble(),it.long.toDouble()),polygonPoints.value,false))
                            tmpData.add(it)
                    }
                    _state.value = AircraftListState(aircraftList = tmpData)
                }
                is Resource.Error -> {
                    _state.value =
                        AircraftListState(error = result.message ?: "An unexpected error occurred")

                }
                is Resource.Loading -> {
                    _state.value = AircraftListState(isLoading = true)
                    selectedMarker.value = Aircraft("", "", "", "", "", "", "", "")
                }
            }
        }.launchIn(viewModelScope)
    }
}