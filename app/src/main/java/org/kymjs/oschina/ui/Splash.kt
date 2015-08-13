package org.kymjs.oschina.ui;

import android.view.KeyEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import org.kymjs.kjframe.KJActivity
import org.kymjs.oschina.AppConfig
import org.kymjs.oschina.R

/**
 * APP启动界面
 * @author kymjs (http://www.kymjs.com/) on 8/10/15.
 */
public class Splash : KJActivity() {
    volatile var animIsEnd = false

    override fun setRootView() {
        val rootView: ImageView = ImageView(aty)
        rootView.setImageResource(R.drawable.splash_bg)

        var anim: Animation = AnimationUtils.loadAnimation(aty, R.anim.splash_start)
        anim?.setAnimationListener(SplashAnimation())
        rootView.setAnimation(anim);

        setContentView(rootView);
    }

    inner class SplashAnimation : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            animIsEnd = true
        }
    }

    override fun initDataFromThread() {
        super.initDataFromThread()
        while (true) {
            if (animIsEnd) {
                break
            }
        }
    }

    override fun threadDataInited() {
        super.threadDataInited()
        if (AppConfig.isLogin()) {
            skipActivity(aty, javaClass<MainActivity>())
        } else {
            skipActivity(aty, javaClass<Login>())
        }
    }

    override fun onKeyDown(keyCode: Int, event: android.view.KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }
}
