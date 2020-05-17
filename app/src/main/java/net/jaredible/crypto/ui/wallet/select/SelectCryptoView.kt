package net.jaredible.crypto.ui.wallet.select

import net.jaredible.crypto.data.model.Crypto

interface SelectCryptoView {

    fun onCryptoSelected(crypto: Crypto)

}