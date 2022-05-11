package org.androdevlinux.spectrum.ui.paymentCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentCodeViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is PaymentCode Fragment"
    }
    val text: LiveData<String> = _text
}