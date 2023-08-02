package com.example.deveemtesttask.ui.fragments.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.deveemtesttask.R
import com.example.deveemtesttask.data.core.Constants.PRODUCT_DETAILS_KEY
import com.example.deveemtesttask.data.remote.model.ProductDto
import com.example.deveemtesttask.databinding.FragmentProductDetailBinding
import com.example.deveemtesttask.ui.core.bases.BaseFragment
import com.example.deveemtesttask.ui.core.getSerializableExt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding, ProductsDetailViewModel>(R.layout.fragment_product_detail) {
    override val binding by viewBinding(FragmentProductDetailBinding::bind)
    override val viewModel by viewModels<ProductsDetailViewModel>()
    private val productModel by lazy {
        arguments?.let {
            getSerializableExt(
                it,
                PRODUCT_DETAILS_KEY,
                ProductDto::class.java
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initialize() {
        super.initialize()
        setData()
    }

    private fun setData() {
        with(binding){
            ivImage.load(productModel?.image)
            tvTitle.text = productModel?.title
            tvPrice.text = productModel?.price.toString()
            tvDesc.text = productModel?.description
            tvCategory.text = productModel?.category
            ratingBar.rating = (productModel?.rating?.rate?.toFloat() ?: 0.0) as Float
        }
    }
}