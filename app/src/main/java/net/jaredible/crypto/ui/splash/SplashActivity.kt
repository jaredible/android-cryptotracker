package net.jaredible.crypto.ui.splash

import android.os.Bundle
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.wallet.list.WalletListActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openWalletListScreen()
    }

    private fun openWalletListScreen() {
        val intent = WalletListActivity.getStartIntent(this)
        startActivity(intent)
        finish()
    }

}