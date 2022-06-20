package org.androdevlinux.spectrum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.androdevlinux.spectrum.R
import org.androdevlinux.spectrum.databinding.FragmentAccountUiBinding


class AccountUiFragment : Fragment() {
    companion object {
        var names: MutableList<String> = mutableListOf()
    }

    private var _binding: FragmentAccountUiBinding? = null
    private val binding get() = _binding!!
    private lateinit var colorCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountUiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RGroup.setOnCheckedChangeListener { group, checkedId -> // find which radio button is selected
            when (checkedId) {
                R.id.color1 -> colorCode = "#EF64ED"
                R.id.color2 -> colorCode = "#5B9A94"
                R.id.color3 -> colorCode = "#CDDC39"
                R.id.color4 -> colorCode = "#F44336"
            }
        }
        binding.addAccount.setOnClickListener {
            if (this::colorCode.isInitialized && binding.accountName.text.isNullOrBlank().not()) {
                val bundle = Bundle()
                bundle.putString("colorCode", colorCode)
                bundle.putString("name", binding.accountName.text.toString())
                names.add(binding.accountName.text.toString())
                findNavController().navigate(R.id.to_navigation_accounts, bundle)
            }
        }
    }
}