package com.ayberk.composeapp.retrofit

import com.ayberk.composeapp.models.Country
import com.ayberk.composeapp.models.city.City
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryAPI {

    @GET("ulkeler")
    suspend fun getCountry(): Country

    @GET("sehirler/{countryCode}")
    suspend fun getCity(@Path("countryCode") countryCode: String): City
}