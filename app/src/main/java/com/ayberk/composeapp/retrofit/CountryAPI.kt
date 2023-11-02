package com.ayberk.composeapp.retrofit

import com.ayberk.composeapp.models.Country
import com.ayberk.composeapp.models.city.City
import com.ayberk.composeapp.models.times.Times
import com.ayberk.composeapp.models.town.Town
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryAPI {

    @GET("ulkeler")
    suspend fun getCountry(): Country

    @GET("sehirler/{countryCode}")
    suspend fun getCity(@Path("countryCode") countryCode: String): City

    @GET("ilceler/{townCode}")
    suspend fun getTown(@Path("townCode") townCode: String): Town

    @GET("vakitler/{timesCode}")
    suspend fun getTimes(@Path("timesCode") timesCode: String): Times
}