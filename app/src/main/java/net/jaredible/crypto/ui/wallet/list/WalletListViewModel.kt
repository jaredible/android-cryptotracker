package net.jaredible.crypto.ui.wallet.list

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.data.repository.WalletRepository
import net.jaredible.crypto.ui.base.BaseViewModel

class WalletListViewModel(application: Application) : BaseViewModel(application) {

    init {
        viewModelScope.launch {
            CryptoRepository.addCrypto(Crypto("BTC", "Bitcoin"))
            CryptoRepository.addCrypto(Crypto("ETH", "Ethereum"))
            CryptoRepository.addCrypto(Crypto("LTC", "Litecoin"))
            WalletRepository.addWallet(Wallet("My BTC Wallet", "BTC", 420))
            WalletRepository.addWallet(Wallet("My ETH Wallet", "ETH", 69))
            WalletRepository.addWallet(Wallet("My LTC Wallet", "LTC", 12))
        }
    }

    fun getWallets() = WalletRepository.getWallets()

}