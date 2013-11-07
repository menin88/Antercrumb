package com.example.antercrumb;

import com.example.antercrumb.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class ProfileSettingsActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_settings);
	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.changeUsername:

			// changeUsername();

			break;
		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ProfileSettingsActivity.this.finish();

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

}
