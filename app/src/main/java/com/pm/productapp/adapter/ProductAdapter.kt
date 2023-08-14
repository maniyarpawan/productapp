package com.pm.productapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pm.productapp.R
import com.pm.productapp.data.model.Product
import com.pm.productapp.databinding.ItemProductBinding
import javax.inject.Inject

class ProductAdapter @Inject constructor() :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var clickInterface: ClickInterface<Product>? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    val differ = AsyncListDiffer(this, DIFF_UTIL)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.view.productTitle.text = product.title
        holder.view.productRating.text = "Rating : ${product.ratingCount}"
        holder.view.productPrice.text = "Price : ${product.price[0].value.toString()}"
        Glide
            .with(holder.view.productImage.context)
            .load(product.imageURL)
            .placeholder(R.drawable.loading_icon)
            .error(R.drawable.image_not_available)
            .centerCrop()
            .into(holder.view.productImage)
        holder.view.productCardView.setOnClickListener {
            clickInterface?.onClick(product)
        }

        holder.view.buttonProductBuy.setOnClickListener {
            clickInterface?.onClickBuyListener(product)
        }
        holder.view.buttonProductFavorite.setOnClickListener {
            clickInterface?.onClickFavoriteListener(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setItemClick(clickInterface: ClickInterface<Product>) {
        this.clickInterface = clickInterface
    }

    class ProductViewHolder(val view: ItemProductBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
    fun onClickFavoriteListener(data: T)
    fun onClickBuyListener(data: T)
}
