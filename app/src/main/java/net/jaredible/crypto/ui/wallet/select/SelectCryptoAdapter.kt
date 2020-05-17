package net.jaredible.crypto.ui.wallet.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.ui.base.BaseAdapter
import net.jaredible.crypto.ui.base.BaseViewHolder

class SelectCryptoAdapter(private val selectCryptoView: SelectCryptoView) : BaseAdapter<SelectCryptoAdapter.SelectCryptoViewHolder>() {

    private val cryptos = mutableListOf<Crypto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCryptoViewHolder {
        val itemGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        return SelectCryptoViewHolder(itemGroup)
    }

    override fun getItemCount() = cryptos.size

    override fun onBindViewHolder(holder: SelectCryptoViewHolder, position: Int) = holder.bind(position)

    fun getCryptos() = cryptos

    fun setCryptos(cryptos: List<Crypto>) {
        this.cryptos.clear()
        this.cryptos.addAll(cryptos)
    }

    inner class SelectCryptoViewHolder(private val view: View) : BaseViewHolder(view) {

        private val vCryptoLogo: ImageView = view.findViewById(R.id.vCryptoLogo)
        private val vCryptoSymbol: TextView = view.findViewById(R.id.vCryptoSymbol)
        private val vCryptoName: TextView = view.findViewById(R.id.vCryptoName)

        override fun bind(position: Int) {
            val crypto = cryptos[position]

            Glide
                .with(view)
                .load(crypto.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.logo_btc)
                .into(vCryptoLogo)

            vCryptoSymbol.text = crypto.symbol
            vCryptoName.text = crypto.name

            view.setOnClickListener(onCryptoSelected(crypto))
        }

        private fun onCryptoSelected(crypto: Crypto): View.OnClickListener {
            return View.OnClickListener {
                selectCryptoView.onCryptoSelected(crypto)
            }
        }

    }

}