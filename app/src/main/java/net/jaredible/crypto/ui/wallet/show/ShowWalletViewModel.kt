package net.jaredible.crypto.ui.wallet.show

import android.app.Application
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.data.repository.WalletRepository
import net.jaredible.crypto.ui.base.BaseViewModel

class ShowWalletViewModel(application: Application, private val walletId: Int) : BaseViewModel(application) {

    fun getCrypto(symbol: String) = CryptoRepository.getCrypto(symbol)

    fun getWallet(id: Int) = WalletRepository.getWallet(id)

}