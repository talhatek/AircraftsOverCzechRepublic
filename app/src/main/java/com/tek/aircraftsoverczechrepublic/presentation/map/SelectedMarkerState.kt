package com.tek.aircraftsoverczechrepublic.presentation.map

import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft

sealed class SelectedMarkerState {
    object Unselected : SelectedMarkerState()
    data class Selected(val aircraft: Aircraft) : SelectedMarkerState()
}