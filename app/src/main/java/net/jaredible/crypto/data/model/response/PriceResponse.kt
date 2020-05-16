package net.jaredible.crypto.data.model.response

import com.google.gson.annotations.SerializedName
import net.jaredible.crypto.data.model.Price

data class PriceResponse(
    @SerializedName("data")
    val data: Map<String, Price>
)