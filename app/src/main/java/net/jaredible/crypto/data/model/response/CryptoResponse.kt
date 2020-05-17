package net.jaredible.crypto.data.model.response

import com.google.gson.annotations.SerializedName
import net.jaredible.crypto.data.model.Crypto

data class CryptoResponse(
    @SerializedName("data")
    val data: Map<String, Crypto>
)