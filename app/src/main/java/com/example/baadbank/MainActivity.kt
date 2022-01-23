package com.example.baadbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.login.LoginViewModel
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getCurrency()
        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                viewModel.isLoading.value
//            }
        }
        setContentView(R.layout.activity_main)


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
                        }
                        is Resource.Error -> {
                        }

                    }
                }
            }
        }

    }

//    private fun getCurrency00() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.loadCurrency.collect {
//                    when (it) {
//                        is Resource.Loading -> {
//                            showLoading()
////                            progressBar(true)
//                        }
//                        is Resource.Success -> {
//                            hideLoading()
////                            progressBar(false)
//                            for (item in it.data!!) {
//                                if (item.currency == "USD") {
//                                    binding.tvUSDCurrency.text = item.currency
//                                    binding.tvUSDValue.text =
//                                        BigDecimal(item.value).setScale(2, RoundingMode.HALF_EVEN)
//                                            .toPlainString()
//                                            .toString()
//                                }
//                                if (item.currency == "EUR") {
//                                    binding.tvEURCurrency.text = item.currency
//                                    binding.tvEURValue.text =
//                                        BigDecimal(item.value).setScale(2, RoundingMode.HALF_EVEN)
//                                            .toPlainString()
//                                            .toString()
//                                }
//                                if (item.currency == "GBP") {
//                                    binding.tvGBPCurrency.text = item.currency
//                                    binding.tvGBPValue.text =
//                                        BigDecimal(item.value).setScale(2, RoundingMode.HALF_EVEN)
//                                            .toPlainString()
//                                            .toString()
//                                }
//
//
//                            }
//
//                            adapter.setData(it.data)
//                            for (c in it.data) {
//                                Utils.currencyList.add(c.currency)
//                            }
//
//
//                        }
//                        is Resource.Error -> {
//                            view?.makeSnackbar("${it.message}")
//                        }
//                    }
//                }
//
//
//            }
//        }
//    }

}