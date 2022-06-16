package org.androdevlinux.spectrum.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.androdevlinux.spectrum.data.model.AddressResponse
import org.androdevlinux.spectrum.data.network.Resource
import org.androdevlinux.spectrum.data.repo.WalletRepo
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(private val repository: WalletRepo) : ViewModel() {
    private val _address: MutableLiveData<Resource<AddressResponse>> = MutableLiveData()
    val address: LiveData<Resource<AddressResponse>>
        get() = _address

    fun getAddress(index: String, seed: String) = viewModelScope.launch {
        _address.value = Resource.Loading
        _address.value = repository.getAddress(index, seed)
    }

}