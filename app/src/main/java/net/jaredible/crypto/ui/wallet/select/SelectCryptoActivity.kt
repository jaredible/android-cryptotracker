package net.jaredible.crypto.ui.wallet.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_select_crypto.*
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.util.observeOnce

class SelectCryptoActivity : BaseActivity(), SelectCryptoView {

    companion object {
        const val RESULT_CRYPTO = "RESULT_CRYPTO"

        fun newIntent(context: Context): Intent {
            return Intent(context, SelectCryptoActivity::class.java)
        }
    }

    private lateinit var viewAdapter: SelectCryptoAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewModel: SelectCryptoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_crypto)

        setSupportActionBar(vToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.choose_one)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = SelectCryptoAdapter(this)
        vCryptos.setHasFixedSize(true)
        vCryptos.layoutManager = viewManager
        vCryptos.adapter = viewAdapter

        viewModel = ViewModelProvider(this).get(SelectCryptoViewModel::class.java)
        viewModel.getCryptos().observeOnce(this, Observer {
            vProgress.visibility = View.INVISIBLE
            viewAdapter.setCryptos(it)
            viewAdapter.notifyDataSetChanged()
            vCryptos.scheduleLayoutAnimation()
        })
    }

    override fun onCryptoSelected(crypto: Crypto) {
        intent.putExtra(RESULT_CRYPTO, crypto)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}