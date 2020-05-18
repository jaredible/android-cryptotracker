package net.jaredible.crypto.ui.wallet.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.Price
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.repository.PreferenceRepository
import net.jaredible.crypto.ui.base.BaseAdapter
import net.jaredible.crypto.ui.base.BaseViewHolder
import net.jaredible.crypto.util.getCryptoFormat
import net.jaredible.crypto.util.getCurrencyFormat
import java.util.*

class WalletListAdapter(private val walletListView: WalletListView) : BaseAdapter<WalletListAdapter.WalletListViewHolder>() {

    private val wallets = mutableListOf<Wallet>()
    private val cryptos = mutableMapOf<String, Crypto>()
    private val prices = mutableMapOf<String, Price>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val itemGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false)
        return WalletListViewHolder(itemGroup)
    }

    override fun getItemCount() = wallets.size

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) = holder.bind(position)

    fun getWallets() = wallets

    fun setWallets(wallets: List<Wallet>) {
        this.wallets.clear()
        this.wallets.addAll(wallets)
    }

    fun removeWallet(position: Int) {
        val wallet: Wallet = wallets.removeAt(position)
        walletListView.onWalletRemoved(wallet)
    }

    fun getCryptos() = cryptos

    fun setCryptos(cryptos: List<Crypto>) {
        this.cryptos.clear()
        this.cryptos.putAll(cryptos.associateBy({ it.symbol }, { it }))
    }

    fun getPrices() = prices

    fun setPrices(prices: List<Price>) {
        this.prices.clear()
        this.prices.putAll(prices.associateBy({ it.symbol }, { it }))
    }

    inner class WalletListViewHolder(private val view: View) : BaseViewHolder(view) {

        private val vCryptoLogo: ImageView = view.findViewById(R.id.vCryptoLogo)
        private val vWalletName: TextView = view.findViewById(R.id.vWalletName)
        private val vConvertedBalance: TextView = view.findViewById(R.id.vConvertedBalance)
        private val vBalance: TextView = view.findViewById(R.id.vBalance)

        override fun bind(position: Int) {
            val wallet = wallets[position]
            val crypto = cryptos[wallet.symbol]
            val price = prices[wallet.symbol]

            Glide
                .with(view)
                .load(crypto!!.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.logo_btc)
                .into(vCryptoLogo)

            val currencyCode = PreferenceRepository.getCurrency().currencyCode.toUpperCase(Locale.getDefault())
            val convertedBalance = getCurrencyFormat().format(wallet.balance * price!!.price)
            val balance = getCryptoFormat().format(wallet.balance)
            val cryptoUnit = wallet.symbol.toUpperCase(Locale.getDefault())

            vWalletName.text = wallet.name
            vBalance.text = "$balance $cryptoUnit"
            vConvertedBalance.text = "$convertedBalance $currencyCode"

            view.setOnClickListener { walletListView.onWalletClicked(wallet) }
        }

    }

}