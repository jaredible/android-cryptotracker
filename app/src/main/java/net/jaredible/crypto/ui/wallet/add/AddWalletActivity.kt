package net.jaredible.crypto.ui.wallet.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_wallet.*
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.MessageType
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.wallet.remove.RemoveWalletDialog
import net.jaredible.crypto.ui.wallet.select.SelectCryptoActivity
import net.jaredible.crypto.util.observeOnce

class AddWalletActivity : BaseActivity(), AddWalletView {

    companion object {
        private const val EXTRA_WALLET = "net.jaredible.crypto.EXTRA_WALLET"
        private const val BUNDLE_CRYPTO = "BUNDLE_CRYPTO"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AddWalletActivity::class.java)
        }

        fun getStartIntent(context: Context, wallet: Wallet): Intent {
            val intent = Intent(context, AddWalletActivity::class.java)
            intent.putExtra(EXTRA_WALLET, wallet)
            return intent
        }
    }

    private lateinit var viewModel: AddWalletViewModel
    private var editingWallet: Wallet? = null
    private lateinit var selectedCrypto: Crypto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wallet)

        setSupportActionBar(vToolbar)
        vToolbar.setNavigationIcon(R.drawable.ic_close)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(this).get(AddWalletViewModel::class.java)

        if (intent.hasExtra(EXTRA_WALLET)) {
            val wallet: Wallet? = intent.getParcelableExtra(EXTRA_WALLET)
            wallet?.let {
                setWallet(wallet)
            }
        } else {
            viewModel.getDefaultCrypto().observeOnce(this, Observer {
                if (savedInstanceState == null) setCrypto(it)
            })
        }

        if (savedInstanceState == null) {
            vRoot.apply {
                visibility = View.INVISIBLE
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                            startRevealTransition(vRoot)
                        }
                    })
                }
            }
        } else {
            selectedCrypto = savedInstanceState.getParcelable(BUNDLE_CRYPTO)!!
            setCrypto(selectedCrypto)
        }

        vCryptosLayout.setOnClickListener { openSelectCryptoScreen() }
        vSaveWallet.setOnClickListener { addWallet() }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BUNDLE_CRYPTO, selectedCrypto)
    }

    private fun setWallet(wallet: Wallet) {
        editingWallet = wallet
        viewModel.getCrypto(wallet.symbol).observeOnce(this, Observer { setCrypto(it) })
        vName.setText(wallet.name)
        vBalance.setText(wallet.balance.toString())
        vRemoveWallet.visibility = View.VISIBLE
        vRemoveWallet.setOnClickListener { removeWallet(wallet.id) }
    }

    private fun removeWallet(id: Int) {
        val dialog = RemoveWalletDialog.newInstance()
        dialog.show(supportFragmentManager, RemoveWalletDialog.TAG)
    }

    private fun setCrypto(crypto: Crypto) {
        selectedCrypto = crypto
        vCryptoSymbol.text = crypto.symbol
        vCryptoName.text = crypto.name
        Glide
            .with(this)
            .load(crypto.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.logo_btc)
            .into(vCryptoLogo)
    }

    private fun addWallet() {
        val name = vName.text.toString()
        val symbol = selectedCrypto.symbol
        val balance = vBalance.text.toString().toDoubleOrNull()

        if (name.isBlank()) {
            showMessage(getString(R.string.invalid_name), MessageType.WARN)
            return
        } else if (balance == null) {
            showMessage(getString(R.string.invalid_balance), MessageType.WARN)
            return
        }

        editingWallet?.let {
            viewModel.deleteWallet(it)
        }

        viewModel.saveWallet(Wallet(0, name, symbol, balance))
        showMessage(getString(R.string.wallet_saved), MessageType.SUCCESS)
        finish()
    }

    override fun onRemoveWallet() {
        editingWallet?.let {
            viewModel.deleteWallet(it)
            showMessage(getString(R.string.wallet_removed), MessageType.SUCCESS)
            finish()
        }
    }

    override fun openSelectCryptoScreen() {
        val intent = SelectCryptoActivity.newIntent(this)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val crypto = it.getParcelableExtra<Crypto>(SelectCryptoActivity.RESULT_CRYPTO)
                setCrypto(crypto)
            }
        }
    }

}