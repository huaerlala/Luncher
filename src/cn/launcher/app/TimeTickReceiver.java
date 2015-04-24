package cn.launcher.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeTickReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		 //检查Service状态   
		boolean isServiceRunning = false;   
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);   
	    for (RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) {
	    		if("cn.launcher.app.BootService".equals(service.service.getClassName())){   
	    			isServiceRunning = true;   
	    		}   
	    }   
		if (!isServiceRunning) {
			Intent i = new Intent(context, BootService.class);   
	        context.startService(i);   
	    }   
	  
	}
}
