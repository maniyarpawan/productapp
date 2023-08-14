package com.pm.productapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pm.productapp.MainActivity
import com.pm.productapp.R
import com.pm.productapp.databinding.FragmentProductDetailsBinding
import com.pm.productapp.roomdb.ProductEntity
import com.pm.productapp.roomdb.ProductRoomDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productEntity: ProductEntity
    private lateinit var productRoomDBViewModel: ProductRoomDBViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productRoomDBViewModel = (activity as MainActivity).productRoomDBViewModel

        val id = arguments?.getString("product_id")
        val title = arguments?.getString("product_title")
        val rating = arguments?.getDouble("product_rating")
        val price = arguments?.getDouble("product_price")
        val favStatus = arguments?.getBoolean("product_favorite")
        val image = arguments?.getString("product_imageUrl")
        if (favStatus == true) {
            binding.buttonDetailProductFavorite.text = "Remove from Favorite"
        } else {
            binding.buttonDetailProductFavorite.text = "Add to Favorite"
        }
        productEntity = ProductEntity(
            id.toString(),
            title.toString(), rating!!, price!!, favStatus!!, image.toString()
        )

        binding.productDetailTitle.text = title.toString()
        binding.productDetailRating.text = "Rating : ${rating.toString()}"
        binding.productDetailPrice.text = "Price : ${price.toString()}"
        Glide
            .with(binding.productDetailImage.context)
            .load(image.toString())
            .placeholder(R.drawable.loading_icon)
            .error(R.drawable.image_not_available)
            .fitCenter()
            .into(binding.productDetailImage)

        binding.buttonDetailProductFavorite.setOnClickListener {
            if (favStatus == true) {
                productRoomDBViewModel.deleteProduct(productEntity)
                //Toast.makeText(context, "REmoved from favorite", Toast.LENGTH_LONG).show()
                Snackbar.make(view, "Removed from favorite "+productEntity.title, Snackbar.LENGTH_SHORT).show()
                binding.buttonDetailProductFavorite.text = "Add to Favorite"
            } else {
                productRoomDBViewModel.addProduct(productEntity)
                Snackbar.make(view, "Added to favorite "+productEntity.title, Snackbar.LENGTH_SHORT).show()
                //Toast.makeText(context, "Added to favorite", Toast.LENGTH_LONG).show()
                binding.buttonDetailProductFavorite.text = "Remove from Favorite"
            }

        }
    }
}