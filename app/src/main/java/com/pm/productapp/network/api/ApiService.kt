package com.pm.productapp.network.api

import com.pm.productapp.data.model.Products
import retrofit2.http.GET

interface ApiService {
    @GET("v3/2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getProducts() : Products

}