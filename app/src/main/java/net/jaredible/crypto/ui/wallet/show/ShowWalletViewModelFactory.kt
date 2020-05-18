package net.jaredible.crypto.ui.wallet.show

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShowWalletViewModelFactory(private val application: Application, private val walletId: Int) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowWalletViewModel::class.java)) return ShowWalletViewModel(application, walletId) as T
        throw IllegalArgumentException("Unknown ViewModel class!")
    }

}