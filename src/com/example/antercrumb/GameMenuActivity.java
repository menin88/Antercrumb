package com.example.antercrumb;

import java.util.ArrayList;

import com.example.antercrumb.utils.Utils;
import com.kinvey.android.Client;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//papparappa!!!
public class GameMenuActivity extends Activity {

	private static final String PREFS_NAME = "MY_PREFERENCES";
	private Client mKinveyClient;
	private boolean[] settingsHolder = new boolean[4];
	private TextView wellcome;
	private TextView logOutButton;
	private TextView scoreBtn;
	private String dataArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		// setText();

		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();
		logOutButton = (TextView) findViewById(R.id.exit);
		scoreBtn = (TextView) findViewById(R.id.scoreMenu);
		wellcome = (TextView) findViewById(R.id.userEmail);
		
		if(getIntent().getIntExtra(Utils.COME_FROM, 0) == 0 ){
		dataArray = getIntent().getStringExtra(Utils.USERDATA);

		wellcome.setText(dataArray);
		}
		else if (getIntent().getIntExtra(Utils.COME_FROM, 0) == 1){
			wellcome.setText("vengo da score");
		}
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.start:
			break;

		case R.id.scoreMenu:
			Toast.makeText(this, "Score button pressed", Toast.LENGTH_SHORT).show();
			Intent scoreIntent = new Intent(GameMenuActivity.this,
					ScoreActivity.class);
			//GameMenuActivity.this.startActivityForResult(scoreIntent, 1);
			GameMenuActivity.this.startActivity(scoreIntent);
			GameMenuActivity.this.finish();
			break;
		/*
		 * case R.id.graphics: editor.putBoolean("graphics",
		 * !settingsHolder[3]); break;
		 */
		case R.id.exit:
			String text = "";
			mKinveyClient.user().logout().execute();
			if (mKinveyClient.user().isUserLoggedIn()) {
				text = "Still logged";
			} else {
				text = "Not Logged";
			}
			Toast.makeText(getBaseContext(), "User State: " + text,
					Toast.LENGTH_LONG).show();
			Intent in = new Intent(GameMenuActivity.this, MainActivity.class);

			GameMenuActivity.this.startActivity(in);
			GameMenuActivity.this.finish();
			break;

		default:
			break;
		}

	}
	public void onStart(){
		super.onStart();
	}
	
	public void onRestart(){
		super.onRestart();
		
	}
	

	/*
	 * @Override public void onClick(View v) { SharedPreferences settings =
	 * getSharedPreferences(MenuActivity.PREFS_NAME, 0);
	 * SharedPreferences.Editor editor = settings.edit();
	 * 
	 * switch(v.getId()){ case R.id.sound: editor.putBoolean("sound",
	 * !settingsHolder[0]); break; case R.id.music: editor.putBoolean("music",
	 * !settingsHolder[1]); break; case R.id.vibrate:
	 * editor.putBoolean("vibrate", !settingsHolder[2]); break; case
	 * R.id.graphics: editor.putBoolean("graphics", !settingsHolder[3]); break;
	 * case R.id.back: finish(); break; }
	 * 
	 * editor.commit();
	 * 
	 * getSettings(); setText(); }
	 */

	public void setText() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"data/fonts/font1.ttf");// font3 NO!!
		TextView start = (TextView) findViewById(R.id.start);
		TextView score = (TextView) findViewById(R.id.scoreMenu);
		TextView exit = (TextView) findViewById(R.id.exit);
		start.setTypeface(tf);
		exit.setTypeface(tf);
		score.setTypeface(tf);
	}

	private void getSettings() {
		SharedPreferences settings = getSharedPreferences(
				GameMenuActivity.PREFS_NAME, 0);

		settingsHolder[0] = settings.getBoolean("sound", true);
		settingsHolder[1] = settings.getBoolean("music", true);
		settingsHolder[2] = settings.getBoolean("vibrate", true);
		settingsHolder[3] = settings.getBoolean("graphics", true);
	}

	public void onResume() {
		super.onResume();

	}

	public void onPause() {
		super.onPause();
	}
	

}
