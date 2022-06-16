package org.androdevlinux.spectrum.data.repo

import org.androdevlinux.spectrum.data.network.ApiService
import org.androdevlinux.spectrum.data.network.SafeApiCall
import javax.inject.Inject

class WalletRepo @Inject constructor(private val api: ApiService) : SafeApiCall {
    suspend fun getSeedPhrase() = safeApiCall {
        api.getSeedPhrase()
    }

    suspend fun getAddress(index: String, seed: String) = safeApiCall {
        api.getAddress(index, seed)
    }
}