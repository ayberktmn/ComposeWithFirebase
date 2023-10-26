package com.ayberk.composeapp.retrofit

import com.ayberk.composeapp.models.Country
import retrofit2.http.GET

interface CountryAPI {

    @GET("ulkeler")
    suspend fun getCountry(): Country
}