package cn.launcher.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.launcher.app.MainActivity;

public abstract class BaseFragment extends android.support.v4.app.Fragment {
	protected MainActivity mMainActivity;

	protected boolean isDestoryView = false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mMainActivity = (MainActivity) activity;

		AppFragmentManager.getAppManager().addFragment(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		isDestoryView = true;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		MobclickAgent.onResume(mMainActivity);
	}  

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		MobclickAgent.onPause(mMainActivity);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private boolean tag = false;

	@Override
	public void onStart() {
		super.onStart();
		if (!tag) {
			setData();
		}
		tag = true;
	}

	protected OnClickListener onClickListenerBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			BaseFragment.this.getFragmentManager().popBackStackImmediate();
		}
	};

	protected abstract void setData();

	public void onBackPressed() {
		// Pop Fragments off backstack and do your other checks
	}

	protected void openActivity(Class<?> cls){
		Intent intent = new Intent(mMainActivity, cls);
		startActivity(intent);
	}
}
