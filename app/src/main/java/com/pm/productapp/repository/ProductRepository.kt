package com.pm.productapp.repository

import com.pm.productapp.network.api.ApiService
import com.pm.productapp.utils.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository  @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts()  = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getProducts()
        emit(NetworkResult.Success(response.products))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}