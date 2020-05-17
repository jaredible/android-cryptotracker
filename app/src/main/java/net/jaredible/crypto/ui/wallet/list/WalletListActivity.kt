package net.jaredible.crypto.ui.wallet.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog
import kotlinx.android.synthetic.main.activity_wallet_list.*
import net.jaredible.crypto.App
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.repository.PreferenceRepository
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.settings.SettingsActivity
import net.jaredible.crypto.ui.wallet.add.AddWalletActivity
import net.jaredible.crypto.util.getCurrencyFormat
import net.jaredible.crypto.util.observeOnce

class WalletListActivity : BaseActivity(), WalletListView {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, WalletListActivity::class.java)
        }
    }

    private lateinit var viewAdapter: WalletListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewModel: WalletListViewModel

    private var currentTotalBalance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_list)
        setSupportActionBar(vToolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(this).get(WalletListViewModel::class.java)

        viewManager = LinearLayoutManager(this)
        viewAdapter = WalletListAdapter(this)
        viewModel.getCryptos().observeOnce(this, Observer { cryptos ->
            viewAdapter.setCryptos(cryptos)
            viewModel.getPrices().observeOnce(this, Observer { prices ->
                viewAdapter.setPrices(prices)
                vWallets.setHasFixedSize(true)
                vWallets.layoutManager = viewManager
                vWallets.adapter = viewAdapter
            })
        })

        vRefresh.setOnRefreshListener { refresh() }
        vAddWallet.setOnClickListener { openAddWalletScreen() }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentTotalBalance = savedInstanceState.getDouble("TOTAL")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("TOTAL", currentTotalBalance)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> {
                openSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onResume() {
        super.onResume()
        refresh()
        vAddWallet.isEnabled = true
    }

    private fun refresh() {
        setContentRefreshing(true)
        viewModel.updatePrices().observeOnce(this, Observer {
            viewModel.getCryptos().observeOnce(this, Observer { cryptos ->
                viewModel.getPrices().observeOnce(this, Observer { prices ->
                    viewModel.getWallets().observeOnce(this, Observer { wallets ->
                        viewAdapter.setCryptos(cryptos)
                        viewAdapter.setPrices(prices)
                        setWallets(wallets)
                        viewAdapter.notifyDataSetChanged()
                        updateTotalBalance()
                        setContentRefreshing(false)
                    })
                })
            })
        })
    }

    private fun setWallets(wallets: List<Wallet>) {
        viewAdapter.setWallets(wallets)
        updateTotalBalance()
        setContentRefreshing(false)
    }

    private fun setContentRefreshing(refreshing: Boolean) {
        vRefresh.isRefreshing = refreshing
    }

    private fun updateTotalBalance() {
        val currencySymbol = PreferenceRepository.getCurrency().symbol
        val decimalFormat = getCurrencyFormat()
        var totalBalance = 0.0
        viewAdapter.getWallets().forEach {
            totalBalance += it.balance * viewAdapter.getPrices()[it.symbol]!!.price
        }
        vTotalBalance
            .setPrefix("$currencySymbol ")
            .setDecimalFormat(decimalFormat)
            .startAnimation(currentTotalBalance.toFloat(), totalBalance.toFloat())
        currentTotalBalance = totalBalance
    }

    override fun onWalletClicked(wallet: Wallet) {
        val intent = AddWalletActivity.getStartIntent(this, wallet)
        startActivity(intent)
    }

    override fun openAddWalletScreen() {
        val intent = AddWalletActivity.getStartIntent(this)
        startActivity(intent)
    }

    override fun openSettingsScreen() {
        val intent = SettingsActivity.getStartIntent(this)
        startActivity(intent)
    }

    private fun showDeleteDialog(wallet: Wallet) {
        AwesomeSuccessDialog(this)
            .setTitle(wallet.symbol)
            .setMessage("Are you sure to remove this wallet?")
            .setColoredCircle(R.color.red)
            .setDialogIconAndColor(R.drawable.ic_delete, R.color.white)
            .setCancelable(true)
            .setNegativeButtonText("Never mind")
            .setNegativeButtonTextColor(android.R.color.black)
            .setNegativeButtonbackgroundColor(android.R.color.white)
            .setNegativeButtonClick {  }
            .setPositiveButtonText("Yes, please")
            .setPositiveButtonbackgroundColor(R.color.red)
            .setPositiveButtonClick { }
            .show()
    }

}