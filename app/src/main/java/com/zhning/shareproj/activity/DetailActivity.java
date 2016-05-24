package com.zhning.shareproj.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.adapter.CommentListAdapter;
import com.zhning.shareproj.adapter.FollowListAdapter;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.entity.PostFollow;
import com.zhning.shareproj.entity.User;
import com.zhning.shareproj.utils.ModelUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.iv_detail_portrait)
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

    FollowListAdapter followListAdapter;
    CommentListAdapter commentListAdapter;

    long postId;
    Post post;
    List<PostFollow> postFollowList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        postId = getIntent().getLongExtra("postId", 0);
        getPostFromServer();
    }

    private void getPostFromServer() {

    }

    private void initView() {
        User user = ModelUtil.getUser(post.getUserId());
        ivDetailHead.setImageResource(user.getPortrait());
        tvDetailName.setText(user.getName());
        tvDetailTitle.setText(post.getTitle());
        tvDetailContext.setText(post.getContent());
        //ivDetailPic.setImageResource(post.getPic());
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
