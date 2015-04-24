package cn.launcher.app;

import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.launcher.app.model.AppModel;
import cn.launcher.app.utils.AppUtil;
import cn.launcher.app.utils.DBHelper;
import cn.launcher.app.utils.NotificationUtil;

public class CopyOfMainActivity extends Activity {
	private DBHelper dbHelper;  
	
	private GridView gridView;
	private RecyclerView recyclerView;
	private GridViewAdapter gridAdapter;
	private MyAdapter adapter;
	
	private List<AppModel> gridList,recyclerList;
	
	private TextView aboutText;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppUtil.init(getApplicationContext());
		
		Intent i = new Intent();
		i.setClass(this, BootService.class);
		startService(i);
		
		initView();
		
		dbHelper = new DBHelper(this); 
		
		reloadData();
		handler = new Handler();
	}
	
	public void showNotification(){
		if(gridList != null && gridList.size() > 0){
			NotificationUtil.showNotification(this, gridList);
		}
	}
	
	public void initView(){
		aboutText = (TextView) findViewById(R.id.about_text);
		aboutText.setText(Html.fromHtml("关于<br/><br/><br/>小编:Larry<br/>QQ:947146730<br/>Email:huaer_java@163.com"));
		gridView=(GridView) findViewById(R.id.gridview);
		
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		/**
		 * RecyclerView 首先的一个特点就是，将 layout 抽象成了一个 LayoutManager，
		 * RecylerView 不负责子 View 的布局，
		 * 我们可以自定义 LayoutManager 来实现不同的布局效果，
		 * 目前只提供了LinearLayoutManager。 
		 * LinearLayoutManager 可以指定方向，默认是垂直， 可以指定水平， 
		 * 这样就轻松实现了水平的 ListView。
		 **/
		
//		StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		
		findViewById(R.id.about_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutText.setVisibility(View.VISIBLE);
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						aboutText.setVisibility(View.GONE);
					}
				}, 3000);
			}
		});
	}
	
	public void reloadData(){
		gridList = AppUtil.getMyAppList(this,dbHelper,1);
		gridAdapter = new GridViewAdapter();
		gridView.setAdapter(gridAdapter);
		
		// 创建Adapter，并指定数据集
		recyclerList = AppUtil.getMyAppList(this,dbHelper,2);
		adapter = new MyAdapter();
		// 设置Adapter
		recyclerView.setAdapter(adapter);   
	}
	

	public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements OnClickListener {
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			// 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
			View view = View.inflate(viewGroup.getContext(), R.layout.main_list_item, null);
			// 创建一个ViewHolder
			ViewHolder holder = new ViewHolder(view);
			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int i) {
			// 绑定数据到ViewHolder上
			AppModel model = recyclerList.get(i);
			viewHolder.appNameText.setText(model.getAppName());
			
			viewHolder.appIconImage.setImageDrawable(model.getIcon());//.setBackgroundDrawable(model.getIcon());
			viewHolder.itemLayout.setTag(i);
			viewHolder.itemLayout.setOnClickListener(this);
			setAnimation(viewHolder.itemLayout, i);
		}

		@Override
		public int getItemCount() {
			return recyclerList.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			public TextView appNameText;
			public ImageView appIconImage;
			public RelativeLayout itemLayout;

			public ViewHolder(View itemView) {
				super(itemView);
				appNameText = (TextView) itemView.findViewById(R.id.app_name);
				appIconImage = (ImageView) itemView.findViewById(R.id.app_icon);
				itemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
			}
		}

		public void remove(int position) {
			recyclerList.remove(position);
	        notifyItemRemoved(position);
	    }
		
		public void remove(AppModel model, int position) {
			recyclerList.remove(model);
	        notifyItemRemoved(position);
	    }

	    public void add(AppModel model, int position) {
	    		recyclerList.add(position, model);
	        notifyItemInserted(position);
	    }
	    
	    public void refresh(int position) {
	        notifyItemChanged(position);
	    }
	    
	    /**点击之后改变值刷新当前数据**/
	    public void change(int position){
//			mDataset.get(position).setIsChecked(mDataset.get(position).getIsChecked() == 0 ? 1 : 0);
//			refresh(position);
		}
	    
	    private int lastPosition = -1;
	    /**
	     * Here is the key method to apply the animation
	     */
	    private void setAnimation(View viewToAnimate, int position)
	    {
	        // If the bound view wasn't previously displayed on screen, it's animated
	        if (position > lastPosition)
	        {
	            Animation animation = AnimationUtils.loadAnimation(CopyOfMainActivity.this, android.R.anim.slide_in_left);
	            viewToAnimate.startAnimation(animation);
	            lastPosition = position;
	        }
	    }
	    
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.item_layout:
				int position = Integer.parseInt(v.getTag().toString());
				AppModel model = (AppModel) recyclerList.get(position);

				if(!dbHelper.queryByPackageName(model.getPackageName())){
					ContentValues values = new ContentValues();  
					values.put("packagename", model.getPackageName());  
		              
					//调用insert插入数据库  
					dbHelper.insert(values);
					notifyData();
					Toast.makeText(CopyOfMainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(CopyOfMainActivity.this, "添加失败，最多只能添加8个", Toast.LENGTH_SHORT).show();
				}
				
				break;
			}
		}
	}
	
	
	private class GridViewAdapter extends BaseAdapter implements OnClickListener {
		@Override
		public int getCount() {
			return gridList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				v = LayoutInflater.from(CopyOfMainActivity.this).inflate(R.layout.grid_item, null);
				MyHolder holder = new MyHolder();
				holder.appNameText = (TextView) v.findViewById(R.id.app_name);
				holder.appIconImage = (ImageView) v.findViewById(R.id.app_icon);
				v.setTag(holder);
			} else {
				v = convertView;
			}
			MyHolder holder = (MyHolder) v.getTag();
			AppModel model = gridList.get(position);
			holder.appIconImage.setImageDrawable(model.getIcon());
			holder.appNameText.setText(model.getAppName());
			holder.appIconImage.setTag(model);
			holder.appIconImage.setOnClickListener(this);
			return v;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.app_icon:
				AppModel model = (AppModel) v.getTag();
				dbHelper.delete(model.getPackageName());
				notifyData();
				Toast.makeText(CopyOfMainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	// 配置 图片 文字
	private class MyHolder {
		TextView appNameText;
		ImageView appIconImage;
	}
	
	private void notifyData(){
		gridList = AppUtil.getMyAppList(CopyOfMainActivity.this,dbHelper,1);
		gridAdapter.notifyDataSetChanged();
		
		recyclerList = AppUtil.getMyAppList(CopyOfMainActivity.this,dbHelper,2);
		adapter.notifyDataSetChanged();
		
		showNotification();
	}
}
