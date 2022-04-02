package com.tek.aircraftsoverczechrepublic.data.remote.dta

import com.tek.aircraftsoverczechrepublic.domain.model.Aircraft

data class AllStatesDto(
    val time: String,
    val states: ArrayList<ArrayList<String>>
)

fun AllStatesDto.toAircraft(): List<Aircraft> {
    val tmp = arrayListOf<Aircraft>()
    this.states.forEach {
        tmp.add(
            Aircraft(
                uniqueId=it[0],
                code = it[1],
                country = it[2],
                long = it[5],
                lat = it[6],
                truck = it[10],
                velocity = it[9],
                geo_altitude = it[13]
            )
        )
    }
    return tmp
}