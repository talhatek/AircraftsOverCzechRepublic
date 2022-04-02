package com.tek.aircraftsoverczechrepublic.data.remote

import com.tek.aircraftsoverczechrepublic.data.remote.dta.AllStatesDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenSkyApi {

    @GET("states/all")
    suspend fun getAllStates(
        @Query("lamin") lamin: String = "48.55",
        @Query("lomin") lomin: String = "12.9",
        @Query("lamax") lamax: String = "51.06",
        @Query("lomax") lomax: String = "18.87"
    ): AllStatesDto
}