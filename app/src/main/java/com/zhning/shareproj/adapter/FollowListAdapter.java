package com.zhning.shareproj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.entity.PostComment;
import com.zhning.shareproj.entity.PostFollow;
import com.zhning.shareproj.entity.User;
import com.zhning.shareproj.listener.OnFollowItemClickListener;
import com.zhning.shareproj.utils.ModelUtil;
import com.zhning.shareproj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baidu on 16/5/5.
 */
public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.FollowViewHolder>{
    private static final String TAG = "FollowListAdapter";
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_NORMAL = 2;

    Post post;
    List<PostFollow> data;

    int createCount = 0;
    int bindCount = 0;

    private LayoutInflater inflater;
    private Context mContext;
    private OnFollowItemClickListener onFollowItemClickListener;

    public FollowListAdapter(Context context, List<PostFollow> data, Post post){
        this.data = data;
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.post = post;
    }


    public void setData(List<PostFollow> data) {
        this.data = data;
    }

    public void setOnCenterItemClickListener(OnFollowItemClickListener onFollowItemClickListener){
        this.onFollowItemClickListener = onFollowItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) return TYPE_HEADER;
        else return TYPE_NORMAL;
    }

    @Override
    public FollowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FollowViewHolder holder;
        Log.i("FollowListAdapter", "createView " + createCount ++);

        if (viewType == TYPE_HEADER){
            View view = inflater.inflate(R.layout.item_detail_follow_header, parent, false);
            holder = new FollowViewHolder(view, TYPE_HEADER);
            return holder;
        } else if (viewType == TYPE_NORMAL){
            final View view = inflater.inflate(R.layout.item_detail_follow, parent, false);
            holder = new FollowViewHolder(view, TYPE_NORMAL);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFollowItemClickListener.onFollowItemClick((long) view.getTag());
                }
            });
            return  holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(FollowViewHolder holder, int position) {
        Log.i("FollowListAdapter", "bindView " + bindCount ++ + "for position " + position);
        if (getItemViewType(position) == TYPE_HEADER){
            User user = ModelUtil.getUser(post.getUserId());
            holder.ivUserImage.setImageResource(user.getPortrait());
            holder.tvUserName.setText(user.getName());
            holder.tvDetailTitle.setText(post.getTitle());
            holder.tvDetailContent.setText(post.getContent());
            holder.ivDetailPic.setImageResource(post.getPic());
        } else {
            PostFollow postFollow = data.get(position - 1);
            User user = ModelUtil.getUser(postFollow.getUserId());
            holder.itemView.setTag(postFollow.getId());
            holder.tvUserName.setText(user.getName());
            holder.ivUserImage.setImageResource(user.getPortrait());
            holder.tvFollowContent.setText(postFollow.getContent());
            holder.tvFollowFloor.setText(String.format("第%d楼", postFollow.getFloor()));
            holder.ivDetailFollowComm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(mContext, "添加您的评论");
                }
            });

            int commCount = 0;
            List<PostComment> postCommentList = postFollow.getPostCommentList();
            if (postCommentList.isEmpty()){
                holder.llItemFollowComm.setVisibility(View.GONE);
                Log.i(TAG, position + "gone");
            } else {
                Log.i(TAG, position + "show");
                holder.llItemFollowComm.setVisibility(View.VISIBLE);
                for (PostComment postComment : postCommentList){
                    User commUser = ModelUtil.getUser(postComment.getUserId());
                    String content = postComment.getContent();
                    if (0 != postComment.getToUserId()){
                        User toUser = ModelUtil.getUser(postComment.getToUserId());
                        content = "回复 " + toUser.getName() + "：" + content;
                    }
                    content = commUser.getName() + "：" + content;
                    SpannableString spannableString = new SpannableString(content);
                    spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.item_comm_username)),
                            0, user.getName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holder.tvItemFollowComms.get(commCount ++).setText(spannableString);
                }

                for (int i = commCount; i < holder.tvItemFollowComms.size(); i ++){
                    holder.tvItemFollowComms.get(i).setVisibility(View.GONE);
                }

            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }



    class FollowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserImage;
        TextView tvUserName;
        TextView tvDetailTitle;
        TextView tvDetailContent;
        ImageView ivDetailPic;
        TextView tvFollowContent;
        TextView tvFollowFloor;
        ImageView ivDetailFollowComm;

        LinearLayout llItemFollowComm;
        List<TextView> tvItemFollowComms = new ArrayList<>();

        int viewType;

        public FollowViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == TYPE_HEADER){
                ivUserImage = (ImageView) itemView.findViewById(R.id.iv_detail_head);
                tvUserName = (TextView) itemView.findViewById(R.id.tv_detail_name);
                tvDetailTitle = (TextView) itemView.findViewById(R.id.tv_detail_title);
                tvDetailContent = (TextView) itemView.findViewById(R.id.tv_detail_context);
                ivDetailPic = (ImageView) itemView.findViewById(R.id.iv_detail_pic);
                tvFollowFloor = (TextView) itemView.findViewById(R.id.tv_detail_floor);
            }

            if (viewType == TYPE_NORMAL){
                ivUserImage = (ImageView) itemView.findViewById(R.id.iv_detail_follow_portrait);
                tvUserName = (TextView) itemView.findViewById(R.id.tv_detail_follow_name);
                tvFollowContent = (TextView) itemView.findViewById(R.id.tv_detail_follow_context);
                tvFollowFloor = (TextView) itemView.findViewById(R.id.tv_detail_follow_floor);
                ivDetailFollowComm = (ImageView) itemView.findViewById(R.id.iv_detail_follow_comm);

                llItemFollowComm = (LinearLayout) itemView.findViewById(R.id.ll_item_detail_comm);
                tvItemFollowComms.add((TextView) itemView.findViewById(R.id.tv_item_follow_comm1));
                tvItemFollowComms.add((TextView) itemView.findViewById(R.id.tv_item_follow_comm2));
                tvItemFollowComms.add((TextView) itemView.findViewById(R.id.tv_item_follow_comm3));
            }

        }
    }
}

