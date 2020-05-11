package net.jaredible.crypto.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    fun showMessage(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, text, duration).show()
    }

}