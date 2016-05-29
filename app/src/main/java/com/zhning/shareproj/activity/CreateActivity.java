package com.zhning.shareproj.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.utils.Constants;
import com.zhning.shareproj.utils.MyApp;
import com.zhning.shareproj.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class CreateActivity extends AppCompatActivity {
    private final static String TAG = "CreateActivity";
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
    String picUrl;

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
        File imageFile = new File(paths.get(0));
        RequestParams params = new RequestParams();
        try {
            params.put("fileUp", imageFile);
            AsyncHttpClient client = new AsyncHttpClient();
            String url = Constants.ipv4 + "pic";
            final ProgressDialog uploadDialog = new ProgressDialog(mContext);
            uploadDialog.setMessage("帖子上传中……");
            uploadDialog.show();

            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "success: " + new String(responseBody));
                    picUrl = new String(responseBody);

                    final Post post = new Post();
                    post.setUserId(((MyApp)getApplication()).getCurrentUser());
                    post.setTitle(tvCreateTitle.getText().toString());
                    post.setContent(tvCreateContent.getText().toString());
                    picUrl = Constants.ipv4 + "images/" + picUrl;
                    post.setPicUrl(picUrl);

                    String createUrl = Constants.ipv4 + "posts";
                    StringRequest uploadReq = new StringRequest(Request.Method.POST,
                            createUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ToastUtil.show(mContext, "帖子上传成功");
                            uploadDialog.hide();
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ToastUtil.show(mContext, "网络异常");
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("post", new Gson().toJson(post));
                            return params;
                        }

                    };
                    ((MyApp)getApplication()).addToRequestQueue(uploadReq);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "图片上传失败：" + new String(responseBody));
                    error.printStackTrace();
                    ToastUtil.show(mContext, "网络异常");
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
            Log.i(TAG, "imagesId: " + images.get(0) + " path: " + paths.get(0));
        }
    }
}
