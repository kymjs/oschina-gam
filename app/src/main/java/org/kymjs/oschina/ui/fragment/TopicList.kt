package org.kymjs.oschina.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kymjs.oschina.R

/**
 * 今日话题，(话题列表)
 * @author kymjs (http://www.kymjs.com/) on 8/13/15.
 */
public class TopicList : BaseMainFragment() {
    override fun inflaterView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle?): View? {
        val rootView: View = layoutInflater.inflate(R.layout.frag_main_topiclist, null)
        return rootView
    }

    override fun initWidget(parentView: View?) {
        super.initWidget(parentView)
    }
}
