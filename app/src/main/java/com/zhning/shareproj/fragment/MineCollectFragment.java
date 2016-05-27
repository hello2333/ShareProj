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

public class MineCollectFragment extends Fragment {
    private static final String TAG = "MineCollectFragment";
    RecyclerView rvMineCollect;
    PoolListAdapter adapter;
    List<Post> collectList = new ArrayList<>();
    SwipeRefreshLayout srlMineCollect;

    public MineCollectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine_collect, container, false);

        collectList = ModelUtil.getCollectPostByUserId(((MyApp)getActivity().getApplication()).getCurrentUser());
        rvMineCollect = (RecyclerView) rootView.findViewById(R.id.rv_mine_collect);
        adapter = new PoolListAdapter(getActivity(), collectList);
        adapter.setOnCenterItemClickListener(new OnCenterItemClickListener() {
            @Override
            public void onCenterItemClick(long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Log.i(TAG, "go to postId:" + id);
                intent.putExtra("postId", id);
                startActivity(intent);
            }
        });

        rvMineCollect.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMineCollect.setAdapter(adapter);

        getMyCollectFromServer();

        srlMineCollect = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_mine_collect);
        srlMineCollect.setProgressBackgroundColorSchemeColor(R.color.white);
        srlMineCollect.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_green_light);
        srlMineCollect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlMineCollect.setRefreshing(true);
                getMyCollectFromServer();

            }
        });

        return rootView;
    }

    private void getMyCollectFromServer() {
        collectList = ModelUtil.getCollectPostByUserId(((MyApp)getActivity().getApplication()).getCurrentUser());
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
