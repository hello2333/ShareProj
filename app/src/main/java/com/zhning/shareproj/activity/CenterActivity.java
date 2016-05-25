package com.zhning.shareproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.adapter.PoolListAdapter;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.listener.OnCenterItemClickListener;
import com.zhning.shareproj.utils.ModelUtil;
import com.zhning.shareproj.utils.MyApp;
import com.zhning.shareproj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CenterActivity extends AppCompatActivity {
    private static final String TAG = "CenterActivity";

    @Bind(R.id.srl_center)
    SwipeRefreshLayout srlCenter;
    @Bind(R.id.rv_center_list)
    RecyclerView rvCenterList;
    @Bind(R.id.iv_recruit_add)
    ImageView ivPostAdd;

    PoolListAdapter poolListAdapter;

    private Context mContext;
    private Activity mActivity;

    private List<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();

    }

    private void initData() {
        mContext = this;
        mActivity = this;
    }

    private void initView() {
        srlCenter.setProgressBackgroundColorSchemeColor(R.color.white);
        srlCenter.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_green_light);
        srlCenter.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlCenter.setRefreshing(true);
                getDateFromServer();
                srlCenter.setRefreshing(false);
            }
        });

        postList = ModelUtil.getAllPost();
        poolListAdapter = new PoolListAdapter(this, postList);

        poolListAdapter.setOnCenterItemClickListener(new OnCenterItemClickListener() {
            @Override
            public void onCenterItemClick(long id) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                ToastUtil.show(mContext, "postId:" + id);
                intent.putExtra("postId", id);
                startActivity(intent);
            }
        });
        rvCenterList.setLayoutManager(new LinearLayoutManager(this));
        rvCenterList.setAdapter(poolListAdapter);

        getDateFromServer();
    }

    private void initListener() {
        ivPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getDateFromServer(){
        postList = ModelUtil.getAllPost();
        poolListAdapter.notifyDataSetChanged();
        Log.i(TAG, "postListSize:" + postList.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_center, menu);
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
