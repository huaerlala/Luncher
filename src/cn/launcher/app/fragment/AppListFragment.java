package cn.launcher.app.fragment;

import java.util.List;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.launcher.app.R;
import cn.launcher.app.model.AppModel;
import cn.launcher.app.utils.AppUtil;
import cn.launcher.app.utils.DBHelper;
import cn.launcher.app.utils.NotificationUtil;

/**
 *
 */
public class AppListFragment extends BaseFragment {
	private RecyclerView recyclerView;
	
	private DBHelper dbHelper;  
	
	private MyAdapter adapter;
	
	private List<AppModel> gridList,recyclerList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.app_list, null);
		
		dbHelper = new DBHelper(mMainActivity); 
		
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		/**
		 * RecyclerView 首先的一个特点就是，将 layout 抽象成了一个 LayoutManager，
		 * RecylerView 不负责子 View 的布局，
		 * 我们可以自定义 LayoutManager 来实现不同的布局效果，
		 * 目前只提供了LinearLayoutManager。 
		 * LinearLayoutManager 可以指定方向，默认是垂直， 可以指定水平， 
		 * 这样就轻松实现了水平的 ListView。
		 **/
		//StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
		LinearLayoutManager layoutManager = new LinearLayoutManager(mMainActivity);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		return view;
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		gridList = AppUtil.getMyAppList(mMainActivity, dbHelper, 1);
		// 创建Adapter，并指定数据集
		recyclerList = AppUtil.getMyAppList(mMainActivity, dbHelper, 2);
		adapter = new MyAdapter();
		// 设置Adapter
		recyclerView.setAdapter(adapter);
		
		showNotification();
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
			viewHolder.check.setTag(i);
			if(model.getCheck()){
				viewHolder.check.setBackgroundResource(R.drawable.check_orange_checked);
			}else{
				viewHolder.check.setBackgroundResource(R.drawable.check_orange_uncheck);
			}
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
			public ImageView check;

			public ViewHolder(View itemView) {
				super(itemView);
				appNameText = (TextView) itemView.findViewById(R.id.app_name);
				appIconImage = (ImageView) itemView.findViewById(R.id.app_icon);
				itemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
				check = (ImageView) itemView.findViewById(R.id.app_checkbox);
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
	            Animation animation = AnimationUtils.loadAnimation(mMainActivity, android.R.anim.slide_in_left);
	            viewToAnimate.startAnimation(animation);
	            lastPosition = position;
	        }
	    }
	    
	    /**item点击事件**/
	    public void itemClick(View v){
	    		int position = Integer.parseInt(v.getTag().toString());
			AppModel model = (AppModel) recyclerList.get(position);
			if(!model.getCheck()){
				model.setCheck(!model.getCheck());
				if(!dbHelper.queryByPackageName(model.getPackageName())){
					ContentValues values = new ContentValues();  
					values.put("packagename", model.getPackageName());  
					//调用insert插入数据库  
					if(dbHelper.insert(values)){
						notifyData();
					}
					//Toast.makeText(mMainActivity, "添加成功", Toast.LENGTH_SHORT).show();
				}
			}else{
				model.setCheck(!model.getCheck());
				if(dbHelper.delete(model.getPackageName())){
					notifyData();
				}
			}
			refresh(position);
	    }
	    
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.item_layout:
				itemClick(v);
				break;
			}
		}
	}
	
	private void notifyData(){
		gridList = AppUtil.getMyAppList(mMainActivity,dbHelper,1);
		recyclerList = AppUtil.getMyAppList(mMainActivity,dbHelper,2);
		//adapter.notifyDataSetChanged();
		
		showNotification();
	}
	
	public void showNotification(){
		if(gridList != null && gridList.size() > 0){
			NotificationUtil.showNotification(mMainActivity, gridList);
		}
	}
}
