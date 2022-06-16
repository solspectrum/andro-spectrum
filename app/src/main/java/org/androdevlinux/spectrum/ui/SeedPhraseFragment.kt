package org.androdevlinux.spectrum.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.androdevlinux.spectrum.R
import org.androdevlinux.spectrum.data.PreferenceProvider
import org.androdevlinux.spectrum.databinding.FragmentHomeBinding
import org.androdevlinux.spectrum.databinding.FragmentSeedPhraseBinding
import javax.inject.Inject

@AndroidEntryPoint
class SeedPhraseFragment : Fragment() {
    @Inject
    lateinit var pref: PreferenceProvider

    private var _binding: FragmentSeedPhraseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeedPhraseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSeedPhrase.text = pref.getSeedPhrase()
        binding.btnAccounts.setOnClickListener {
            findNavController().navigate(R.id.navigation_addAccount)
        }
    }

}