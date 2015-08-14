package org.kymjs.oschina.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.oschina.AppConfig;
import org.kymjs.oschina.R;

/**
 * @author kymjs (http://www.kymjs.com/) on 8/14/15.
 */
public class FriendGroupAdapter extends RecyclerView.Adapter<FriendGroupAdapter.VH> {

    private KJBitmap kjb = new KJBitmap();

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_friendgroup, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tv_content.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        holder.tv_name.setText(AppConfig.INSTANCE$.getUser().getName());
        holder.tv_time.setText("09:18");
        kjb.display(holder.img_head, AppConfig.INSTANCE$.getUser().getPortrait());
        kjb.display(holder.image, "http://kymjs.com/image/logo.png");
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public static class VH extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_content;
        public ImageView img_head;
        public ImageView image;

        public VH(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.item_friendgroup_tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.item_friendgroup_tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.item_friendgroup_tv_content);
            img_head = (ImageView) itemView.findViewById(R.id.item_friendgroup_img_head);
            image = (ImageView) itemView.findViewById(R.id.item_friendgroup_image);
        }
    }
}
