package com.zhning.shareproj.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zhning.shareproj.R;
import com.zhning.shareproj.adapter.MinePagerAdapter;
import com.zhning.shareproj.fragment.MineCollectFragment;
import com.zhning.shareproj.fragment.MinePublishFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineActivity extends AppCompatActivity {
    @Bind(R.id.tl_mine)
    TabLayout tlMine;
    @Bind(R.id.vp_mine)
    ViewPager vpMine;

    List<String> titles;
    List<Fragment> fragmentList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        Fragment publish = new MinePublishFragment();
        Fragment collect = new MineCollectFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(publish);
        fragmentList.add(collect);

        titles = new ArrayList<>();
        titles.add("我发布的");
        titles.add("我的收藏");
    }

    private void initView() {
        MinePagerAdapter pagerAdapter = new MinePagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        vpMine.setAdapter(pagerAdapter);
        tlMine.setupWithViewPager(vpMine);
        tlMine.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initListener() {

    }

}
