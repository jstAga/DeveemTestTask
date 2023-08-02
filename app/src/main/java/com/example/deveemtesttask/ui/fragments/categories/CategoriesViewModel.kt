package com.example.deveemtesttask.ui.fragments.categories

import com.example.deveemtesttask.data.remote.repository.CategoriesRepository
import com.example.deveemtesttask.ui.core.bases.BaseViewModel
import com.geeks.ulul.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: CategoriesRepository,
) :
    BaseViewModel() {

    private val _categoriesState = mutableUiStateFlow<List<String>>()
    val categoriesState = _categoriesState.asStateFlow()

    private val _isCategoriesSavedState = mutableUiStateFlow<Boolean>()
    val isCategoriesSavedState = _isCategoriesSavedState.asStateFlow()

    private val _createCategoryState = MutableStateFlow<UIState<Unit>>(UIState.Idle())

    fun isCategoriesSaved() {
        repository.isCategoriesSaved().collectFlow(_isCategoriesSavedState)
    }

    fun getCategories() {
        repository.getCategories().collectFlow(_categoriesState)
    }

    fun createCategories(categories: List<String>) {
        categories.map { repository.createCategory(it).collectFlow(_createCategoryState) }
    }
}