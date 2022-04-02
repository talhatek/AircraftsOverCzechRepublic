package com.tek.aircraftsoverczechrepublic.domain.repository

import com.tek.aircraftsoverczechrepublic.data.remote.dta.AllStatesDto


interface AircraftRepository {

  suspend fun getAirCrafts(): AllStatesDto
}