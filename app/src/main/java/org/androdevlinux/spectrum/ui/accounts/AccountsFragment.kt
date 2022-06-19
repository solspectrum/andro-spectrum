package org.androdevlinux.spectrum.ui.accounts

import android.R.interpolator.linear
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import dagger.hilt.android.AndroidEntryPoint
import org.androdevlinux.spectrum.R
import org.androdevlinux.spectrum.data.PreferenceProvider
import org.androdevlinux.spectrum.data.model.AddressResponse
import org.androdevlinux.spectrum.data.network.Resource
import org.androdevlinux.spectrum.databinding.FragmentAccountsBinding
import org.androdevlinux.spectrum.ui.AccountUiFragment
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
                findNavController().navigate(R.id.navigation_address, bundle)
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
                    addButton(colorCode, name)
                }
            }
        }
    }

    private fun addButton(colorCode: String, btnName: String) {


        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,/* 1.0f*/
        )
        param.setMargins(0, 10, 0, 0)

        val btn = arrayOfNulls<Button>(AccountUiFragment.names.size)
        for (i in 0 until AccountUiFragment.names.size) {
            btn[i] = Button(requireContext())
            btn[i]?.text = AccountUiFragment.names[i]
            btn[i]?.setBackgroundColor(Color.parseColor(colorCode))
            btn[i]?.textSize = 20f
//            btn[i]?.height = 100
            btn[i]?.layoutParams = param
            btn[i]?.setPadding(15, 5, 15, 5)
            btn[i]?.marginBottom
            binding.mainLayout.addView(btn[i])
            btn[i]?.setOnClickListener {
                pref.getSeedPhrase()?.let { viewModel.getAddress(i.toString(), it) }
            }
        }
    }


}