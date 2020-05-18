package net.jaredible.crypto.ui.wallet.remove

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_remove_wallet.*
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseDialog
import net.jaredible.crypto.ui.wallet.add.AddWalletView
import java.lang.ClassCastException

class RemoveWalletDialog : BaseDialog() {

    companion object {
        val TAG = RemoveWalletDialog::class.simpleName

        fun newInstance(): RemoveWalletDialog {
            return RemoveWalletDialog()
        }
    }

    private lateinit var addWalletView: AddWalletView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is AddWalletView) {
            addWalletView = context
        } else {
            throw ClassCastException("$context must implement AddWalletView")
        }
    }

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
        return inflater.inflate(R.layout.dialog_remove_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vRemove.setOnClickListener {
            addWalletView.onRemoveWallet()
            dismiss()
        }
        vCancel.setOnClickListener { dismiss() }
    }

}