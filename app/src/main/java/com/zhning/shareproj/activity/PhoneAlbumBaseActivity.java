package com.zhning.shareproj.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.MediaColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.AlbumBean;
import com.zhning.shareproj.entity.ImageBean;

import java.util.ArrayList;
import java.util.List;


public class PhoneAlbumBaseActivity extends AppCompatActivity {
	ListView listView;//列表组件
	List<AlbumBean> albumlist;//专辑列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题栏
		setContentView(R.layout.activity_phone_album_base);
		
		//获得各个组件
		listView = (ListView) findViewById(R.id.list2);
		getAlbums();
		//绑定Adapter
		BaseAdapter adapter = new AlbumAdapter(this);
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.photo_album, menu);
		return super.onCreateOptionsMenu(menu);
	}

	class AlbumAdapter extends BaseAdapter{
		TextView text;
		ImageView cover;
		Context context;
		LayoutInflater mInflater;
		int height,width;
		
		public AlbumAdapter(Context context) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
			
			//获取屏幕大小确定每行显示多少张图片
			DisplayMetrics  dm = new DisplayMetrics();    
		    getWindowManager().getDefaultDisplay().getMetrics(dm);
		    int screenWidth = dm.widthPixels;
		    int screenHeight = dm.heightPixels;
		    width = screenWidth / 5;
		    height = screenHeight / 8 ;
		}
		
		@Override
		public int getCount() {
			return albumlist.size();
		}

		@Override
		public Object getItem(int position) {
			return albumlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return albumlist.get(position).getCover();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.list_cell, null);
			text = (TextView) convertView.findViewById(R.id.textInList);
			cover = (ImageView) convertView.findViewById(R.id.coverInList);
			//设置每个TextView的大小
			LayoutParams params1 = text.getLayoutParams();  
		    params1.height = height;   
		    text.setLayoutParams(params1); 
		    //设置ImageView的大小
		    LayoutParams params2 = cover.getLayoutParams();
		    params2.height = height;
		    params2.width = width;
		    cover.setLayoutParams(params2);
		    //设置相册封面
			long id = albumlist.get(position).getCover();
			Bitmap bitmap = Thumbnails.getThumbnail(context.getContentResolver(),id,Thumbnails.MICRO_KIND, null);
			cover.setImageBitmap(bitmap);
			//设置相册名字
			String str = albumlist.get(position).getName() + " ( " + albumlist.get(position).getCount() + " )";
			text.setText(str);
			text.setOnClickListener(new TextClickListener(position));
			return convertView;
		}
		
		class TextClickListener implements OnClickListener{
			int position;
			final String TAG = "AlbumActivity GETVIEW";
			public TextClickListener(int position){ this.position = position; }

			@Override
			public void onClick(View v) {
				Log.v(TAG, "Click Success");
				Intent intent = new Intent();
				ComponentName comp = new ComponentName(PhoneAlbumBaseActivity.this,PhoneItemBaseActivity.class);
				Bundle bundle = new Bundle();
				AlbumBean album = albumlist.get(position);
				bundle.putSerializable("images",(album.getImages()));
				intent.putExtras(bundle);
				intent.setComponent(comp);
				startActivity(intent);
			}
			
		}
		
	}
	
	private void getAlbums() {
		//存放专辑集合的链表，为了给SimpleAdapter的构造函数传递List类型的参数
		albumlist = new ArrayList<AlbumBean>();

				//定义搜索的多媒体库的表的地址
		Uri table = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				//定义搜索表中的哪些项
		String[] columns = {BaseColumns._ID,
				ImageColumns.BUCKET_ID,
				ImageColumns.BUCKET_DISPLAY_NAME,
				MediaColumns.DATA,
				MediaColumns.DISPLAY_NAME};
		//使用Cursor获取搜索结果
		Cursor cursor = getContentResolver().query(table, columns, null, null, null);
				//获得各项的列号，因为android中只能通过列号不能通过列名获取列值
		int imageIdColumn = cursor.getColumnIndex(columns[0]);
		int bucketIdColumn = cursor.getColumnIndex(columns[1]);
		int bucketNameColumn = cursor.getColumnIndex(columns[2]);
		int dataColumn = cursor.getColumnIndex(columns[3]);
		int imageNameColumn = cursor.getColumnIndex(columns[4]);
		//遍历搜索结果
		while(cursor.moveToNext()) {
			boolean exist = false;
			//根据columnIndex获得相应的列值
			int imageid = cursor.getInt(imageIdColumn);
			int bucketid = cursor.getInt(bucketIdColumn);
			String bucketname = cursor.getString(bucketNameColumn);
			String data = cursor.getString(dataColumn);
			String imagename = cursor.getString(imageNameColumn);

			ImageBean item = new ImageBean(imageid, bucketid, imagename, bucketname, data);
			
			//将相片添加到对应专辑的相片链表中
			
			for(AlbumBean temp : albumlist) {
				if(temp.getName().equals(bucketname)) {
					temp.addImage(item);
					exist = true;
					break;
				}
			}
			
			if(!exist) {
				//如果这张专辑不存在，新建一个专辑Map，并新建专辑中的相片List存放相片Map
				AlbumBean album = new AlbumBean(0, imageid, bucketname);
				album.addImage(item);
				albumlist.add(album);
			}

		}
		cursor.close();
		
	}
}
