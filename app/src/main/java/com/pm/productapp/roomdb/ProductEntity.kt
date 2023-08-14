package com.pm.productapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val rating: Double,
    val price: Double,
    val favouriteStatus: Boolean,
    val productImage: String
)
