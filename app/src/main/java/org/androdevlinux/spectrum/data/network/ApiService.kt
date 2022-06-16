package org.androdevlinux.spectrum.data.network


import org.androdevlinux.spectrum.data.model.AddressResponse
import org.androdevlinux.spectrum.data.model.WalletResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/create")
    suspend fun getSeedPhrase(): WalletResponse

    @GET("/account")
    suspend fun getAddress(
        @Query("index") index: String,
        @Query("seed") seed: String,
    ): AddressResponse
}