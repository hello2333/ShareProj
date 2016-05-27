package com.zhning.shareproj.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.utils.MyApp;
import com.zhning.shareproj.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CreateActivity extends AppCompatActivity {
    @Bind(R.id.tv_create_title)
    TextView tvCreateTitle;
    @Bind(R.id.tv_create_content)
    TextView tvCreateContent;
    @Bind(R.id.iv_create_pic)
    ImageView ivCreatePic;
    @Bind(R.id.tv_create_finish)
    TextView tvCreateFinish;

    List<Long> images;
    List<String> paths;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    private void initData(){
        mContext = this;
    }

    private void initListener() {
        ivCreatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhoneAlbumBaseActivity.class);
                startActivity(intent);
            }
        });

        tvCreateFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = tvCreateTitle.getText().toString().trim();
                if (title.length() == 0){
                    ToastUtil.show(mContext, "请输入标题");
                    return;
                }

                String content = tvCreateContent.getText().toString().trim();
                if (content.length() == 0){
                    ToastUtil.show(mContext, "请输入内容");
                    return;
                }

                Post post = new Post();
                post.setTitle(title);
                post.setContent(content);
                post.setUserId(((MyApp)getApplication()).getCurrentUser());

                if (!paths.isEmpty()){
                    String picUrl = sendPictoServer();
                    post.setPicUrl(picUrl);
                }

                sendPostToServer();
            }
        });
    }

    private void sendPostToServer() {
        finish();
    }

    private String sendPictoServer() {
        String picUrl = "picUrl";
        return picUrl;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        images = (List<Long>) intent.getSerializableExtra("images");
        paths = (List<String>) intent.getSerializableExtra("paths");

        if (images != null){
            ivCreatePic.setImageBitmap(MediaStore.Images.Thumbnails.getThumbnail(
                    this.getContentResolver(), images.get(0),
                    MediaStore.Images.Thumbnails.MICRO_KIND, null));
        }
    }
}
