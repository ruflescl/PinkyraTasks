package com.rafaellyra.pinkyratasks.retrofit.base

import retrofit2.Callback
import retrofit2.Response

abstract class RetrofitResponseHandlerBase<T> {
    protected abstract fun createSingleFetchResponseHandler(adapterPosition: Int = -1): Callback<T>
    protected abstract fun createListFetchResponseHandler(): Callback<List<T>>
    protected abstract fun createPersistResponseHandler(): Callback<T>
    protected abstract fun createDeleteResponseHandler(id: Long): Callback<Void>

    protected fun createListFromResponse(response: Response<T>?): List<T> {
        val result: List<T> = if (response?.body() != null) {
            listOf(response.body()!!)
        } else {
            ArrayList<T>()
        }
        return result
    }
}