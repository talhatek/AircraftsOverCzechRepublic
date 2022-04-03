package com.tek.aircraftsoverczechrepublic.data.repository

import com.tek.aircraftsoverczechrepublic.common.ResponseType
import com.tek.aircraftsoverczechrepublic.data.remote.dta.AllStatesDto
import com.tek.aircraftsoverczechrepublic.domain.repository.AircraftRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * I am not aware of this API so I am returning randomly
 */
class FakeAircraftRepositoryImpl(private val type: ResponseType) : AircraftRepository {
    private val allStatesDto = AllStatesDto(
        "1", states = arrayListOf(
            arrayListOf(
                "4ba976",
                "THY78E  ",
                "Turkey",
                "1648984612",
                "1648984612",
               " 8.2701",
               " 47.6945",
               " 6339.84",
                "false",
                "197.02",
               " 104.36",
               " 12.35",
               " 6210.3",
                "6747",
               " false",
               " 0",
                "0"
            )
        )
    )

    override suspend fun getAirCrafts(): AllStatesDto {
        return when (type) {
            ResponseType.SUCCESS -> {
                allStatesDto
            }
            ResponseType.HTTP_ERROR -> {
                throw HttpException(Response.error<ResponseBody>(500 ,
                    "http error".toResponseBody("plain/text".toMediaTypeOrNull())
                ))
            }
            ResponseType.IO_ERROR -> {
                throw IOException()
            }


        }

    }
}