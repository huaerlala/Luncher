package cn.launcher.app.fragment;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author lw
 * @version 1.0
 * @created 2012-3-21
 */
public class AppFragmentManager {
	
	private static Stack<Fragment> fragmentStack;
	private static AppFragmentManager instance;
	
	private AppFragmentManager(){}
	/**
	 */
	public static AppFragmentManager getAppManager(){
		if(instance==null){
			instance=new AppFragmentManager();
		}
		return instance;
	}
	/**
	 */
	public void addFragment(Fragment fragment){
		if(fragmentStack==null){
			fragmentStack=new Stack<Fragment>();
		}
		fragmentStack.add(fragment);
	}
	public void popBackStackFragment(Fragment fragment){
		if(fragment!=null && fragment.getFragmentManager() != null){
			fragment.getFragmentManager().popBackStackImmediate();
		}
	}
	
	public void popBackStackFragment(Class<?> cls){
		if(cls != null){
			for (Fragment fragment : fragmentStack) {
				if(fragment.getClass().equals(cls) ){
					popBackStackFragment(fragment);
				}
			}
		}
	}
	
	public Fragment getStrackFragment(Class<?> cls){
		Fragment f = null;
		if(cls != null){
			for (Fragment fragment : fragmentStack) {
				if(fragment.getClass().equals(cls) ){
					f = fragment;
				}
			}
		}
		return f;
	}
}