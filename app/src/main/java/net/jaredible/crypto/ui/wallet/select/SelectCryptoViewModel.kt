package net.jaredible.crypto.ui.wallet.select

import android.app.Application
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.ui.base.BaseViewModel

class SelectCryptoViewModel(application: Application) : BaseViewModel(application) {

    fun getCryptos() = CryptoRepository.getAllCryptos()

}