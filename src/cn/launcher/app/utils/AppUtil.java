package cn.launcher.app.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import cn.launcher.app.model.AppModel;

public class AppUtil {
	public static final String SYS_PARAMS = "SYS_PARAMS";
//	public static final String SAVA_APP_KEY_SET = "SavaLauncherAppPackageNameSETList";

	public static Context ctx;

	public static void init(Context context) {
		if (ctx == null) {
			ctx = context;
		}
	}

	/**
	 * 
	 * @param context
	 * @param type  1:添加的APP
	 * @return
	 */
	public static List<AppModel> getMyAppList(Context context, DBHelper dbHelper, int type) {
		List<AppModel> appList = new ArrayList<AppModel>(); // 用来存储获取的应用信息数据
		
		
//		Set<String> set = AppUtil.getParam(AppUtil.SAVA_APP_KEY_SET, new HashSet<String>());
		List<String> set = dbHelper.queryAll();
		PackageManager packageManager = context.getPackageManager();
		List<ApplicationInfo> list = packageManager
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (int i = 0; i < list.size(); i++) {
			ApplicationInfo applicationInfo = list.get(i);
			boolean isAdd = false;
			if (set.contains(applicationInfo.packageName)) {
				isAdd = true;
			}
			if(type == 1){
				if(isAdd){
					AppModel tmpInfo = new AppModel();
					tmpInfo.setAppName(applicationInfo.loadLabel(
							context.getPackageManager()).toString());
					tmpInfo.setPackageName(applicationInfo.packageName);
					tmpInfo.setClassName(applicationInfo.className);
					tmpInfo.setVersionName("0");
					tmpInfo.setVersionCode(0);
					tmpInfo.setIcon(applicationInfo.loadIcon(context
							.getPackageManager()));
					// Only display the non-system app info
					// 如果非系统应用，则添加至appList
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
						appList.add(tmpInfo);
					}
				}
			}else{
				
					AppModel tmpInfo = new AppModel();
					tmpInfo.setAppName(applicationInfo.loadLabel(
							context.getPackageManager()).toString());
					tmpInfo.setPackageName(applicationInfo.packageName);
					tmpInfo.setClassName(applicationInfo.className);
					tmpInfo.setVersionName("0");
					tmpInfo.setVersionCode(0);
					tmpInfo.setIcon(applicationInfo.loadIcon(context
							.getPackageManager()));
					if(isAdd){
						tmpInfo.setCheck(true);
					}
					// Only display the non-system app info
					// 如果非系统应用，则添加至appList
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
						appList.add(tmpInfo);
					}
			}
		}
		return appList;
	}

	public static List<ResolveInfo> getAppList(Context context) {
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> pkgAppsList = context.getPackageManager()
				.queryIntentActivities(mainIntent, 0);
		return pkgAppsList;
	}

	/**
	 * Open another app.
	 * @deprecated
	 * @param context
	 *            current Context, like Activity, App, or Service
	 * @param packageName
	 *            the full package name of the app to open
	 * @return true if likely successful, false if unsuccessful
	 */
	public static void openApp(Context context, ResolveInfo resolveinfo) {
		// packagename = 参数packname
		String packageName = resolveinfo.activityInfo.packageName;
		// 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
		String className = resolveinfo.activityInfo.name;
		// LAUNCHER Intent
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		// 设置ComponentName参数1:packagename参数2:MainActivity路径
		ComponentName cn = new ComponentName(packageName, className);

		intent.setComponent(cn);

		context.startActivity(intent);
	}

	/**
	 * Open another app.
	 * 
	 * @param context
	 *            current Context, like Activity, App, or Service
	 * @param packageName
	 *            the full package name of the app to open
	 * @return true if likely successful, false if unsuccessful
	 */
	public static boolean openApp(Context context, String packageName) {
		PackageManager manager = context.getPackageManager();
		try {
			Intent i = manager.getLaunchIntentForPackage(packageName);
			if (i == null) {
				return false;
				// throw new PackageManager.NameNotFoundException();
			}
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			context.startActivity(i);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	/** SharedPreferences 操作 **/
	public static SharedPreferences getConfig() {
		return AppUtil.ctx.getSharedPreferences(SYS_PARAMS,
				Context.MODE_PRIVATE);
	}

	public static void saveParam(Editor db, String code, String value) {
		db.putString(code, value);
	}

	public static void saveParam(String code, Object value) {
		SharedPreferences sp = getConfig();
		Editor tx = sp.edit();
		try {
			saveParam(tx, code, value == null ? "" : value + "");
		} finally {
			tx.commit();
		}
	}

	public static String getParam(String code, String defaultValue) {
		SharedPreferences sp = getConfig();
		return sp.getString(code, defaultValue);
	}

	
	public static void saveParam(Editor db, String code, Set<String> value) {
		db.putStringSet(code, value);
	}
	
	public static void saveParam(String code, Set<String> value){
		SharedPreferences sp = getConfig();
		Editor tx = sp.edit();
		try {
			saveParam(tx, code, value);
		} finally {
			tx.commit();
		}
	}
	
	public static Set<String> getParam(String code, Set<String> defaultValue) {
		SharedPreferences sp = getConfig();
		return sp.getStringSet(code, defaultValue);
	}
}
