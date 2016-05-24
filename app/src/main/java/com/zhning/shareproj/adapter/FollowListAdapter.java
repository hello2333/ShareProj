package com.zhning.shareproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.PostFollow;
import com.zhning.shareproj.entity.User;
import com.zhning.shareproj.listener.OnFollowItemClickListener;
import com.zhning.shareproj.utils.ModelUtil;
import com.zhning.shareproj.utils.ToastUtil;

import java.util.List;

/**
 * Created by baidu on 16/5/5.
 */
public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.PoolViewHolder>{
    List<PostFollow> data;
    private LayoutInflater inflater;
    private Context mContext;
    private OnFollowItemClickListener onFollowItemClickListener;

    public FollowListAdapter(Context context, List<PostFollow> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(List<PostFollow> data) {
        this.data = data;
    }

    public void setOnCenterItemClickListener(OnFollowItemClickListener onFollowItemClickListener){
        this.onFollowItemClickListener = onFollowItemClickListener;
    }
    @Override
    public PoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_center, parent, false);
        PoolViewHolder holder = new PoolViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFollowItemClickListener.onFollowItemClick((long) view.getTag());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PoolViewHolder holder, int position) {
        PostFollow postFollow = data.get(position);
        User user = ModelUtil.getUser(postFollow.getUserId());
        holder.itemView.setTag(postFollow.getId());
        holder.tvUserName.setText(user.getName());
        holder.ivUserImage.setImageResource(user.getPortrait());
        holder.tvFollowContent.setText(postFollow.getContent());
        holder.tvFollowFloor.setText(postFollow.getFloor() + "");
        holder.ivDetailFollowComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(mContext, "添加您的评论");
            }
        });

        // holder.ivCenterPic.setImageResource();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PoolViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserImage;
        TextView tvUserName;
        TextView tvFollowContent;
        TextView tvFollowFloor;
        ImageView ivDetailFollowComm;
        ListView lvDetailComment;
        public PoolViewHolder(View itemView) {
            super(itemView);
            ivUserImage = (ImageView) itemView.findViewById(R.id.iv_detail_follow_portrait);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_detail_follow_name);
            tvFollowContent = (TextView) itemView.findViewById(R.id.tv_detail_follow_context);
            tvFollowFloor = (TextView) itemView.findViewById(R.id.tv_detail_follow_floor);
            ivDetailFollowComm = (ImageView) itemView.findViewById(R.id.iv_detail_follow_comm);
            lvDetailComment = (ListView) itemView.findViewById(R.id.lv_detail_follow_comment);
        }
    }
}

