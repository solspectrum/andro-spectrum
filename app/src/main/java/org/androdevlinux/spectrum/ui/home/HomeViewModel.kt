package org.androdevlinux.spectrum.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.androdevlinux.spectrum.data.model.AddressResponse
import org.androdevlinux.spectrum.data.model.WalletResponse
import org.androdevlinux.spectrum.data.network.Resource
import org.androdevlinux.spectrum.data.repo.WalletRepo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WalletRepo) : ViewModel() {
    private val _seedPhrase: MutableLiveData<Resource<WalletResponse>> = MutableLiveData()
    val seedPhrase: LiveData<Resource<WalletResponse>>
        get() = _seedPhrase



    fun getSeedPhrase() = viewModelScope.launch {
        _seedPhrase.value = Resource.Loading
        _seedPhrase.value = repository.getSeedPhrase()
    }
}