package com.tek.aircraftsoverczechrepublic.data.repository

import com.tek.aircraftsoverczechrepublic.data.remote.OpenSkyApi
import com.tek.aircraftsoverczechrepublic.data.remote.dta.AllStatesDto
import com.tek.aircraftsoverczechrepublic.domain.repository.AircraftRepository

class AircraftRepositoryImpl(private val api: OpenSkyApi) : AircraftRepository {

    override suspend fun getAirCrafts(): AllStatesDto = api.getAllStates()

}