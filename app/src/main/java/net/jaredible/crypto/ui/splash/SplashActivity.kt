package net.jaredible.crypto.ui.splash

import android.os.Bundle
import android.os.Handler
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.wallet.list.WalletListActivity

class SplashActivity : BaseActivity() {

    private lateinit var splashFragment: SplashFragment
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler?.postDelayed(Runnable {
            openWalletListScreen()
        }.also { runnable = it }, 5000)

        splashFragment = SplashFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.vContainer, splashFragment, SplashFragment.TAG)
            .commit()
    }

    override fun onDestroy() {
        runnable?.let { handler?.removeCallbacks(it) }
        handler = null
        runnable = null
        super.onDestroy()
    }

    private fun openWalletListScreen() {
        val intent = WalletListActivity.getStartIntent(this)
        startActivity(intent)
        finish()
    }

}