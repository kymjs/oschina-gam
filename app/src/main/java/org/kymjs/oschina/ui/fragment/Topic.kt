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
 * 话题圈

 * @author kymjs (http://www.kymjs.com/) on 8/13/15.
 */
public class Topic : BaseMainFragment() {

    BindView(id = R.id.myworks_group)
    private val mGroup: PagerSlidingTabStrip? = null
    BindView(id = R.id.myworks_pager)
    private val mPager: ViewPager? = null

    private var adapter: ViewPageFragmentAdapter? = null

    override fun inflaterView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle?): View? {
        val rootView: View = layoutInflater.inflate(R.layout.frag_main_topic, null)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        setTitle("话题圈子")
    }

    override fun initWidget(parentView: View?) {
        super.initWidget(parentView)
        mPager?.setOffscreenPageLimit(2)
        adapter = ViewPageFragmentAdapter(getChildFragmentManager(), mGroup, mPager)
        mPager?.setAdapter(adapter)
        mGroup?.setViewPager(mPager)

        val bundle1 = Bundle()
        bundle1.putInt("tag", 1)
        adapter?.addTab("今日话题", "tag1", javaClass<TopicList>(), bundle1)

        val bundle2 = Bundle()
        bundle2.putInt("tag", 2)
        adapter?.addTab("心情广场", "tag2", javaClass<MoodPlaza>(), bundle2)
    }
}
