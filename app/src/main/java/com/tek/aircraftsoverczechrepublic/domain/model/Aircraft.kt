package com.tek.aircraftsoverczechrepublic.domain.model

data class Aircraft(
    val code: String,
    val country:String,
    val velocity:String,
    val geo_altitude:String?,
    val long: String,
    val lat: String,
    val truck: String
)