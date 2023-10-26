package com.ayberk.composeapp.di

import com.ayberk.composeapp.retrofit.CountryAPI
import com.ayberk.composeapp.retrofit.CountryRepository
import com.ayberk.composeapp.util.Constans.BASE_URL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun firebase_auth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebasefirestoreDatabase() =  Firebase.firestore

    @Singleton
    @Provides
    fun provideCryptoRepository(
        api: CountryAPI
    ) = CountryRepository(api)

    @Singleton
    @Provides
    fun provideCryptoApi(): CountryAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CountryAPI::class.java)
    }
}