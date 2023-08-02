package com.example.deveemtesttask.ui.fragments.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.deveemtesttask.data.remote.model.ProductDto
import com.example.deveemtesttask.databinding.ItemProductBinding

class ProductsAdapter(private val onItemClick: (ProductDto) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val data = arrayListOf<ProductDto>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<ProductDto>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ProductsViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProductDto) {
            with(binding) {
                tvTitle.text = model.title
                tvPrice.text = model.price.toString()
                ivImage.load(model.image)

            }
            itemView.setOnClickListener { onItemClick(model) }
        }
    }
}