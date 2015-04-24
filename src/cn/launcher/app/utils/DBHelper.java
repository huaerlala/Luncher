package cn.launcher.app.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private final static int VERSION = 1;
	private final static String DB_NAME = "larrylauncher.db";
	private final static String TABLE_NAME = "launcher";
	private final static String CREATE_TBL = "create table launcher(id integer primary key autoincrement, packagename text)";
	private SQLiteDatabase db;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBHelper(Context context) {
		this(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(CREATE_TBL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean insert(ContentValues values) {
		boolean flag = false;
		//if (queryCount()) {
			// 获取SQLiteDatabase实例
			SQLiteDatabase db = getWritableDatabase();
			// 插入数据库中
			try {
				if(db.insert(TABLE_NAME, null, values) != 0){
					flag = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				db.close();
			}
		//}
		return flag;
	}

	public Cursor query() {
		SQLiteDatabase db = getReadableDatabase();
		// 获取Cursor
		Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null,
				null);
		return c;

	}

	public boolean queryByPackageName(String packageName) {
		SQLiteDatabase db = getReadableDatabase();
		try {
			// 获取Cursor
			Cursor c = db.rawQuery(
					"select packagename from launcher where packagename=?",
					new String[] { packageName });
			while (c.moveToNext()) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return false;
	}

	public boolean queryCount() {
		SQLiteDatabase db = getReadableDatabase();
		try {
			// 获取Cursor
			Cursor c = db.rawQuery("select count(*) from launcher", null);
			while (c.moveToNext()) {
				if (c.getInt(0) > 7) {
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return false;

	}
	
	public List<String> queryAll() {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = getReadableDatabase();
		try {
			// 获取Cursor
			Cursor c = db.rawQuery("select packagename from launcher order by id", null);
			while (c.moveToNext()) {
				list.add(c.getString(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return list;

	}

	public void delete(int id) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.delete(TABLE_NAME, "_id=?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public boolean delete(String packageName) {
		boolean flag = false;
		SQLiteDatabase db = getWritableDatabase();
		try {
			if( db.delete(TABLE_NAME, "packagename=?", new String[] { packageName })!= 0){
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return flag;
	}

	public void update(ContentValues values, String whereClause,
			String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		db.update(TABLE_NAME, values, whereClause, whereArgs);
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}

}
