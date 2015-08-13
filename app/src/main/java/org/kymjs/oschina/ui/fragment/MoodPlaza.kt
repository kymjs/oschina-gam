package org.kymjs.oschina.ui.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kymjs.kjframe.ui.BindView
import org.kymjs.oschina.R
import org.kymjs.oschina.adapter.ViewPageFragmentAdapter
import org.kymjs.oschina.ui.widget.PagerSlidingTabStrip

/**
 * 心情广场

 * @author kymjs (http://www.kymjs.com/) on 8/13/15.
 */
public class MoodPlaza : BaseMainFragment() {
    override fun inflaterView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle?): View? {
        val rootView: View = layoutInflater.inflate(R.layout.frag_main_friendgroup, null)
        return rootView
    }

    override fun initWidget(parentView: View?) {
        super.initWidget(parentView)
    }
}
