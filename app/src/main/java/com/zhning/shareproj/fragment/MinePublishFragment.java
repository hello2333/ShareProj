package com.zhning.shareproj.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhning.shareproj.R;
import com.zhning.shareproj.activity.DetailActivity;
import com.zhning.shareproj.adapter.PoolListAdapter;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.listener.OnCenterItemClickListener;
import com.zhning.shareproj.utils.ModelUtil;
import com.zhning.shareproj.utils.MyApp;

import java.util.ArrayList;
import java.util.List;


public class MinePublishFragment extends Fragment {
    private static final String TAG = "MinePublishFragment";
    RecyclerView rvMinePublish;
    PoolListAdapter adapter;
    List<Post> publishList = new ArrayList<>();
    SwipeRefreshLayout srlMinePublish;

    public MinePublishFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine_publish, container, false);

        publishList = ModelUtil.getCollectPostByUserId(((MyApp)getActivity().getApplication()).getCurrentUser());
        rvMinePublish = (RecyclerView) rootView.findViewById(R.id.rv_mine_publish);
        adapter = new PoolListAdapter(getActivity(), publishList);
        adapter.setOnCenterItemClickListener(new OnCenterItemClickListener() {
            @Override
            public void onCenterItemClick(long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Log.i(TAG, "go to postId:" + id);
                intent.putExtra("postId", id);
                startActivity(intent);
            }
        });

        rvMinePublish.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMinePublish.setAdapter(adapter);

        getMyCollectFromServer();

        srlMinePublish = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_mine_publish);
        srlMinePublish.setProgressBackgroundColorSchemeColor(R.color.white);
        srlMinePublish.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_green_light);
        srlMinePublish.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlMinePublish.setRefreshing(true);
                getMyCollectFromServer();

            }
        });

        return rootView;
    }

    private void getMyCollectFromServer() {
        publishList = ModelUtil.getCollectPostByUserId(((MyApp)getActivity().getApplication()).getCurrentUser());
        adapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
