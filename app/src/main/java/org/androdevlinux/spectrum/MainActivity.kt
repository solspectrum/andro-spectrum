package org.androdevlinux.spectrum

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import info.guardianproject.netcipher.NetCipher
import info.guardianproject.netcipher.client.StrongBuilder
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder
import info.guardianproject.netcipher.proxy.OrbotHelper
import okhttp3.OkHttpClient
import org.androdevlinux.spectrum.databinding.ActivityMainBinding
import java.io.IOException
import java.lang.Exception


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), StrongBuilder.Callback<OkHttpClient> {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_accounts,
                R.id.navigation_settings,
                R.id.navigation_payment_code
            )
        )
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
        binding.navView.setupWithNavController(navHostFragment.navController)
        val  navController=findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home->navController.navigate(R.id.navigation_home)
                R.id.navigation_accounts->navController.navigate(R.id.navigation_accounts)
                R.id.navigation_settings->navController.navigate(R.id.navigation_settings)
                R.id.navigation_payment_code->navController.navigate(R.id.navigation_payment_code)
            }
            true
        }
        try {
            StrongOkHttpClientBuilder
                .forMaxSecurity(this)
                .withTorValidation()
                .build(this)
        } catch (e: Exception) {
            println(e.message)
            Toast.makeText(this, e.message, Toast.LENGTH_LONG)
                .show();
        }
    }


    private fun currentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val fragmentManager = navHostFragment?.childFragmentManager
        fragmentManager?.addOnBackStackChangedListener {
            val backStackEntryCount = fragmentManager.backStackEntryCount
            val fragmentCount = fragmentManager.fragments.size

            println("backStackEntryCount: $backStackEntryCount, fragmentCount: $fragmentCount")
        }
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        return navHost.childFragmentManager.fragments[0]
    }

    override fun onConnected(conn: OkHttpClient?) {
        println("--------------------Connection created-------------------------------")
        try {
        } catch (e: IOException) {
            onConnectionException(e);
        }
    }

    override fun onConnectionException(e: Exception?) {
        println("--------------------Connection Exception occurred = ${e?.message}-------------------------------")
    }

    override fun onTimeout() {
        println("--------------------Connection Timeout -------------------------------")
    }

    override fun onInvalid() {
        println("--------------------Connection Invalid -------------------------------")
    }
}