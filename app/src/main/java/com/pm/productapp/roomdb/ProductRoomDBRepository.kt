package com.pm.productapp.roomdb

import androidx.lifecycle.LiveData

class ProductRoomDBRepository(private val productDao: ProductDao) {

    suspend fun addProduct(productEntity: ProductEntity): Long {
        return productDao.addProduct(productEntity)
    }

    suspend fun deleteProduct(productEntity: ProductEntity) {
        productDao.deleteProduct(productEntity)
    }

    fun getAllProduct(): LiveData<List<ProductEntity>> {
        return productDao.getAllProductsEntity()
    }
}