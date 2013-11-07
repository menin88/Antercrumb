package com.example.antercrumb;

import com.example.antercrumb.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.

	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    //Handle the back button
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent in = new Intent(SettingsActivity.this, GameMenuActivity.class);	
	    	in.putExtra(Utils.COME_FROM, 1);
			SettingsActivity.this.finish();

	    	return true; 
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }

	}


}
