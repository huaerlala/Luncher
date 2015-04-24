package cn.launcher.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private Toolbar toolbar;
	private DrawerLayout drawerLayout;
	private Handler handler;
	private TextView aboutText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		//toolbar.setTitle("");
		setSupportActionBar(toolbar);
		//是否显示左上角图标，是否可点击。4.0以下默认true,4.0以上默认false
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_settings:
					aboutText.setVisibility(View.VISIBLE);
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							aboutText.setVisibility(View.GONE);
						}
					}, 3000);
					break;
				default:
					break;
				}
				return true;
			}
		});  
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

		/**
		 * 	mDrawerLayout.setScrimColor(Color.TRANSPARENT);
			I'm Assuming that's what you mean. The shadow on the drawers (in between the drawer and the background content) is disabled by default and can be set with the
			
			setDrawerShadow(Drawable shadowDrawable, int gravity)
			setDrawerShadow(int resId, int gravity)
		**/
		drawerLayout.setScrimColor(getResources().getColor(R.color.transparent_white_aa));
		//mDrawerLayout.setDrawerShadow(R.drawable.repeat_dark, GravityCompat.START); 
		
		ActionBarDrawerToggle actionBarDrawerToggle = 
				new ActionBarDrawerToggle(this, drawerLayout, toolbar, 
						R.string.drawer_layout_open, R.string.drawer_layout_close);
        actionBarDrawerToggle.syncState();
        
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        
        handler = new Handler();
        
        aboutText = (TextView) findViewById(R.id.about_text);
		aboutText.setText(Html.fromHtml("关于<br/><br/><br/>小编:Larry<br/>QQ : 947146730<br/>Email : huaer_java@163.com"));
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
		TimeTickReceiver receiver = new TimeTickReceiver();   
	    registerReceiver(receiver, filter);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
	    getMenuInflater().inflate(R.menu.menu_main, menu);
	    return true;
	}
}
