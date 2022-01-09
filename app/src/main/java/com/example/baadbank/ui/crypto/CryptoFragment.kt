package com.example.baadbank.ui.crypto

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentCryptoBinding
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.ui.CryptoAdapter
import com.example.baadbank.ui.login.cryptoBody
import com.example.baadbank.ui.login.currencyBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoFragment : BaseFragment<FragmentCryptoBinding>(FragmentCryptoBinding::inflate) {

    private lateinit var adapterCrypto: CryptoAdapter

    override fun start() {

//        getCoins()

        setRecycler()
        adapterCrypto.setData(cryptoBody)

//        getCoinsGecko()

    }

    private fun setRecycler(){
        adapterCrypto = CryptoAdapter()
        binding.recycler.apply {
            adapter=adapterCrypto
            layoutManager=LinearLayoutManager(requireContext())

        }
    }

    private fun getCoinsGecko(){

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.apiCoinGecko.getCoinGecko()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
//                    adapterCrypto.setData(body)

                } else {
                    Log.d("---", "${response.code()}")

                }
            }
        }

    }

    private fun getCoins() {
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.apiCoin.getCoins()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")

                } else {
                    Log.d("---", "${response.code()}")

                }
            }
        }


    }






}