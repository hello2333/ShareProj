package com.zhning.shareproj.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.ImageBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PhoneItemBaseActivity extends AppCompatActivity {
	@Bind(R.id.tv_photo_item_finish)
    TextView tvPhotoItemFinish;

	final String TAG = "ItemBaseActivity";
	GridView grid;
	//选中的相片的数目相关的变量
	List<Long> imagesSelected;
	List<String> imagesPath;
	int count;//统计有多少张照片被选中
	//MenuItem number;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		count = 0;
		//设置标题栏
		setContentView(R.layout.activity_phone_item_base);
        ButterKnife.bind(this);

		//获得各个组件
		grid = (GridView) findViewById(R.id.grid2);

		if(grid == null){
			Log.v(TAG, "grid is null");
		}else Log.v(TAG, "grid not null");
		//获得传递过来的参数，得到对应ID号的相册的图片列表
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		List<ImageBean> itemlist = (ArrayList<ImageBean>) bundle.getSerializable("images");
		if(itemlist == null){
			Log.v(TAG, "itemList is null");
		}else Log.v(TAG, "itemList not null");
		//为GridView绑定Adapter组件
		BaseAdapter adapter = new GridAdapter(this, itemlist, grid);
		grid.setAdapter(adapter);

		//为标题栏的按钮绑定监听器
		tvPhotoItemFinish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneItemBaseActivity.this, CreateActivity.class);
                intent.putExtra("images", (Serializable)imagesSelected);
                intent.putExtra("paths", (Serializable)imagesPath);
                startActivity(intent);
            }
        });
	}

	class GridAdapter extends BaseAdapter{
		final String TAG2 = "CACHE";
		//组件相关的变量
		GridView gridView;
		Context context;
		ImageView imageView,choice;
		LayoutInflater mInflater;
		//GridView中每个项相关的变量
		List<ImageBean> itemList;
		int width,height;//每个图片占据的宽和高
		//和缓存相关的变量
		LruCache<Long, Bitmap> mLruCache;
		boolean isFirstEnterThisActivity = true;
		int mFirstVisibleItem;//当前屏幕第一个组件的position
		int mVisibleItemCount;//当前屏幕一共显示了多少个组件
		
		public GridAdapter(Context context,
				List<ImageBean> itemList, GridView gridView){
			this.itemList = itemList;
			this.context = context;
			this.gridView = gridView;
			mInflater = LayoutInflater.from(context);
			imagesSelected = new LinkedList<Long>();
			imagesPath = new LinkedList<String>();
			//获取屏幕大小确定每行显示多少张图片
			DisplayMetrics  dm = new DisplayMetrics();    
		    getWindowManager().getDefaultDisplay().getMetrics(dm);    
		    int screenWidth = dm.widthPixels;              
		    int screenHeight = dm.heightPixels;
		    width = screenWidth / 4 - screenWidth / 4 / 8;		//图片大小的1/8作为图片之间的间隔
		    height = screenHeight / 6  - screenHeight / 6 / 8 ;
		    //获取缓存空间的大小
		    int maxMemory = (int) Runtime.getRuntime().maxMemory();
		    	//设置图片缓存大小为maxMemory的1/3
		    int cacheSize = maxMemory / 3;
		    Log.v(TAG2, "cacheSize is:" + cacheSize);
		    mLruCache = new LruCache<Long,Bitmap>(cacheSize){
				@Override//返回每张图片的大小
	            protected int sizeOf(Long key, Bitmap bitmap) {
	                //return bitmap.getRowBytes() * bitmap.getHeight();
		    		//return width * height;
		    		return bitmap.getByteCount();
	            }

		    };
		    //为grid绑定监听器，设置第一次未滚动时图片的加载
		    grid.setOnScrollListener(new GridScrollListener());
		}
		
		@Override
		public int getCount() {
			//返回当前GridView中共有多少张图片。这个是必须这样的
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			//返回对应position的item的Map
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			//返回在位置标号，并不是Id号
			return position;
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//获得各个组件
			convertView = mInflater.inflate(R.layout.grid_cell, null);
			imageView = (ImageView) convertView.findViewById(R.id.imageInGrid);
			choice = (ImageView) convertView.findViewById(R.id.choiceInGrid);
			//设置每个ImageView的图片大小
			LayoutParams params = imageView.getLayoutParams();  
		    params.height = height;  
		    params.width = width;  
		    imageView.setLayoutParams(params);
		    
		    //获取图片Id，从缓冲区中加载图片，并为这张图片相关的ImageView组件设置标签，以便后期得到正确的关联组件
		    long id = itemList.get(position).getImageid();
		    String path = itemList.get(position).getData();
		    imageView.setTag(id + "image");
		    choice.setTag(id + "choice");
		    setImageFromCache(id, imageView);
		    //绑定监听器响应相应的点击操作
			imageView.setOnClickListener(new PhotoListener(id,path,position));
			return convertView;
		}
		
		class PhotoListener implements OnClickListener{
			long viewTag;
			int position;
			String path;
			ImageView select;
			final String TAG3 = "ImageCount";
			public PhotoListener(long id, String path,int position){	
				this.viewTag = id;
				this.position = position;
				this.path = path;
			}
			@Override
			public void onClick(View v) {
				boolean flag = itemList.get(position).isSelected();

				select = (ImageView) gridView.findViewWithTag(viewTag + "choice");
				//根据flag的值确定相片的选中状态
				
					if(!flag){
						if(count < 1){
						count ++;
						imagesSelected.add(viewTag);
						imagesPath.add(path);
						itemList.get(position).select();
						//number.setTitle(String.valueOf(count));
						Log.v(TAG3, "count of images selected:" + imagesSelected.size() + " form add " + count);
						select.setVisibility(View.VISIBLE);
						}
						else{
							Toast.makeText(PhoneItemBaseActivity.this, "最多只能选择1张图片!", Toast.LENGTH_SHORT).show();
						}
					}
					else{
						count --;
						imagesSelected.remove(viewTag);
						imagesPath.remove(path);
						itemList.get(position).deselect();
						//number.setTitle(String.valueOf(count));
						Log.v(TAG3, "count of images selected:" + imagesSelected.size() + " form remove " + count);
						select.setVisibility(View.INVISIBLE);
					}
			}
		}
		
		class GridScrollListener implements OnScrollListener{

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				mFirstVisibleItem = firstVisibleItem;
				mVisibleItemCount = visibleItemCount;
				if(isFirstEnterThisActivity && visibleItemCount > 0){
					loadBitmaps(firstVisibleItem,visibleItemCount);
					isFirstEnterThisActivity = false;
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == SCROLL_STATE_IDLE) 
	                loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
			}
			
		}
		
		//为对应ImageView设置图片
		public void setImageFromCache(Long key,ImageView image){
			Bitmap bitmap = getBitmapFromLruCache(key);
			if (bitmap != null) 
	            imageView.setImageBitmap(bitmap);
	         else 
	        	imageView.setImageResource(R.drawable.logo2);
	        
		}
		//将图片添加到缓存中
		public void addBitmapToLruCache(Long key,Bitmap bitmap){
			if (getBitmapFromLruCache(key) == null) {
	            mLruCache.put(key, bitmap);
	        }
		}
		//从缓存中获得图片
		private Bitmap getBitmapFromLruCache(Long id) {
			return mLruCache.get(id);
		}
		//为新的一屏幕加载图片
		public void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
			try{
				for(int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i ++){
					long id = itemList.get(i).getImageid();
					Bitmap bitmap = getBitmapFromLruCache(id);
					if(bitmap == null){
						Log.v(TAG2, "图片" + id + "不在缓存中");
						//获取对应id缩略图
						bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),
								id,MediaStore.Images.Thumbnails.MICRO_KIND, null);
						//根据标签获取ImageView组件，为对应组件设置图片
						ImageView imageView = (ImageView) gridView.findViewWithTag(id + "image");
						if(imageView != null && bitmap != null)
							imageView.setImageBitmap(bitmap);
						//并将图片添加到缓存中
						addBitmapToLruCache(id, bitmap);
					}else{
						ImageView imageView = (ImageView) gridView.findViewWithTag(id + "image");
	                    if (imageView != null && bitmap != null) {
	                        imageView.setImageBitmap(bitmap);
	                    }
					}
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}

}
