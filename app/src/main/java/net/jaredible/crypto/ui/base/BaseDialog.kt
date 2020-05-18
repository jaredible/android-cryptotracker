package net.jaredible.crypto.ui.base

import androidx.fragment.app.DialogFragment
import es.dmoral.toasty.Toasty
import net.jaredible.crypto.data.model.MessageType

abstract class BaseDialog : DialogFragment() {

    fun showMessage(message: String, type: MessageType) =
        when (type) {
            MessageType.DEFAULT -> Toasty.normal(requireContext(), message).show()
            MessageType.SUCCESS -> Toasty.success(requireContext(), message).show()
            MessageType.INFO -> Toasty.info(requireContext(), message).show()
            MessageType.WARN -> Toasty.warning(requireContext(), message).show()
            MessageType.ERROR -> Toasty.error(requireContext(), message).show()
        }

}