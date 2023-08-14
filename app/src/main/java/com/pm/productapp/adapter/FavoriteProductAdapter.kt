package com.pm.productapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pm.productapp.R
import com.pm.productapp.databinding.ItemProductBinding
import com.pm.productapp.roomdb.ProductEntity
import javax.inject.Inject

class FavoriteProductAdapter @Inject constructor() :
    RecyclerView.Adapter<FavoriteProductAdapter.FavoriteProductViewHolder>() {

    private var clickInterface: FavoriteClickInterface<ProductEntity>? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductEntity,
                newItem: ProductEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    val differ = AsyncListDiffer(this, DIFF_UTIL)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.view.productTitle.text = product.title
        holder.view.productRating.text = "Rating : ${product.rating}"
        holder.view.productPrice.text = "Price : ${product.price}"
        holder.view.buttonProductFavorite.text = "Remove from Favorite"

        Glide
            .with(holder.view.productImage.context)
            .load(product.productImage)
            .placeholder(R.drawable.loading_icon)
            .error(R.drawable.image_not_available)
            .centerCrop()
            .into(holder.view.productImage)
        holder.view.productCardView.setOnClickListener {
            clickInterface?.onClick(product)
        }

        holder.view.buttonProductBuy.setOnClickListener {
            // Toast.makeText(context, "Product added to Cart", Toast.LENGTH_LONG).show()
            clickInterface?.onClickBuyListener(product)

        }

        holder.view.buttonProductFavorite.setOnClickListener {
            clickInterface?.onClickUnFavoriteListener(product)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setItemClick(clickInterface: FavoriteClickInterface<ProductEntity>) {
        this.clickInterface = clickInterface
    }

    class FavoriteProductViewHolder(val view: ItemProductBinding) :
        RecyclerView.ViewHolder(view.root)
}

interface FavoriteClickInterface<T> {
    fun onClick(data: T)
    fun onClickUnFavoriteListener(data: T)
    fun onClickBuyListener(data: T)
}
