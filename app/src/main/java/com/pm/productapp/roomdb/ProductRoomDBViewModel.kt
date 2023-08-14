package com.pm.productapp.roomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductRoomDBViewModel(application: Application) : AndroidViewModel(application) {

    private val productRoomDBRepository: ProductRoomDBRepository

    init {
        val productDao = ProductDatabase.getDatabase(application).productDao()
        productRoomDBRepository = ProductRoomDBRepository(productDao)
    }

    fun addProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            productRoomDBRepository.addProduct(productEntity)
        }
    }

    fun deleteProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            productRoomDBRepository.deleteProduct(productEntity)
        }
    }

    fun getAllProductList(): LiveData<List<ProductEntity>> {
        return productRoomDBRepository.getAllProduct()
    }
}