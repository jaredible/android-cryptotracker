package net.jaredible.crypto.ui.wallet.list

import android.app.Application
import net.jaredible.crypto.data.repository.WalletRepository
import net.jaredible.crypto.ui.base.BaseViewModel

class WalletListViewModel(application: Application) : BaseViewModel(application) {

    fun getWallets() = WalletRepository.getAllWallets()

}