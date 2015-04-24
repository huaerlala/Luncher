package cn.launcher.app.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class AppModel implements Parcelable {

	private String appName;
	private String packageName;
	private String className;
	private String versionName;
	private boolean check;
	
	public boolean getCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	private int versionCode;
	private Drawable icon;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(appName);
		dest.writeString(packageName);
		dest.writeString(className);
		dest.writeString(versionName);
		dest.writeInt(check ? 1 : 0);
	}
	

    public static final Parcelable.Creator<AppModel> CREATOR = new Creator<AppModel>() {
		@Override
		public AppModel[] newArray(int size) {
			return new AppModel[size];
		}

		@Override
		public AppModel createFromParcel(Parcel in) {
			AppModel model = new AppModel();
			model.setAppName(in.readString());
			model.setPackageName(in.readString());
			model.setClassName(in.readString());
			model.setVersionName(in.readString());
			model.setCheck(in.readInt() == 1 ? true : false);
			return model;
		}
	};
}
