package com.pm.productapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pm.productapp.MainActivity
import com.pm.productapp.R
import com.pm.productapp.adapter.FavoriteClickInterface
import com.pm.productapp.adapter.FavoriteProductAdapter
import com.pm.productapp.databinding.FragmentFavoriteBinding
import com.pm.productapp.roomdb.ProductEntity
import com.pm.productapp.roomdb.ProductRoomDBViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteProductFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var productRoomDBViewModel: ProductRoomDBViewModel

    @Inject
    lateinit var favoriteProductAdapter: FavoriteProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productRoomDBViewModel = (activity as MainActivity).productRoomDBViewModel
        binding.productFavoriteRecycler.adapter = favoriteProductAdapter
        binding.productFavoriteRecycler.layoutManager = GridLayoutManager(context, 2)

        productRoomDBViewModel.getAllProductList()
            .observe(viewLifecycleOwner, Observer { favoriteList ->
                favoriteProductAdapter.differ.submitList(favoriteList)
            })

        favoriteProductAdapter.setItemClick(object : FavoriteClickInterface<ProductEntity> {
            override fun onClick(data: ProductEntity) {
                //Toast.makeText(context, "Product Click", Toast.LENGTH_LONG).show()
                Snackbar.make(view, "Selected Product..."+data.title, Snackbar.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("product_id", data.id)
                bundle.putString("product_title", data.title)
                bundle.putString("product_imageUrl", data.productImage)
                bundle.putDouble("product_price", data.price)
                bundle.putDouble("product_rating", data.rating)
                bundle.putBoolean("product_favorite", true)
                val transaction = fragmentManager?.beginTransaction()
                val productDetailsFragment = ProductDetailsFragment()
                productDetailsFragment.arguments = bundle

                transaction?.replace(R.id.container, productDetailsFragment)
                transaction?.addToBackStack(null)
                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction?.commit()

            }

            override fun onClickUnFavoriteListener(data: ProductEntity) {
                productRoomDBViewModel.deleteProduct(productEntity = data)
                //Toast.makeText(context, "Unfavorite product", Toast.LENGTH_LONG).show()
                Snackbar.make(view, "Unfavorite Product successfully..."+data.title, Snackbar.LENGTH_SHORT).show()
            }

            override fun onClickBuyListener(data: ProductEntity) {
               // Toast.makeText(context, "Buy product", Toast.LENGTH_LONG).show()
                Snackbar.make(view, "Buy product "+data.title, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}