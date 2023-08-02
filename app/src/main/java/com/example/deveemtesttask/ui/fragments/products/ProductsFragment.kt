package com.example.deveemtesttask.ui.fragments.products

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.deveemtesttask.R
import com.example.deveemtesttask.data.core.Constants.CATEGORY_KEY
import com.example.deveemtesttask.data.core.Constants.PRODUCT_DETAILS_KEY
import com.example.deveemtesttask.data.remote.model.ProductDto
import com.example.deveemtesttask.databinding.FragmentProductsBinding
import com.example.deveemtesttask.databinding.FragmentSortDialogBinding
import com.example.deveemtesttask.ui.core.bases.BaseFragment
import com.example.deveemtesttask.ui.fragments.products.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment :
    BaseFragment<FragmentProductsBinding, ProductsViewModel>(R.layout.fragment_products) {
    override val binding by viewBinding(FragmentProductsBinding::bind)
    override val viewModel by viewModels<ProductsViewModel>()
    private val productsAdapter by lazy { ProductsAdapter(this::toDetails) }
    private val categoryName by lazy { arguments?.getString(CATEGORY_KEY) }
    private var isSaved = false

    override fun initialize() {
        super.initialize()
        initAdapter()
    }

    private fun initAdapter() {
        with(binding.rvProducts) {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initListeners() {
        super.initListeners()
        showSortDialog()
    }

    override fun initRequest() {
        super.initRequest()
        checkIsSaved()
        getProducts()
    }

    private fun checkIsSaved() {
        categoryName?.let { viewModel.isProductsByCategorySaved(it) }
    }

    private fun getProducts() {
        categoryName?.let { viewModel.getProducts(it) }
    }

    override fun initSubscribers() {
        super.initSubscribers()
        subscribeCheckIsSaved()
        subscribeProducts()
    }

    private fun subscribeProducts() {
        viewModel.productsState.spectateUiState(success = {
            productsAdapter.addData(it)
            if (!isSaved) {
                viewModel.createProducts(it)
            }
        })
    }

    private fun subscribeCheckIsSaved() {
        viewModel.isProductsByCategorySavedState.spectateUiState(success = {
            isSaved = it
        })
    }

    private fun toDetails(model: ProductDto) {
        val bundle = Bundle()
        bundle.putSerializable(PRODUCT_DETAILS_KEY, model)
        findNavController().navigate(R.id.productDetailFragment, bundle)
    }

    private fun showSortDialog() {
        binding.btnSort.setOnClickListener {
            val dialogBinding = FragmentSortDialogBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext()).apply {
                setView(dialogBinding.root)
                setTitle("Sort by")
                setNegativeButton("Cancel", null)
                setPositiveButton("OK") { _, _ ->
                    when {
                        dialogBinding.radioDefault.isChecked -> {
                            categoryName?.let { it -> viewModel.getProducts(it) }
                        }
                        dialogBinding.radioAsc.isChecked -> {
                            categoryName?.let { it -> viewModel.getProductAsc(it) }
                        }
                        dialogBinding.radioDesc.isChecked -> {
                            categoryName?.let { it -> viewModel.getProductDesc(it) }
                        }
                    }
                }
            }
            alertDialogBuilder.create().show()
        }
    }
}