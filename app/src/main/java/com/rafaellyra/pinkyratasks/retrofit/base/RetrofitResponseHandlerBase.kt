package com.rafaellyra.pinkyratasks.retrofit.base

import retrofit2.Response

abstract class RetrofitResponseHandlerBase<T> {
    protected fun createListFromResponse(response: Response<T>?): List<T> {
        val result: List<T> = if (response?.body() != null) {
            listOf(response.body()!!)
        } else {
            ArrayList<T>()
        }
        return result
    }
}