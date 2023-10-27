package com.ayberk.composeapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.composeapp.models.CountryItem
import com.ayberk.composeapp.models.city.City
import com.ayberk.composeapp.models.city.CityItem
import com.ayberk.composeapp.retrofit.CountryRepository
import com.ayberk.composeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class countrylistviewmodel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    var countryList = mutableStateOf<List<CountryItem>>(listOf())
    var cityList = mutableStateOf<List<CityItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isLoadingcity = mutableStateOf(false)

    init {
        loadCountry()
    }

    fun loadCountry() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCountry()

            when(result) {
                is Resource.Success -> {
                    val countryItems = result.data!!.mapIndexed { index, item ->
                        CountryItem(item.UlkeAdi,item.UlkeAdiEn,item.UlkeID)
                    } as List<CountryItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    countryList.value += countryItems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    errorMessage.value = ""
                }

                else -> {}
            }
        }
    }

    suspend fun getCity(countryCode: String): Resource<City> {
        return repository.getCity(countryCode)

    }
}