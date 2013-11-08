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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameMenuActivity extends Activity {

	private static final String PREFS_NAME = "MY_PREFERENCES";
	private Client mKinveyClient;
	private boolean[] settingsHolder = new boolean[4];

	private TextView profileBtn;
	private TextView wellcome;
	private TextView logOutButton;
	private TextView scoreBtn;
	private String dataArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_menu_activity);

		// setText();

		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();

		/*
		 * if(getIntent().getIntExtra(Utils.COME_FROM, 0) == 0 ){ dataArray =
		 * getIntent().getStringExtra(Utils.USERDATA);
		 * 
		 * wellcome.setText(dataArray); } else if
		 * (getIntent().getIntExtra(Utils.COME_FROM, 0) == 1){
		 * wellcome.setText("vengo da score"); }
		 */

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.toProfileBar:
			Toast.makeText(this, "Profile button pressed", Toast.LENGTH_SHORT)
					.show();

			GameMenuActivity.this.startActivity(new Intent(
					GameMenuActivity.this, ProfileActivity.class));
			break;
		/*
		 * case R.id.imageView1: Toast.makeText(this, "Profile button pressed",
		 * Toast.LENGTH_SHORT) .show();
		 * 
		 * GameMenuActivity.this.startActivity(new Intent(
		 * GameMenuActivity.this, ProfileActivity.class)); //
		 * GameMenuActivity.this.finish(); break;
		 * 
		 * case R.id.profileText: Toast.makeText(this, "Profile button pressed",
		 * Toast.LENGTH_SHORT) .show();
		 * 
		 * GameMenuActivity.this.startActivity(new Intent(
		 * GameMenuActivity.this, ProfileActivity.class)); //
		 * GameMenuActivity.this.finish(); break;
		 */
		case R.id.scoreMenu:
			Toast.makeText(this, "Score button pressed", Toast.LENGTH_SHORT)
					.show();
			Intent scoreIntent = new Intent(GameMenuActivity.this,
					ScoreActivity.class);
			// GameMenuActivity.this.startActivityForResult(scoreIntent, 1);
			GameMenuActivity.this.startActivity(scoreIntent);
			// GameMenuActivity.this.finish();
			break;
		/*
		 * case R.id.graphics: editor.putBoolean("graphics",
		 * !settingsHolder[3]); break;
		 */
		case R.id.playTheGame:
			break;

		case R.id.logout:

			mKinveyClient.user().logout().execute();

			Intent intent = new Intent(GameMenuActivity.this,
					MainActivity.class);
			GameMenuActivity.this.startActivity(intent);
			GameMenuActivity.this.finish();
			break;

		default:
			break;
		}

	}

	public void onStart() {
		super.onStart();
	}

	public void onRestart() {
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

		// TextView profile = (TextView) findViewById(R.id.profile);
		TextView start = (TextView) findViewById(R.id.playTheGame);
		TextView score = (TextView) findViewById(R.id.scoreMenu);

		// profile.setTypeface(tf);
		start.setTypeface(tf);

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
