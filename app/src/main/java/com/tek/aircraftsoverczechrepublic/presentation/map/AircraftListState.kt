package com.tek.aircraftsoverczechrepublic.presentation.map

import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft

data class AircraftListState(
    val isLoading: Boolean = false,
    val aircraftList: List<Aircraft> = emptyList(),
    val error: String = ""
)