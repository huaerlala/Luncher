package cn.launcher.app.utils;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import cn.launcher.app.R;
import cn.launcher.app.model.AppModel;

@SuppressLint("NewApi")
public class NotificationUtil {
	public static void showNotification(Context context, List<AppModel> builderData) {
		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				R.layout.custom_notification_big);
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				;
		
		contentView.setViewVisibility(R.id.line2_layout, View.GONE);
		contentView.setViewVisibility(R.id.app_icon1, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon2, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon3, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon4, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon5, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon6, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon7, View.INVISIBLE);
		contentView.setViewVisibility(R.id.app_icon8, View.INVISIBLE);
		for(int i = 0;i<builderData.size();i++){
			AppModel model = builderData.get(i);
			if(i == 0){
				contentView.setViewVisibility(R.id.app_icon1, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon1, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon1, getPendingIntent(context, model));
			}else if(i == 1){
				contentView.setViewVisibility(R.id.app_icon2, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon2, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon2, getPendingIntent(context, model));
			}else if(i == 2){
				contentView.setViewVisibility(R.id.app_icon3, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon3, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon3, getPendingIntent(context, model));
			}else if(i == 3){
				contentView.setViewVisibility(R.id.app_icon4, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon4, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon4, getPendingIntent(context, model));
			}else if(i == 4){
				contentView.setViewVisibility(R.id.line2_layout, View.VISIBLE);
				contentView.setViewVisibility(R.id.app_icon5, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon5, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon5, getPendingIntent(context, model));
			}else if(i == 5){
				contentView.setViewVisibility(R.id.app_icon6, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon6, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon6, getPendingIntent(context, model));
			}else if(i == 6){
				contentView.setViewVisibility(R.id.app_icon7, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon7, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon7, getPendingIntent(context, model));
			}else if(i == 7){
				contentView.setViewVisibility(R.id.app_icon8, View.VISIBLE);
				contentView.setImageViewBitmap(R.id.app_icon8, drawableToBitmap(model.getIcon()));
				contentView.setOnClickPendingIntent(R.id.app_icon8, getPendingIntent(context, model));
			}
		}

		Notification notification = mBuilder.build();
		notification.bigContentView = contentView;
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.flags |= Notification.FLAG_NO_CLEAR;

		// notification.contentView = contentView;
		// notification.flags |= Notification.FLAG_ONGOING_EVENT;// 将通知放到通知栏的
		// // "Ongoing" 即
		// // "正在运行" 组中
		// notification.flags |= Notification.FLAG_NO_CLEAR;

		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		mNotificationManager.notify(1, notification);
	}
	
	public static PendingIntent getPendingIntent(Context context, AppModel model){
		PackageManager manager = context.getPackageManager();
		Intent i = manager.getLaunchIntentForPackage(model.getPackageName());
		if (i == null) {
			// throw new PackageManager.NameNotFoundException();
		}
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
		return pendingIntent;
	}
	
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}
	
	public static void switchViewState(RemoteViews contentView, List<AppModel> builderData){
		if(builderData != null){
			switch(builderData.size()){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			}
		}
	}
}
