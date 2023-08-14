package com.pm.productapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pm.productapp.MainActivity
import com.pm.productapp.R
import com.pm.productapp.adapter.ClickInterface
import com.pm.productapp.adapter.ProductAdapter
import com.pm.productapp.data.model.Product
import com.pm.productapp.databinding.FragmentProductListBinding
import com.pm.productapp.roomdb.ProductEntity
import com.pm.productapp.roomdb.ProductRoomDBViewModel
import com.pm.productapp.utils.NetworkResult
import com.pm.productapp.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productRoomDBViewModel: ProductRoomDBViewModel

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productViewModel = (activity as MainActivity).productViewModel
        productRoomDBViewModel = (activity as MainActivity).productRoomDBViewModel

        binding.productRecycler.adapter = productAdapter
        binding.productRecycler.layoutManager = GridLayoutManager(context, 2)

        productAdapter.setItemClick(object : ClickInterface<Product> {
            override fun onClick(data: Product) {
                //Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
                Snackbar.make(view, "Selected Product..."+data.title, Snackbar.LENGTH_SHORT).show()

                val bundle = Bundle()
                bundle.putString("product_id", data.id)
                bundle.putString("product_title", data.title)
                bundle.putString("product_imageUrl", data.imageURL)
                bundle.putDouble("product_price", data.price[0].value)
                bundle.putDouble("product_rating", data.ratingCount)
                bundle.putBoolean("product_favorite", false)
                val transaction = fragmentManager?.beginTransaction()
                val productDetailsFragment = ProductDetailsFragment()
                productDetailsFragment.arguments = bundle

                transaction?.replace(R.id.container, productDetailsFragment)
                transaction?.addToBackStack(null)
                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction?.commit()

            }

            override fun onClickFavoriteListener(data: Product) {
                //Toast.makeText(context, "Favorite Button Click", Toast.LENGTH_SHORT).show()
                Snackbar.make(view, "Favorite Product successfully..."+data.title, Snackbar.LENGTH_SHORT).show()
                val productEntity = ProductEntity(
                    data.id,
                    data.title,
                    data.ratingCount,
                    data.price[0].value,
                    true,
                    data.imageURL
                )
                productRoomDBViewModel.addProduct(productEntity = productEntity)
            }

            override fun onClickBuyListener(data: Product) {
                //Toast.makeText(context, "Buy Button Click", Toast.LENGTH_SHORT).show()
                Snackbar.make(view, "Buy product "+data.title, Snackbar.LENGTH_SHORT).show()
            }
        })

        productViewModel.productResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                }

                is NetworkResult.Success -> {
                    productAdapter.differ.submitList(it.data)
                    binding.progressbar.isVisible = false
                }
                else -> {}
            }
        }
    }
}