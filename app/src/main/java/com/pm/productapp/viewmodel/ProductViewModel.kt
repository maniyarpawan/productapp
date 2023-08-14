package com.pm.productapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pm.productapp.data.model.Product
import com.pm.productapp.repository.ProductRepository
import com.pm.productapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _productResponse = MutableLiveData<NetworkResult<List<Product>>>()
    val productResponse: LiveData<NetworkResult<List<Product>>> = _productResponse

    init {
        fetchAllProducts()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect {
                _productResponse.postValue(it)
            }
        }
    }
}