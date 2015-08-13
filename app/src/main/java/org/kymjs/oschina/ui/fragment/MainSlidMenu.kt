package org.kymjs.oschina.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.kymjs.kjframe.KJBitmap
import org.kymjs.kjframe.ui.BindView
import org.kymjs.kjframe.ui.SupportFragment
import org.kymjs.kjframe.widget.RoundImageView
import org.kymjs.oschina.AppConfig
import org.kymjs.oschina.R
import org.kymjs.oschina.bean.User
import org.kymjs.oschina.ui.MainActivity
import rx.Observable
import rx.Subscriber

/**
 * 侧滑菜单Fragment

 * @author kymjs (http://www.kymjs.com/) on 8/11/15.
 */
public class MainSlidMenu : SupportFragment() {

    BindView(id = R.id.menu_img_head)
    private val headImage: RoundImageView? = null
    BindView(id = R.id.menu_img_gender)
    private val imgGender: ImageView? = null
    BindView(id = R.id.menu_tv_name)
    private val tvName: TextView? = null
    BindView(id = R.id.menu_tv_desc)
    private val tvDesc: TextView? = null

    BindView(id = R.id.menu_item_tag1, click = true)
    private val layoutTag1: LinearLayout? = null
    BindView(id = R.id.menu_item_tag2, click = true)
    private val layoutTag2: LinearLayout? = null

    public var rootView: View? = null

    private val kjb: KJBitmap = KJBitmap()
    private val user: User? = AppConfig.getUser();

    override fun inflaterView(p0: LayoutInflater?, p1: ViewGroup?, p2: Bundle?): View? {
        rootView = View.inflate(getActivity(), R.layout.frag_main_menu, null);
        return rootView
    }

    override fun initWidget(parentView: View) {
        kjb.displayWithErrorBitmap(headImage, user?.getPortrait() ?: "http://kymjs.com/image/logo_s.png", R.drawable.default_head)
        tvName?.setText(user?.getName() ?: "未知用户")
        if (user?.getGender() == 1) {
            imgGender?.setImageResource(R.drawable.userinfo_icon_male)
        } else if (user?.getGender() == 2) {
            imgGender?.setImageResource(R.drawable.userinfo_icon_female)
        }
        //        tvDesc?.setText("helloworld，一句话代表一个世界")
    }

    override fun widgetClick(v: View?) {
        super.widgetClick(v)
        when (v!!.getId()) {
            R.id.menu_item_tag1 -> sendChangeFragmentEven(1)
            R.id.menu_item_tag2 -> sendChangeFragmentEven(2)
        }
    }

    /**
     * 侧滑菜单中的item被点击，发送消息，让MainActivity响应这个事件
     */
    fun sendChangeFragmentEven(tag: Int) {
        var changeFragmentEven = Observable.create(object : Observable.OnSubscribe<Int> {
            override fun call(sub: Subscriber<in Int>) {
                sub.onNext(tag)
                sub.onCompleted()
            }
        })

        val aty: Activity = getActivity();
        if (aty is MainActivity)
            changeFragmentEven.subscribe(aty.changeContentSubscribers);
    }
}
