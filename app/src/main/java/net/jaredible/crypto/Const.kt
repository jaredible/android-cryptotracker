package net.jaredible.crypto

import net.jaredible.crypto.data.model.Crypto

object Const {
    // API
    const val CRYPTO_API_BASE_URL = "https://min-api.cryptocompare.com/data/"
    const val MEME_API_BASE_URL= "https://api.imgur.com/3/"

    // Currency
    val CRYPTO_BTC = Crypto("BTC", "Bitcoin")
    const val DEFAULT_CURRENCY = "USD"
}