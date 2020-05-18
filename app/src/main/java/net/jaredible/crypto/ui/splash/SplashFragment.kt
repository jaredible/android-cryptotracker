package net.jaredible.crypto.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_splash.*
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseFragment

class SplashFragment : BaseFragment() {

    companion object {
        val TAG = SplashFragment::class.simpleName

        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(this)
            .load(R.raw.splash_background)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(vSplashImage)
    }

}