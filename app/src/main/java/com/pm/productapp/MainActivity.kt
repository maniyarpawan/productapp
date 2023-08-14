package com.pm.productapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pm.productapp.databinding.ActivityMainBinding
import com.pm.productapp.roomdb.ProductRoomDBViewModel
import com.pm.productapp.ui.FavoriteProductFragment
import com.pm.productapp.ui.ProductListFragment
import com.pm.productapp.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val productViewModel: ProductViewModel by viewModels()
    val productRoomDBViewModel: ProductRoomDBViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productListFragment = ProductListFragment()
        val favoriteProductFragment = FavoriteProductFragment()
       setCurrentFragment(productListFragment)

        binding.navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_products -> setCurrentFragment(productListFragment)
                R.id.menu_favorite -> setCurrentFragment(favoriteProductFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
}