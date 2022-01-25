package com.example.baadbank.ui.currency

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.CurrencyItemsAdapter
import com.example.baadbank.databinding.FragmentCurrencyBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.currencyList
import com.example.baadbank.util.Utils.currencyListForAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


@AndroidEntryPoint
class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {

    private val viewModel: CurrencyViewModel by activityViewModels()
    private lateinit var adapter: CurrencyItemsAdapter

    override fun start() {

        setRecycler()

        getCurrency00()
        getCommercialRates()
//        adapter.setData(currencyListForAdapter)

//        setCommercialRates()

    }


private fun convertRates(rate: Double): String{
    return BigDecimal(rate).setScale(2,RoundingMode.HALF_EVEN).toPlainString().toString()

}


    private fun getCommercialRates(){

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loadCommercialRates.collect {
                    when(it){
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
//                            Log.d("---", "commercial rates fragment ${it.data!!}")
                            for (item in it.data!!.commercialRatesList){
                                if (item.currency == "USD"){
                                    binding.tvUsdBuyValue.text = convertRates(item.buy)
                                    binding.tvUsdSellValue.text = convertRates(item.sell)
                                }
                                if (item.currency == "EUR"){
                                    binding.tvEurBuyValue.text = convertRates(item.buy)
                                    binding.tvEurSellValue.text = convertRates(item.sell)
                                }
                                if (item.currency == "GBP"){
                                    binding.tvGbpBuyValue.text = convertRates(item.buy)
                                    binding.tvGbpSellValue.text = convertRates(item.sell)
                                }


                            }
                        }
                        is Resource.Error -> {}
                    }
                }
            }
        }
    }

    private fun getCurrency00() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadCurrency.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
//                            progressBar(true)
                        }
                        is Resource.Success -> {
                            hideLoading()
//                            progressBar(false)
                            for (item in it.data!!) {
                                if (item.currency == "USD") {
                                    binding.tvUSDCurrency.text = item.currency
                                    binding.tvUSDValue.text =
                                        BigDecimal(item.value).setScale(2, RoundingMode.HALF_EVEN)
                                            .toPlainString()
                                            .toString()
                                }
                                if (item.currency == "EUR") {
                                    binding.tvEURCurrency.text = item.currency
                                    binding.tvEURValue.text =
                                        BigDecimal(item.value).setScale(2, RoundingMode.HALF_EVEN)
                                            .toPlainString()
                                            .toString()
                                }
                                if (item.currency == "GBP") {
                                    binding.tvGBPCurrency.text = item.currency
                                    binding.tvGBPValue.text =
                                        BigDecimal(item.value).setScale(2, RoundingMode.HALF_EVEN)
                                            .toPlainString()
                                            .toString()
                                }


                            }

                            adapter.setData(it.data)
                            for (c in it.data) {
                                currencyList.add(c.currency)
                            }


                        }
                        is Resource.Error -> {
                            view?.makeSnackbar("${it.message}")
                        }
                    }
                }


            }
        }
    }




    private fun setRecycler() {

        adapter = CurrencyItemsAdapter()
        binding.apply {
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
        }


    }


}