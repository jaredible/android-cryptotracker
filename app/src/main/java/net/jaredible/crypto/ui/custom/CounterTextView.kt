package net.jaredible.crypto.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.text.DecimalFormat

class CounterTextView(context: Context, attrs: AttributeSet? = null) :
    AppCompatTextView(context, attrs) {

    private val DURATION: Long = 2000

    private val animator: ValueAnimator
    private var prefix: String? = ""
    private var decimalFormat: DecimalFormat? = null

    init {
        animator = ValueAnimator().apply {
            duration = DURATION
            addUpdateListener({
                text = prefix + (decimalFormat?.format(it.animatedValue)
                    ?: it.animatedValue.toString())
            })
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    fun setPrefix(prefix: String): CounterTextView {
        this.prefix = prefix
        return this
    }

    fun setDecimalFormat(decimalFormat: DecimalFormat): CounterTextView {
        this.decimalFormat = decimalFormat
        return this
    }

    fun startAnimation(from: Float, to: Float) {
        if (animator.isRunning) {
            animator.cancel()
            animator.setFloatValues(animator.animatedValue.toString().toFloat(), to)
        } else {
            animator.setFloatValues(from, to)
        }
        animator.start()
    }

}