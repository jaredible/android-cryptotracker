package net.jaredible.crypto.ui.wallet.add

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_add_wallet.*
import net.jaredible.crypto.R
import net.jaredible.crypto.intFrom
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.wallet.list.WalletListActivity

class AddWalletActivity : BaseActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, AddWalletActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wallet)

        if (savedInstanceState == null) {
            vRoot.apply {
                visibility = View.INVISIBLE
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                            startRevealTransition()
                        }
                    })
                }
            }
        }
    }

    private fun startRevealTransition(){
        val x = 9 * vRoot.width / 10
        val y = 9 * vRoot.height / 10
        val startRadius = 0F
        val endRadius = Math.max(vRoot.width, vRoot.height).toFloat()
        val duration = intFrom(android.R.integer.config_mediumAnimTime).toLong()

        vRoot.visibility = View.VISIBLE
        val animation = ViewAnimationUtils
            .createCircularReveal(vRoot, x, y, startRadius, endRadius)
            .setDuration(duration)
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { }
            override fun onAnimationEnd(animation: Animator?) {
                //vScanQrCode.playAnimation()
            }
            override fun onAnimationCancel(animation: Animator?) { }
            override fun onAnimationRepeat(animation: Animator?) { }
        })
        animation.start()
    }

}