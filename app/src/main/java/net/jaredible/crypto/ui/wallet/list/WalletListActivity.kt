package net.jaredible.crypto.ui.wallet.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_wallet_list.*
import net.jaredible.crypto.R
import net.jaredible.crypto.isConnected
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.custom.SwipeController
import net.jaredible.crypto.ui.settings.SettingsActivity
import net.jaredible.crypto.ui.wallet.add.AddWalletActivity
import java.math.BigDecimal

class WalletListActivity : BaseActivity(), WalletListView {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, WalletListActivity::class.java)
        }
    }

    private lateinit var viewAdapter: WalletListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewModel: WalletListViewModel

    private var currentTotalBalance = BigDecimal.ZERO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_list)
        setSupportActionBar(vToolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewManager = LinearLayoutManager(this)
        viewAdapter = WalletListAdapter(this)
        vWallets.setHasFixedSize(true)
        vWallets.layoutManager = viewManager
        vWallets.adapter = viewAdapter

        viewModel = ViewModelProvider(this).get(WalletListViewModel::class.java)
        viewModel.getWallets().observe(this, Observer {
            viewAdapter.setWallets(it)
            vRefresh.isRefreshing = false
        })

        val itemTouchHelper = ItemTouchHelper(SwipeController())
        itemTouchHelper.attachToRecyclerView(vWallets)

        vRefresh.setOnRefreshListener { refresh() }
        vAddWallet.setOnClickListener { openAddWalletScreen() }

        updateTotalBalance()
    }

    override fun onResume() {
        super.onResume()
        refresh()
        vAddWallet.isEnabled = true
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

    private fun refresh() {
        if (isConnected()) {
            vRefresh.isRefreshing = true
            updateTotalBalance()
        }
    }

    private fun updateTotalBalance() {
        var totalBalance = BigDecimal.ZERO
        totalBalance += "100000".toBigDecimal()
        vTotalBalance
            .setPrefix("B ")
            .startAnimation(currentTotalBalance.toFloat(), totalBalance.toFloat())
        currentTotalBalance = totalBalance
    }

    override fun openAddWalletScreen() {
        val intent = AddWalletActivity.getStartIntent(this)
        startActivity(intent)
    }

    override fun openSettingsScreen() {
        val intent = SettingsActivity.getStartIntent(this)
        startActivity(intent)
    }

}