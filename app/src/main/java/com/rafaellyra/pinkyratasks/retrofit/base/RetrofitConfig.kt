package com.rafaellyra.pinkyratasks.retrofit.base

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    private object Constants {
        val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    fun init() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.HTTP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}