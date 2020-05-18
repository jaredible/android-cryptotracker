package net.jaredible.crypto.ui.wallet.list

import android.app.Application
import androidx.lifecycle.LiveData
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.data.repository.PriceRepository
import net.jaredible.crypto.data.repository.WalletRepository
import net.jaredible.crypto.ui.base.BaseViewModel

class WalletListViewModel(application: Application) : BaseViewModel(application) {

    fun getCryptos() = CryptoRepository.getAllCryptos()

    fun getPrices() = PriceRepository.getAllPrices()

    fun getWallets(): LiveData<List<Wallet>> = WalletRepository.getAllWallets()

    fun updatePrices() = PriceRepository.updatePrices()

    fun deleteWallet(wallet: Wallet) = WalletRepository.deleteWallet(wallet)

}