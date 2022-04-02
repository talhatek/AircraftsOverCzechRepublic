package com.tek.aircraftsoverczechrepublic.presentation.map

import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft

sealed class MapEvent {
    data class OnMarkerSelected(val marker:Aircraft) :MapEvent()
    object  OnMapClicked :MapEvent()
}