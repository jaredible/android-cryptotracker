package net.jaredible.crypto.ui.wallet.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.ui.base.BaseAdapter
import net.jaredible.crypto.ui.base.BaseViewHolder

class WalletListAdapter(private val walletListView: WalletListView) : BaseAdapter<WalletListAdapter.WalletListViewHolder>() {

    val wallets = mutableListOf<Wallet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val itemGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false)
        return WalletListViewHolder(itemGroup)
    }

    override fun getItemCount() = wallets.size

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) = holder.bind(position)

    fun setWallets(wallets: List<Wallet>) {
        this.wallets.clear()
        this.wallets.addAll(wallets)
        notifyDataSetChanged()
    }

    inner class WalletListViewHolder(private val view: View) : BaseViewHolder(view) {

        private val vWalletName: TextView = view.findViewById(R.id.vWalletName)
        private val vConvertedBalance: TextView = view.findViewById(R.id.vConvertedBalance)
        private val vBalance: TextView = view.findViewById(R.id.vBalance)

        override fun bind(position: Int) {
            val wallet = wallets[position]

            vWalletName.text = wallet.cryptoSymbol
            vConvertedBalance.text = "$ 123,456.99"
            vBalance.text = "420.69"
        }

    }

}