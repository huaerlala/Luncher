package cn.launcher.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.launcher.app.R;

/**
 *
 */
public class MenuListFragment extends BaseFragment implements OnClickListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menu_list, null);
		return view;
	}

	private void reloadData() {
	}
	
	@Override
	protected void setData() {
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		}
	}
}
