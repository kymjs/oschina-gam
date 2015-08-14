package org.kymjs.oschina.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kymjs.kjframe.ui.BindView
import org.kymjs.oschina.R
import org.kymjs.oschina.adapter.FriendGroupAdapter
import org.kymjs.oschina.ui.widget.CircleRefreshLayout

/**
 * 好友动态列表界面

 * @author kymjs (http://www.kymjs.com/) on 8/12/15.
 */
public class FriendGroup : BaseMainFragment() {

    @BindView(id = R.id.recyclerView)
    private val recyclerView: RecyclerView? = null

    @BindView(id = R.id.swiperefreshlayout)
    private val refreshLayout: CircleRefreshLayout? = null

    private var datas: MutableList<String>? = null

    override fun inflaterView(layoutInflater: LayoutInflater, viewGroup: ViewGroup, bundle: Bundle?): View? {
        val rootView: View = layoutInflater.inflate(R.layout.frag_main_friendgroup, null)
        return rootView
    }

    override fun initWidget(parentView: View?) {
        super.initWidget(parentView)
        refreshLayout?.setOnRefreshListener(object : CircleRefreshLayout.OnCircleRefreshListener {
            override fun refreshing() {
                refreshLayout.finishRefreshing();
            }

            override fun completeRefresh() {
            }
        })
        recyclerView?.setLayoutManager(LinearLayoutManager(outsideAty))
        recyclerView?.setItemAnimator(DefaultItemAnimator())

        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration();
        recyclerView?.addItemDecoration(itemDecoration);

        val myAdapter = FriendGroupAdapter()
        recyclerView?.setAdapter(myAdapter)
    }

    override fun onResume() {
        super.onResume()
        setTitle("好友动态")
    }

    /**
     * set RecyclerView divider height 20px
     */
    inner class DividerItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, 20);//每个item的底部偏移
        }
    }
}
