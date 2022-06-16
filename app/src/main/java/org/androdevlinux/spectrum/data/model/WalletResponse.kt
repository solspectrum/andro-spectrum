package org.androdevlinux.spectrum.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class WalletResponse(
    @SerializedName("seedphrase") val seedPhrase: String? = null
)