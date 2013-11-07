package com.example.antercrumb;

import com.example.antercrumb.utils.Utils;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	protected Client mKinveyClient;
	protected EditText mEditUserEmail;
	protected EditText mEditPassword;
	protected TextView mErrorMessage;

	private RelativeLayout loadingLayout;

	protected TextView username;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		
	//	setAllNotVisible();
	//	loadingLayout.setVisibility(View.VISIBLE);
		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();
		new KinveyUserCallback() {
			@Override
			public void onFailure(Throwable t) {
				CharSequence text = "Failure on accessing the server";
	//			mErrorMessage.setText(text);
	//			loadingLayout.setVisibility(View.INVISIBLE);
	//			setAllVisible();
			}

			@Override
			public void onSuccess(User u) {
				//getUsername and save into usarname variable 
				username.setText(mKinveyClient.user().getUsername());
		        setContentView(username);
		        

			}

		}
		;
		
		


	}

	// set all Views to invisible while waiting for server respons to the
	// username reading
	// WHY  it could NOT see these methods from MainActivity?
	public void setAllNotVisible() {
		mEditUserEmail.setVisibility(View.INVISIBLE);
		mEditPassword.setVisibility(View.INVISIBLE);
		mErrorMessage.setVisibility(View.INVISIBLE);
	}

	// if login/signin fail for some reason, set all elements visible
	public void setAllVisible() {
		mEditUserEmail.setVisibility(View.VISIBLE);
		mEditPassword.setVisibility(View.VISIBLE);
		mErrorMessage.setVisibility(View.VISIBLE);
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
		//receives a String from the caller and that string will be stored as new username
		
		setAllNotVisible();
		loadingLayout.setVisibility(View.VISIBLE);
		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();
		new KinveyUserCallback() {
			@Override
			public void onFailure(Throwable t) {
				CharSequence text = "Failure on accessing the server";
				mErrorMessage.setText(text);
				loadingLayout.setVisibility(View.INVISIBLE);
				setAllVisible();
			}

			@Override
			public void onSuccess(User u) {
				mKinveyClient.user().put("username", newUsername);
			
			}

		};
		
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

	public void onStart() {
		super.onStart();

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
