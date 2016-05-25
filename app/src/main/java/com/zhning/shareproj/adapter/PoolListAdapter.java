package com.zhning.shareproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.entity.User;
import com.zhning.shareproj.listener.OnCenterItemClickListener;
import com.zhning.shareproj.utils.ModelUtil;

import java.util.List;

/**
 * Created by baidu on 16/5/5.
 */
public class PoolListAdapter extends RecyclerView.Adapter<PoolListAdapter.PoolViewHolder>{
    List<Post> data;
    private LayoutInflater inflater;
    private OnCenterItemClickListener onCenterItemClickListener;

    public PoolListAdapter(Context context, List<Post> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Post> data) {
        this.data = data;
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener onCenterItemClickListener){
        this.onCenterItemClickListener = onCenterItemClickListener;
    }
    @Override
    public PoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_center, parent, false);
        PoolViewHolder holder = new PoolViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCenterItemClickListener.onCenterItemClick((long) view.getTag());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PoolViewHolder holder, int position) {
        Post post = data.get(position);
        User user = ModelUtil.getUser(post.getUserId());
        holder.itemView.setTag(post.getId());
        holder.tvUserName.setText(user.getName());
        holder.ivUserImage.setImageResource(user.getPortrait());
        holder.tvCenterTitle.setText(post.getTitle());
        holder.tvCenterContent.setText(post.getContent());
        holder.ivCenterPic.setImageResource(post.getPic());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PoolViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserImage;
        TextView tvUserName;
        TextView tvCenterTitle;
        TextView tvCenterContent;
        ImageView ivCenterPic;

        public PoolViewHolder(View itemView) {
            super(itemView);
            ivUserImage = (ImageView) itemView.findViewById(R.id.iv_center_portrait);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_center_name);
            tvCenterTitle = (TextView) itemView.findViewById(R.id.tv_center_title);
            tvCenterContent = (TextView) itemView.findViewById(R.id.tv_center_context);
            ivCenterPic = (ImageView) itemView.findViewById(R.id.iv_center_pic);
        }
    }
}

