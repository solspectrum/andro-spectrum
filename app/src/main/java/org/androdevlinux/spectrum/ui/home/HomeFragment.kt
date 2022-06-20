package org.androdevlinux.spectrum.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.androdevlinux.spectrum.R
import org.androdevlinux.spectrum.data.PreferenceProvider
import org.androdevlinux.spectrum.data.model.WalletResponse
import org.androdevlinux.spectrum.data.network.Resource
import org.androdevlinux.spectrum.databinding.FragmentHomeBinding
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var pref: PreferenceProvider


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.seedPhrase.observe(viewLifecycleOwner) { handleResponse(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreateWallet.setOnClickListener {
            viewModel.getSeedPhrase()
        }
    }


    private fun handleResponse(res: Resource<WalletResponse>) {
        binding.progressBar.visibility = View.GONE
        when (res) {
            is Resource.Success -> when (res.value.seedPhrase.isNullOrBlank().not()) {
                true -> res.value.seedPhrase?.let {
                    pref.setSeedPhrase(it)
                    findNavController().navigate(R.id.navigation_seed_phrase)
                }
                else -> println(res.toString())
            }
            is Resource.Failure -> println(res.errorBody)
            Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
        }
    }
}