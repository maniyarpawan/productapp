package com.pm.productapp.data.model

data class Product(
    val addToCartButtonText: String,
    val badges: List<String>,
    val brand: String,
    val citrusId: String,
    val id: String,
    val imageURL: String,
    val isAddToCartEnable: Boolean,
    val isDeliveryOnly: Boolean,
    val isDirectFromSupplier: Boolean,
    val isFindMeEnable: Boolean,
    val isInTrolley: Boolean,
    val isInWishlist: Boolean,
    val messages: Messages,
    val price: List<Price>,
    val purchaseTypes: List<PurchaseType>,
    val ratingCount: Double,
    val saleUnitPrice: Double,
    val title: String,
    val totalReviewCount: Int
)