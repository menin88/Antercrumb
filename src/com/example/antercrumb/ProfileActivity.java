package com.example.antercrumb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antercrumb.utils.ScoreEntity;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;

public class ProfileActivity extends Activity {

	protected Client mKinveyClient;
	protected EditText mEditUserEmail;
	protected EditText mEditPassword;

	private RelativeLayout loadingLayout;

	protected TextView username;
	protected RelativeLayout relativeLayout1;
	protected LinearLayout linearLayout2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);

		username = (TextView) findViewById(R.id.username);
		loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanel);
		relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);

		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();

		// setAllNotVisible();
		/*
		 * new KinveyUserCallback() {
		 * 
		 * @Override public void onFailure(Throwable t) { CharSequence text =
		 * "Failure on accessing the server";
		 * Toast.makeText(getApplicationContext(), text,
		 * Toast.LENGTH_SHORT).show(); setAllVisible(); }
		 * 
		 * @Override public void onSuccess(User u) { // getUsername and save
		 * into username variable Log.e("PROFILE", "Success");
		 * username.setText(mKinveyClient.user().getUsername());
		 * setAllVisible(); }
		 * 
		 * };
		 */

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.save_button:

			// prendo il nuovo username inserito
			EditText editText = (EditText) findViewById(R.id.edit_message);
			String newUsername = editText.getText().toString();
			changeUsername(newUsername);
			Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
			
			
			Intent backAfterSaveIntent = new Intent(ProfileActivity.this,
					GameMenuActivity.class);
			ProfileActivity.this.startActivity(backAfterSaveIntent);
		//	farla finire dopo un po' se no non fa in tempo a salvare sul DB
			
		//  ProfileActivity.this.finish();

			break;
		default:
			break;

		}
	}

	/*
	 * public void userSignup(View view) { mUserEmail =
	 * mEditUserEmail.getText().toString(); mPassword =
	 * mEditPassword.getText().toString(); if (TextUtils.isEmpty(mUserEmail) ||
	 * TextUtils.isEmpty(mPassword) || !isEmailValid(mUserEmail)) {
	 * mErrorMessage .setText("Please enter a valid username and password."); }
	 * else { mErrorMessage.setText("Signing in Progress!"); setAllNotVisible();
	 * loadingLayout.setVisibility(View.VISIBLE);
	 * mKinveyClient.user().create(mUserEmail, mPassword, new
	 * KinveyUserCallback() { public void onFailure(Throwable t) { CharSequence
	 * text = "Could not sign up or User already exist";
	 * mErrorMessage.setText(text); loadingLayout.setVisibility(View.INVISIBLE);
	 * setAllVisible(); } public void onSuccess(User u) {
	 * mKinveyClient.user().put("email", u.getUsername());
	 * mKinveyClient.user().put("imageID", imageID);
	 * mKinveyClient.user().update(new KinveyUserCallback() {
	 * 
	 * @Override public void onFailure(Throwable e) {
	 * mErrorMessage.setText("Errore nel salvare i dati"); }
	 * 
	 * @Override public void onSuccess(User u) { CharSequence text =
	 * u.getUsername() + ", your account has been created.";
	 * mErrorMessage.setText(text); String userName = u.getUsername(); Intent in
	 * = new Intent(MainActivity.this, GameMenuActivity.class);
	 * in.putExtra(Utils.COME_FROM, 0); in.putExtra(Utils.USERDATA, userName);
	 * loadingLayout.setVisibility(View.INVISIBLE);
	 * MainActivity.this.startActivity(in); MainActivity.this.finish(); } });
	 * 
	 * } }); } }
	 */

	public void changeUsername(final String newUsername) {
		// receives a String from the caller and that string will be stored as
		// new username

		setAllNotVisible();
		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();
		mKinveyClient.user().put("user", newUsername);
		//WHY it deletes the imageID from the DB?

		mKinveyClient.user().update(new KinveyUserCallback() {
			@Override
			public void onFailure(Throwable t) {
				CharSequence text = "Failure on accessing the server";
				setAllVisible();
			}

			@Override
			public void onSuccess(User u) {
				CharSequence text = "The username has been changed";
				// TODO refresh in order to change the changed values

				setAllVisible();
			}

		});

	}

	// set all Views to invisible while waiting for server respons to the
	// username reading
	public void setAllNotVisible() {
		relativeLayout1.setVisibility(View.INVISIBLE);
		linearLayout2.setVisibility(View.INVISIBLE);
		loadingLayout.setVisibility(View.VISIBLE);

	}

	// if login/signin fail for some reason, set all elements visible
	public void setAllVisible() {
		relativeLayout1.setVisibility(View.VISIBLE);
		linearLayout2.setVisibility(View.VISIBLE);
		loadingLayout.setVisibility(View.INVISIBLE);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent in = new Intent(ProfileActivity.this, GameMenuActivity.class);
			// in.putExtra(Utils.COME_FROM, 1);
			// ProfileActivity.this.startActivity(in);
			ProfileActivity.this.finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void getPlayer() {

	}

	public void submit() {
		mKinveyClient.user().retrieve(new KinveyUserCallback() {
			@Override
			public void onFailure(Throwable e) {
				Toast.makeText(getApplicationContext(), "Can't show user name",
						Toast.LENGTH_LONG).show();
				setAllVisible();
			}

			@Override
			public void onSuccess(User user) {
				username.setText(user.get("user").toString());

				setAllVisible();
			}

		});
	}



	public void onStart() {
		super.onStart();
		setAllNotVisible();
		submit();

	}

	public void onPause() {
		super.onPause();

	}

	public void onResume() {
		super.onResume();

	}

	public void onDestroy() {
		super.onDestroy();

	}

}
