package net.jaredible.crypto.ui.base

import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import net.jaredible.crypto.data.model.MessageType
import net.jaredible.crypto.util.intFrom

abstract class BaseActivity : AppCompatActivity() {

    fun startRevealTransition(view: View) {
        val x = 9 * view.width / 10
        val y = 9 * view.height / 10
        val startRadius = 0F
        val endRadius = view.width.coerceAtLeast(view.height).toFloat()
        val duration = intFrom(android.R.integer.config_mediumAnimTime).toLong()
        view.visibility = View.VISIBLE
        val animation = ViewAnimationUtils
            .createCircularReveal(view, x, y, startRadius, endRadius)
            .setDuration(duration)
        animation.start()
    }

    fun showMessage(message: String, type: MessageType) =
        when (type) {
            MessageType.DEFAULT -> Toasty.normal(this, message).show()
            MessageType.SUCCESS -> Toasty.success(this, message).show()
            MessageType.INFO -> Toasty.info(this, message).show()
            MessageType.WARN -> Toasty.warning(this, message).show()
            MessageType.ERROR -> Toasty.error(this, message).show()
        }

}