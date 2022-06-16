package org.androdevlinux.spectrum.ui.paymentCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.androdevlinux.spectrum.databinding.FragmentPaymentCodeBinding


class PaymentCodeFragment : Fragment() {

    private var _binding: FragmentPaymentCodeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentPaymentCodeBinding.inflate(inflater, container, false)
        /* val root: View = binding.root

         val textView: TextView = binding.textNotifications
         settingsViewModel.text.observe(viewLifecycleOwner) {
             textView.text = it
         }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        bundle?.let {
            it.getString("address")?.let { code ->
                binding.hashCodeView.text = code
                try {
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap = barcodeEncoder.encodeBitmap("asfadsgsfgdfgdfgsdgsdfadfasd", BarcodeFormat.QR_CODE, 400, 400)
                    binding.apply {
                        qrCode.setImageBitmap(bitmap)
                    }
                    binding.qrCode.setImageBitmap(bitmap)
                } catch (e: Exception) {
                }

            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}