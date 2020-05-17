package net.jaredible.crypto.ui.wallet.list

import net.jaredible.crypto.data.model.Wallet

interface WalletListView {

    fun onWalletClicked(wallet: Wallet)

    fun openAddWalletScreen()

    fun openSettingsScreen()

}