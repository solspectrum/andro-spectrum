package org.androdevlinux.spectrum.ui.accounts

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.androdevlinux.spectrum.R
import org.androdevlinux.spectrum.data.PreferenceProvider
import org.androdevlinux.spectrum.data.model.AddressResponse
import org.androdevlinux.spectrum.data.network.Resource
import org.androdevlinux.spectrum.databinding.FragmentAccountsBinding
import javax.inject.Inject

@AndroidEntryPoint
class AccountsFragment : Fragment() {
    companion object {
        var btnCount: MutableList<Int> = mutableListOf()
    }

    private var _binding: FragmentAccountsBinding? = null
    private val viewModel by viewModels<AccountsViewModel>()

    private val binding get() = _binding!!
    lateinit var colorCode: String
    lateinit var btnName: String
    val i = 1

    @Inject
    lateinit var pref: PreferenceProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        viewModel.address.observe(viewLifecycleOwner) { handleResponse(it) }
        return binding.root
    }

    private fun handleResponse(res: Resource<AddressResponse>) {
        when (res) {
            is Resource.Success -> res.value.address?.let {
                val bundle = Bundle()
                bundle.putString("address", it)
                findNavController().navigate(R.id.navigation_payment_code)
            }

            is Resource.Failure -> println(res.errorBody)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        bundle?.let {
            it.getString("colorCode")?.let { code ->
                colorCode = code
                it.getString("name")?.let { name ->
                    btnName = name
                    createButtonDynamically(colorCode, btnName)
                }
            }


        }
    }

    private fun createButtonDynamically(colorCode: String, btnName: String) {
        val rowTextView = Button(requireContext())
        rowTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )  // add the textview to the linearlayout
        binding.mainLayout.addView(rowTextView, i)
        rowTextView.text = btnName
        rowTextView.setBackgroundColor(Color.parseColor(colorCode))
        rowTextView.setOnClickListener {
            pref.getSeedPhrase()?.let { viewModel.getAddress(i.toString(), it) }
        }
        i.inc()
    }
}