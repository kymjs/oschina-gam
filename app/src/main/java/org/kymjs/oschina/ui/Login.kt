package org.kymjs.oschina.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout

import org.kymjs.kjframe.KJActivity
import org.kymjs.kjframe.KJHttp
import org.kymjs.kjframe.http.HttpCallBack
import org.kymjs.kjframe.http.HttpConfig
import org.kymjs.kjframe.http.HttpParams
import org.kymjs.kjframe.ui.BindView
import org.kymjs.kjframe.ui.ViewInject
import org.kymjs.kjframe.utils.DensityUtils
import org.kymjs.kjframe.utils.KJLoger
import org.kymjs.kjframe.utils.StringUtils
import org.kymjs.kjframe.widget.RoundImageView
import org.kymjs.oschina.AppConfig
import org.kymjs.oschina.R
import org.kymjs.oschina.bean.LoginData
import org.kymjs.oschina.bean.User
import org.kymjs.oschina.utils.KJAnimations
import org.kymjs.oschina.utils.Parser

/**
 * 登录界面

 * @author kymjs (http://www.kymjs.com/) on 8/11/15.
 */
public class Login : KJActivity() {
    // wdiget
    BindView(id = R.id.login_img_logo)
    private val mImgLogo: ImageView? = null
    BindView(id = R.id.login_layout_input)
    private val mLayoutInput: RelativeLayout? = null
    BindView(id = R.id.login_et_uid)
    private val mEtUid: EditText? = null
    BindView(id = R.id.login_img_delete, click = true)
    private val mImgDel: ImageView? = null
    BindView(id = R.id.login_et_pwd)
    private val mEtPwd: EditText? = null
    BindView(id = R.id.login_btn_login, click = true)
    private val mBtnLogin: Button? = null
    BindView(id = R.id.login_img_head)
    private val mImgHead: RoundImageView? = null

    // 实在没有精力做第三方了
    BindView(id = R.id.login_img_baidu)
    private val mImgBaiDu: ImageView? = null
    BindView(id = R.id.login_img_qq)
    private val mImgQQ: ImageView? = null
    BindView(id = R.id.login_img_sina)
    private val mImgSina: ImageView? = null

    override fun setRootView() {
        setContentView(R.layout.activity_login)
    }

    override fun initWidget() {
        super.initWidget()
        initEtUser()
        screenAdapter()
        // 设置动画
        KJAnimations.openLoginAnim(mLayoutInput)
        mImgHead!!.setAnimation(KJAnimations.getRotateAnimation(360f, 0f, 500))
        mImgHead.setBorderOutsideColor(-1)
        mImgHead.setBorderThickness(2)
    }

    /**
     * 初始化用户名输入框
     */
    private fun initEtUser() {
        if (StringUtils.isEmpty(mEtUid!!.getText())) {
            mImgDel!!.setVisibility(View.GONE)
        } else {
            mImgDel!!.setVisibility(View.VISIBLE)
        }
        mEtUid.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    /**
     * 屏幕适配
     */
    private fun screenAdapter() {
        // 输入框适配
        val inputParams = mLayoutInput!!.getLayoutParams() as RelativeLayout.LayoutParams
        inputParams.width = (DensityUtils.getScreenW(aty) * 0.8).toInt()
        mLayoutInput.setLayoutParams(inputParams)
        // 高度计算
        mLayoutInput.measure(0, 0)
        val layoutH = mLayoutInput.getMeasuredHeight()
        mImgHead!!.measure(0, 0)
        val headH = getResources().getDimension(R.dimen.splash_head_height).toInt()
        // 头像适配
        val headParams = mImgHead.getLayoutParams() as RelativeLayout.LayoutParams
        headParams.topMargin = (DensityUtils.getScreenH(aty) - layoutH - headH) / 2
        mImgHead.setLayoutParams(headParams)
        // logo适配
        val logoParams = mImgLogo!!.getLayoutParams() as RelativeLayout.LayoutParams
        logoParams.width = (DensityUtils.getScreenW(aty) * 0.4).toInt()
        logoParams.height = (logoParams.width / 2.3).toInt()
        logoParams.topMargin = (DensityUtils.getScreenH(aty) - layoutH - headH - logoParams.height) / 4
        mImgLogo.setLayoutParams(logoParams)
    }

    override fun widgetClick(v: View?) {
        super.widgetClick(v)
        when (v!!.getId()) {
            R.id.login_btn_login -> doLogin()
            R.id.login_img_delete -> mEtUid!!.setText(null)
        }
    }

    /**
     * 输入合法性检测
     */
    private fun inputCheck(): Boolean {
        if (StringUtils.isEmpty(mEtUid!!.getText(), mEtPwd!!.getText())) {
            ViewInject.toast("用户名或密码为空")
            return false
        } else {
            return true
        }
    }

    private fun doLogin() {
        if (!inputCheck()) {
            return
        }
        val config = HttpConfig()
        config.cacheTime = 0
        val kjh = KJHttp(config)
        val params = HttpParams()
        params.put("username", mEtUid!!.getText().toString())
        params.put("pwd", mEtPwd!!.getText().toString())
        kjh.post("http://www.oschina.net/action/api/login_validate", params, object : HttpCallBack() {
            override fun onSuccess(headers: Map<String, String>?, t: ByteArray?) {
                val cookie = headers!!.get("Set-Cookie")
                if (t != null) {
                    val str = String(t)
                    KJLoger.debug("登陆网络请求：" + String(t))
                    val data = Parser.xmlToBean(javaClass<LoginData>(), str)
                    try {
                        if (1 == data.getResult().getErrorCode()) {
                            val user = data.getUser()
                            user.setCookie(cookie)
                            user.setAccount(mEtUid.getText().toString())
                            user.setPwd(mEtPwd.getText().toString())
                            user.setIsLogin(true)
                            AppConfig.save(user)
                            skipActivity(aty, javaClass<MainActivity>())
                        } else {
                            mEtPwd.setText(null)
                            mEtUid.setText(null)
                        }
                        ViewInject.toast(data.getResult().getErrorMessage())
                        // 太多判断了，写的蛋疼，还不如一个NullPointerException
                    } catch (e: NullPointerException) {
                        ViewInject.toast("登陆失败")
                        mEtPwd.setText(null)
                        mEtUid.setText(null)
                    }
                }
            }
        })
    }
}
