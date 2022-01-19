package com.example.baadbank.ui.crypto

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentCryptoBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.ui.CryptoAdapter
import com.example.baadbank.ui.login.cryptoBody
import com.example.baadbank.ui.login.currencyBody
import com.example.baadbank.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CryptoFragment : BaseFragment<FragmentCryptoBinding>(FragmentCryptoBinding::inflate) {

    private val viewModel: CryptoViewModel by activityViewModels()
    private lateinit var adapterCrypto: CryptoAdapter

    override fun start() {


        setRecycler()
//        adapterCrypto.setData(cryptoBody)

        getCoins()


    }

    private fun setRecycler() {
        adapterCrypto = CryptoAdapter()
        binding.recycler.apply {
            adapter = adapterCrypto
            layoutManager = LinearLayoutManager(requireContext())

        }
    }


    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }


    private fun getCoins() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadCoins03.collect {
                    when (it) {
                        is Resource.Loading -> {
                            progressBar(true)

                        }
                        is Resource.Success -> {
                            progressBar(false)
                            adapterCrypto.setData(it.data!!)

                        }
                        is Resource.Error -> {
                            progressBar(false)
                            view?.makeSnackbar("${it.message}")
                        }

                    }

                }

            }

        }

    }

}


