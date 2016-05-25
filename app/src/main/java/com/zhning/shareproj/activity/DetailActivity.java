package com.zhning.shareproj.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.adapter.FollowListAdapter;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.entity.PostComment;
import com.zhning.shareproj.entity.PostFollow;
import com.zhning.shareproj.entity.User;
import com.zhning.shareproj.listener.OnFollowItemClickListener;
import com.zhning.shareproj.utils.ModelUtil;
import com.zhning.shareproj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    @Bind(R.id.iv_detail_head)
    ImageView ivDetailHead;
    @Bind(R.id.tv_detail_name)
    TextView tvDetailName;
    @Bind(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @Bind(R.id.tv_detail_context)
    TextView tvDetailContext;
    @Bind(R.id.iv_detail_pic)
    ImageView ivDetailPic;
    @Bind(R.id.rv_detail_follow)
    RecyclerView rvDetailFollow;
    @Bind(R.id.ll_detail_first)
    LinearLayout llDetailFirst;

    FollowListAdapter followListAdapter;

    long postId;
    Post post;
    List<PostFollow> postFollowList = new ArrayList<>();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        mContext = this;

        postId = getIntent().getLongExtra("postId", 1);
        getPostFromServer();

    }

    private void initView() {
        User user = ModelUtil.getUser(post.getUserId());

        ivDetailHead.setImageResource(
                user.getPortrait());
        tvDetailName.setText(user.getName());
        tvDetailTitle.setText(post.getTitle());
        tvDetailContext.setText(post.getContent());
        ivDetailPic.setImageResource(post.getPic());

        // notifyDataFailed
        postFollowList = ModelUtil.getPostFollowByPostId(postId);
        if (null == postFollowList)
            postFollowList = new ArrayList<>();
        for (PostFollow follow : postFollowList){
            List<PostComment> postCommentList = ModelUtil.getCommentByFollowId(follow.getId());
            if (null == postCommentList)
                postCommentList = new ArrayList<>();
            Log.i(TAG, "follow " + follow.getId() + "'s comment size:" + postCommentList.size());
            follow.setPostCommentList(postCommentList);
        }

        followListAdapter = new FollowListAdapter(mContext, postFollowList);
        followListAdapter.setOnCenterItemClickListener(new OnFollowItemClickListener() {
            @Override
            public void onFollowItemClick(long id) {
                ToastUtil.show(mContext, "postFollow " + id + " clicked");
            }
        });
        rvDetailFollow.setLayoutManager(new LinearLayoutManager(mContext));
        rvDetailFollow.addView(llDetailFirst);
        rvDetailFollow.setAdapter(followListAdapter);
        getPostFollowFromServer();
    }

    private void getPostFromServer() {
        post = ModelUtil.getPostDetail(postId);
    }

    private void getPostFollowFromServer(){
        postFollowList = ModelUtil.getPostFollowByPostId(postId);
        if (null == postFollowList)
            postFollowList = new ArrayList<>();
        followListAdapter.notifyDataSetChanged();
        getCommentsForFollows();
    }

    private void getCommentsForFollows() {
        for (PostFollow follow : postFollowList){
            List<PostComment> postCommentList = ModelUtil.getCommentByFollowId(follow.getId());
            if (null == postCommentList)
                postCommentList = new ArrayList<>();
            follow.setPostCommentList(postCommentList);
        }
        followListAdapter.notifyDataSetChanged();
    }

    private void initListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
