package net.jaredible.crypto.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_crypto_logo.view.*
import net.jaredible.crypto.R
import net.jaredible.crypto.data.model.Crypto

class CryptoLogoView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var mBackgroundColor = Color.TRANSPARENT

    init {
        inflate(getContext(), R.layout.view_crypto_logo, this)
        if (attrs != null) {
            val styleAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.CryptoLogoView)
            mBackgroundColor = styleAttrs.getColor(R.styleable.CryptoLogoView_clv_backgroundColor, mBackgroundColor)
            styleAttrs.recycle()
        }
        vClvLogo.imageTintList = ColorStateList.valueOf(mBackgroundColor)
    }

    fun setCrypto(crypto: Crypto) {
        clear()
        //vClvLogo.setImageResource(crypto.logoResId)
    }

    fun clear() {
        vClvLogo.setImageResource(0)
        vClvLogo.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
    }

}