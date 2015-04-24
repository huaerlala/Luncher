package cn.launcher.app;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import cn.launcher.app.model.AppModel;
import cn.launcher.app.utils.AppUtil;
import cn.launcher.app.utils.DBHelper;
import cn.launcher.app.utils.NotificationUtil;

public class BootService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		AppUtil.init(getApplicationContext());
		DBHelper dbHelper = new DBHelper(this); 
		List<AppModel> gridList = AppUtil.getMyAppList(this,dbHelper, 1);
		if(gridList != null && gridList.size() > 0){
			NotificationUtil.showNotification(this, gridList);
		}
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);   
	      
		TimeTickReceiver receiver = new TimeTickReceiver();   
	    registerReceiver(receiver, filter); 
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Intent i = new Intent();
		i.setClass(this, BootService.class);
		startService(i);
	}
}
