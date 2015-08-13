package org.kymjs.oschina.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kymjs.oschina.R

/**
 * 好友动态列表界面

 * @author kymjs (http://www.kymjs.com/) on 8/12/15.
 */
public class FriendGroup : BaseMainFragment() {

    override fun inflaterView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle?): View? {
        val rootView: View = layoutInflater.inflate(R.layout.frag_main_friendgroup, null)
        return rootView
    }

    override fun initWidget(parentView: View?) {
        super.initWidget(parentView)
    }
}
