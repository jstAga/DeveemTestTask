package com.example.deveemtesttask.ui.fragments.categories

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.deveemtesttask.R
import com.example.deveemtesttask.data.core.Constants.CATEGORY_KEY
import com.example.deveemtesttask.databinding.FragmentCategoriesBinding
import com.example.deveemtesttask.ui.core.bases.BaseFragment
import com.example.deveemtesttask.ui.fragments.categories.adapter.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment :
    BaseFragment<FragmentCategoriesBinding, CategoriesViewModel>(R.layout.fragment_categories) {
    override val binding by viewBinding(FragmentCategoriesBinding::bind)
    override val viewModel by viewModels<CategoriesViewModel>()
    private val categoriesAdapter by lazy { CategoriesAdapter(this::toProducts) }
    private var isSaved = false

    override fun initialize() {
        super.initialize()
        initAdapter()
    }

    override fun initRequest() {
        super.initRequest()
        checkIsSaved()
        fetchCategories()
    }

    override fun initSubscribers() {
        super.initSubscribers()
        subscribeCheckIsSaved()
        subscribeCategories()
    }

    private fun initAdapter() {
        with(binding.rvCategories) {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun checkIsSaved() = viewModel.isCategoriesSaved()


    private fun fetchCategories() {
        viewModel.getCategories()
    }

    private fun subscribeCategories() {
        viewModel.categoriesState.spectateUiState(success = {
            categoriesAdapter.addData(it)
            if (!isSaved) {
                viewModel.createCategories(it)
            }
        })
    }

    private fun subscribeCheckIsSaved() {
        viewModel.isCategoriesSavedState.spectateUiState(success = {
            isSaved = it
        })
    }

    private fun toProducts(category: String) {
        val bundle = Bundle()
        bundle.putString(CATEGORY_KEY, category)
        findNavController().navigate(R.id.productsFragment, bundle)
    }
}