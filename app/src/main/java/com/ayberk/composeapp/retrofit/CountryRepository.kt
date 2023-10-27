package com.ayberk.composeapp.retrofit

import com.ayberk.composeapp.models.Country
import com.ayberk.composeapp.models.city.City
import com.ayberk.composeapp.util.Resource
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val api: CountryAPI
) {

    suspend fun getCountry(): Resource<Country> {
        val response = try {
            api.getCountry()
        } catch(e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCity(countryCode: String): Resource<City> {
        val response = try {
            api.getCity(countryCode)
        } catch(e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}