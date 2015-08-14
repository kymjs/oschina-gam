package org.kymjs.oschina.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.widget.DrawerLayout
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import org.kymjs.kjframe.KJActivity
import org.kymjs.kjframe.ui.BindView
import org.kymjs.kjframe.ui.KJActivityStack
import org.kymjs.kjframe.ui.SupportFragment
import org.kymjs.oschina.R
import org.kymjs.oschina.ui.fragment.FriendGroup
import org.kymjs.oschina.ui.fragment.MainSlidMenu
import org.kymjs.oschina.ui.fragment.Topic
import org.kymjs.oschina.ui.widget.menu.MaterialMenuDrawable
import org.kymjs.oschina.ui.widget.menu.MaterialMenuIcon
import org.kymjs.oschina.utils.KJAnimations
import rx.functions.Action1

/**
 * 应用主界面
 */
public class MainActivity : KJActivity() {

    @BindView(id = R.id.drawer_layout)
    private val drawerLayout: DrawerLayout? = null
    @BindView(id = R.id.titlebar_text_title, click = true)
    public val tvTitle: TextView? = null
    @BindView(id = R.id.titlebar_text_exittip, click = true)
    public var tvDoubleClickTip: TextView? = null
    @BindView(id = R.id.titlebar_img_back, click = true)
    private val imgBack: ImageView? = null

    private var materialMenuIcon: MaterialMenuIcon? = null

    private var content1: SupportFragment = FriendGroup()
    private var content2: SupportFragment = Topic()
    private var menuFragment: MainSlidMenu? = null

    private var titleBarHeight: Float = 0f
    private var isOpen: Boolean = false;
    private var isOnKeyBacking: Boolean = false


    protected val mMainLoopHandler: Handler = Handler(Looper.getMainLooper())

    /**
     * 响应侧滑菜单MainSlidMenu中的item点击事件
     */
    public val changeContentSubscribers: Action1<Int> = Action1() { tag ->
        when (tag) {
            1 -> changeFragment(content1)
            2 -> changeFragment(content2)
        }
    }

    public fun menuIsOpen(): Boolean = isOpen

    override fun setRootView() {
        setContentView(R.layout.activity_main)
    }

    override fun initData() {
        titleBarHeight = getResources().getDimension(R.dimen.titlebar_height)
    }

    override fun initWidget() {
        materialMenuIcon = MaterialMenuIcon(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN)
        drawerLayout?.setDrawerListener(DrawerLayoutStateListener());
        menuFragment = getSupportFragmentManager().findFragmentById(R.id.main_menu) as MainSlidMenu?;
        changeFragment(content1)
    }

    override fun widgetClick(v: View?) {
        when (v?.getId()) {
            R.id.titlebar_img_back -> changeMenuState()
            R.id.titlebar_text_title -> changeMenuState()
        }
    }

    fun changeMenuState() {
        if (isOpen) {
            drawerLayout?.closeDrawers()
        } else {
            drawerLayout?.openDrawer(menuFragment?.rootView)
        }
    }

    fun changeFragment(targetFragment: SupportFragment) {
        drawerLayout?.closeDrawers()
        changeFragment(R.id.main_content, targetFragment)
        targetFragment.onResume();
    }

    inner class DrawerLayoutStateListener : DrawerLayout.SimpleDrawerListener() {
        override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
            materialMenuIcon?.setTransformationOffset(
                    MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                    if (isOpen) {
                        2 - slideOffset
                    } else {
                        slideOffset
                    })
        }

        override fun onDrawerOpened(drawerView: View?) {
            isOpen = true
        }

        override fun onDrawerClosed(drawerView: View?) {
            isOpen = false
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        materialMenuIcon?.syncState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        materialMenuIcon?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * 取消退出
     */
    private fun cancleExit() {
        val anim = KJAnimations.getTranslateAnimation(0f, 0f, -titleBarHeight, 0f, 300)
        tvTitle?.startAnimation(anim)
        val anim2 = KJAnimations.getTranslateAnimation(0f, 0f, 0f, -titleBarHeight, 300)
        anim2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                tvDoubleClickTip?.setVisibility(View.GONE)
            }
        })
        tvDoubleClickTip?.startAnimation(anim2)
    }

    /**
     * 显示退出提示
     */
    private fun showExitTip() {
        tvDoubleClickTip?.setVisibility(View.VISIBLE)
        val anim = KJAnimations.getTranslateAnimation(0f, 0f, 0f, -titleBarHeight, 300)
        tvTitle?.startAnimation(anim)
        val anim2 = KJAnimations.getTranslateAnimation(0f, 0f, -titleBarHeight, 0f, 300)
        tvDoubleClickTip?.startAnimation(anim2)
    }

    private val onBackTimeRunnable = object : Runnable {
        override fun run() {
            isOnKeyBacking = false
            cancleExit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: android.view.KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isOnKeyBacking) {
                mMainLoopHandler.removeCallbacks(onBackTimeRunnable)
                isOnKeyBacking = false
                // UIHelper.toHome(aty);
                KJActivityStack.create().AppExit(aty)
            } else {
                isOnKeyBacking = true
                showExitTip()
                mMainLoopHandler.postDelayed(onBackTimeRunnable, 2000)
            }
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }
}
