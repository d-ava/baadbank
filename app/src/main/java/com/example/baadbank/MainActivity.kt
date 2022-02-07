package com.example.baadbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils
import com.example.baadbank.util.Utils.commercialRatesList
import com.example.baadbank.util.Utils.currencyList
import com.example.baadbank.util.Utils.currencyListForAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getCommercialRatesList()
        getCurrency()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }



        setContentView(R.layout.activity_main)


    }

    private fun getCommercialRatesList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCommercialRates.collect {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {

                            commercialRatesList = it.data!!.commercialRatesList
                            Log.d("---", "commercial rates - $commercialRatesList")
                        }
                        is Resource.Error -> {
                        }

                    }
                }
            }
        }

    }

    private fun getCurrency() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCurrency.collect {
                    when (it) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {

                            Utils.currencyListForAdapter = it.data!!

                            Log.d("---", "currency list for adapter $currencyListForAdapter")

                            for (c in it.data) {
                                Utils.currencyList.add(c.currency)
                            }

                            Log.d("---", "currency list - $currencyList")


                        }
                        is Resource.Error -> {


                        }

                    }
                }
            }
        }

    }


}