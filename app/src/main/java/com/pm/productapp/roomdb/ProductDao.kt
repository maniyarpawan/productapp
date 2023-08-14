package com.pm.productapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productEntity: ProductEntity): Long

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("DELETE FROM Product")
    suspend fun deleteAllProduct()

    @Query("SELECT * FROM Product ORDER BY id ASC")
    fun getAllProductsEntity(): LiveData<List<ProductEntity>>

}