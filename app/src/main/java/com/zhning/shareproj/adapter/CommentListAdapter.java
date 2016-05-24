package com.zhning.shareproj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhning.myshareapp.R;
import com.zhning.myshareapp.entity.PostComment;
import com.zhning.myshareapp.entity.User;
import com.zhning.myshareapp.utils.ModelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhning on 2016/5/24.
 */
public class CommentListAdapter extends BaseAdapter {
    List<PostComment> data = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public CommentListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_comment, null);
        TextView comment = (TextView) view.findViewById(R.id.tv_comment);
        PostComment postComment = data.get(position);
        User user = ModelUtil.getUser(postComment.getUserId());
        String content = postComment.getContent();
        if (0 != postComment.getToUserId()){
            User toUser = ModelUtil.getUser(postComment.getToUserId());
            content = "回复 " + toUser.getName() + "：" + content;
        }
        content = user.getName() + "：" + content;
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),
                0, user.getName().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        comment.setText(spannableString);
        return view;
    }
}
