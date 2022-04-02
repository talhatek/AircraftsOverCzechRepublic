package com.tek.aircraftsoverczechrepublic.presentation.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tek.aircraftsoverczechrepublic.common.Resource
import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft
import com.tek.aircraftsoverczechrepublic.domain.use_case.get_aircrafts.GetAircraftUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MapViewModel(private val aircraftUseCase: GetAircraftUseCase) : ViewModel() {
    private val _state = mutableStateOf(AircraftListState())
    val state: State<AircraftListState> = _state

   val selectedMarker= mutableStateOf(Aircraft("", "", "", "", "", "", ""))


    init {
        viewModelScope.launch {
            while (this.isActive) {
                getAircraftList()
                delay(10_000L)
            }
        }
    }

    fun onEvent(event:MapEvent){
        when(event){
            is MapEvent.OnMarkerSelected-> {
                selectedMarker.value = event.marker
            }
            is MapEvent.OnMapClicked->{
                selectedMarker.value = Aircraft("", "", "", "", "", "", "")
            }
        }
    }

    private fun getAircraftList() {
        aircraftUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AircraftListState(aircraftList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        AircraftListState(error = result.message ?: "An unexpected error occurred")

                }
                is Resource.Loading -> {
                    _state.value = AircraftListState(isLoading = true)
                    selectedMarker.value = Aircraft("", "", "", "", "", "", "")
                }
            }
        }.launchIn(viewModelScope)
    }
}