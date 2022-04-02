package com.tek.aircraftsoverczechrepublic.domain.use_case.get_aircrafts

import com.tek.aircraftsoverczechrepublic.common.Resource
import com.tek.aircraftsoverczechrepublic.data.remote.dta.toAircraft
import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft
import com.tek.aircraftsoverczechrepublic.domain.repository.AircraftRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetAircraftUseCase(private val repository: AircraftRepository) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getAirCrafts().toAircraft()
            emit(Resource.Success(data = data))
        } catch (ex: HttpException) {
            emit(Resource.Error(message = ex.localizedMessage ?: "An unexpected error occurred"))

        } catch (ex: IOException) {
            emit(Resource.Error(message = "You look offline. Check your internet connection."))

        }
    }
}