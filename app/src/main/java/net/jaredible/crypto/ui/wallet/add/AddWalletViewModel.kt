package net.jaredible.crypto.ui.wallet.add

import android.app.Application
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.data.repository.WalletRepository
import net.jaredible.crypto.ui.base.BaseViewModel
import net.jaredible.crypto.util.Const

class AddWalletViewModel(application: Application) : BaseViewModel(application) {

    fun saveWallet(wallet: Wallet) {
        WalletRepository.addWallet(wallet)
    }

    fun deleteWallet(wallet: Wallet) = WalletRepository.deleteWallet(wallet)

    fun getCrypto(symbol: String) = CryptoRepository.getCrypto(symbol)

    fun getDefaultCrypto() = CryptoRepository.getCrypto(Const.DEFAULT_CRYPTO)

}