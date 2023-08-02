package com.example.deveemtesttask.ui.fragments.products

import com.example.deveemtesttask.data.remote.model.ProductDto
import com.example.deveemtesttask.data.remote.repository.ProductsRepository
import com.example.deveemtesttask.ui.core.bases.BaseViewModel
import com.geeks.ulul.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : BaseViewModel() {

    private var _productsState = mutableUiStateFlow<List<ProductDto>>()
    val productsState = _productsState.asStateFlow()

    private val _isProductsByCategorySavedState = mutableUiStateFlow<Boolean>()
    val isProductsByCategorySavedState = _isProductsByCategorySavedState.asStateFlow()

    private val _createProductsState = MutableStateFlow<UIState<Unit>>(UIState.Idle())

    fun isProductsByCategorySaved(category: String) {
        repository.isProductsByCategorySaved(category).collectFlow(_isProductsByCategorySavedState)
    }

    fun getProducts(category: String) {
        repository.getProducts(category).collectFlow(_productsState)
    }

    fun getProductAsc(category: String) {
        repository.getProductAsc(category).collectFlow(_productsState)
    }

    fun getProductDesc(category: String) {
        repository.getProductDesc(category).collectFlow(_productsState)
    }

    fun createProducts(products: List<ProductDto>) {
        products.map { repository.createProduct(it).collectFlow(_createProductsState) }
    }
}