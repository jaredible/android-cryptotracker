package net.jaredible.crypto.ui.wallet.show

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_show_wallet.*
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseDialog
import net.jaredible.crypto.util.observeOnce

class ShowWalletDialog : BaseDialog() {

    companion object {
        val TAG = ShowWalletDialog::class.simpleName
        private const val ARGUMENT_WALLET_ID = "ARGUMENT_WALLET_ID"

        fun newInstance(walletId: Int): ShowWalletDialog {
            val dialog = ShowWalletDialog()
            val args = Bundle()
            args.putInt(ARGUMENT_WALLET_ID, walletId)
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: ShowWalletViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_show_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val walletId = requireArguments().getInt(ARGUMENT_WALLET_ID, 0)
        val viewModelFactory = ShowWalletViewModelFactory(requireActivity().application, walletId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShowWalletViewModel::class.java)

        viewModel.getWallet(walletId).observeOnce(this, Observer { wallet ->
            vName.text = wallet.name
            vBalance.text = wallet.balance.toString()
            viewModel.getCrypto(wallet.symbol).observeOnce(this, Observer { crypto ->
                Glide
                    .with(view)
                    .load(crypto.imageUrl)
                    .centerCrop()
                    .into(vLogo)
            })
        })
    }

}