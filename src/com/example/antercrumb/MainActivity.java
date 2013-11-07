package com.example.antercrumb;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antercrumb.utils.Utils;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;

public class MainActivity extends Activity {

	protected Client mKinveyClient;
	final static String TAG = MainActivity.class.getSimpleName();

	// progress bar
	private ProgressBar mProgress;
	private int mProgressStatus = 0;
	private RelativeLayout loadingLayout;
	private int imageID;
	
	protected Button mButtonLogin;
	protected Button mFacebookLoginButton;
	protected EditText mEditUserEmail;
	protected EditText mEditPassword;
	protected TextView mErrorMessage;

	protected String mUserEmail;
	protected String mPassword;

	protected Utils util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageID = R.drawable.defaultpp;
		
		if (savedInstanceState != null) {
			mUserEmail = savedInstanceState.getString(Utils.USERMAIL);
			mPassword = savedInstanceState.getString(Utils.USERPWD);
			// set by default with last password and last username
			mEditUserEmail.setText(mUserEmail);
			mEditPassword.setText(mPassword);
		}

		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();

		// mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanel);

		mEditUserEmail = (EditText) findViewById(R.id.etEmailLogin);
		mEditPassword = (EditText) findViewById(R.id.etPassword);
		mErrorMessage = (TextView) findViewById(R.id.tvErrorMessage);

		mKinveyClient.ping(new KinveyPingCallback() {
			public void onFailure(Throwable t) {
				Log.e(TAG, "Kinvey Ping Failed", t);
			}

			public void onSuccess(Boolean b) {
				Log.d(TAG, "Kinvey Ping Success");
			}
		});

	}

	public void onStart() {
		super.onStart();
		// controlla se la sessione è gia aperta
		if (mKinveyClient.user().isUserLoggedIn()) {
			mErrorMessage.setText("User Already looged In");

			String userName = mKinveyClient.user().getUsername();

			Intent in = new Intent(MainActivity.this, GameMenuActivity.class);
			in.putExtra(Utils.COME_FROM, 0);
			in.putExtra(Utils.USERDATA, userName);

			MainActivity.this.startActivity(in);
			MainActivity.this.finish();
		}
	}

	/*
	 * public void launchFacebookLogin(View view) { final ProgressDialog
	 * progressDialog = ProgressDialog.show( MainActivity.this,
	 * "Connecting to Facebook", "Logging in with Facebook - just a moment");
	 * 
	 * doFacebookSso(progressDialog);
	 * 
	 * }
	 */

	/**
	 * Facebook SSO Oauth
	 */
	/*
	 * private void doFacebookSso(final ProgressDialog progressDialog) { try {
	 * Session.openActiveSession(this, true, new Session.StatusCallback() {
	 * 
	 * @Override public void call(Session session, SessionState state, Exception
	 * exception) { if (exception == null) { if (state.equals(RESULT_CANCELED))
	 * { Toast.makeText(MainActivity.this, "FB login cancelled",
	 * Toast.LENGTH_LONG) .show(); } else if (state.isOpened()) { if
	 * (progressDialog != null && progressDialog.isShowing()) {
	 * progressDialog.dismiss(); } Toast.makeText(MainActivity.this,
	 * "Logged in with Facebook.", Toast.LENGTH_LONG).show();
	 * 
	 * loginFacebookKinveyUser(progressDialog, session.getAccessToken()); } }
	 * else { error(progressDialog, exception.getMessage()); } } }); } catch
	 * (Exception ex) { Log.i("Kinvey - SignIn", ex.getMessage()); } }
	 * 
	 * private void loginFacebookKinveyUser(final ProgressDialog progressDialog,
	 * String accessToken) {
	 * 
	 * mKinveyClient.user().loginFacebook(accessToken, new KinveyUserCallback()
	 * {
	 * 
	 * @Override public void onFailure(Throwable e) { CharSequence text =
	 * "Wrong username or password"; Toast toast =
	 * Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
	 * toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); toast.show(); }
	 * 
	 * @Override public void onSuccess(User u) { CharSequence text =
	 * "Logged in."; Toast.makeText(getApplicationContext(), text,
	 * Toast.LENGTH_LONG).show();
	 * 
	 * ArrayList<CharSequence> dataArray = new ArrayList<CharSequence>();
	 * dataArray.add(mKinveyClient.user().getUsername());
	 * 
	 * Intent in = new Intent(MainActivity.this, GameMenuActivity.class);
	 * in.putCharSequenceArrayListExtra(Utils.USERDATA, dataArray);
	 * 
	 * MainActivity.this.startActivity(in); MainActivity.this.finish(); } });
	 * 
	 * }
	 * 
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { super.onActivityResult(requestCode,
	 * resultCode, data); Session.getActiveSession().onActivityResult(this,
	 * requestCode, resultCode, data); }
	 */
	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	// set all Views to invisible while waiting for server respons to
	// login/signin
	public void setAllNotVisible() {
		mEditUserEmail.setVisibility(View.INVISIBLE);
		mEditPassword.setVisibility(View.INVISIBLE);
		mErrorMessage.setVisibility(View.INVISIBLE);
		findViewById(R.id.btnRegisterAccount).setVisibility(View.INVISIBLE);
		findViewById(R.id.btnLogin).setVisibility(View.INVISIBLE);
	}

	// if login/signin fail for some reason, set all elements visible
	public void setAllVisible() {
		mEditUserEmail.setVisibility(View.VISIBLE);
		mEditPassword.setVisibility(View.VISIBLE);
		mErrorMessage.setVisibility(View.VISIBLE);
		findViewById(R.id.btnRegisterAccount).setVisibility(View.VISIBLE);
		findViewById(R.id.btnLogin).setVisibility(View.VISIBLE);

	}

	/**
	 * Method to handle Login button clicks - gets Username and Password and
	 * calls User Login method.
	 */
	public void login(View view) {
		// IMPORTANTE!!!: BISOGNA FARE DEI CHECK SUI DATI IMMESSI SENN0' NON HA
		// SENSO CHE UNO CREA UN ACCOUNT A BUFFO xd
		mUserEmail = mEditUserEmail.getText().toString();
		mPassword = mEditPassword.getText().toString();
		if (TextUtils.isEmpty(mUserEmail) || TextUtils.isEmpty(mPassword)
				|| !isEmailValid(mUserEmail)) {
			mErrorMessage
					.setText("Please enter a valid username and password.");
		} else {
			if (mKinveyClient.user().isUserLoggedIn()) {
				mErrorMessage.setText("User Already looged In");

				String userName = mKinveyClient.user().getUsername();

				Intent in = new Intent(MainActivity.this,
						GameMenuActivity.class);
				in.putExtra(Utils.COME_FROM, 0);
				in.putExtra(Utils.USERDATA, userName);

				MainActivity.this.startActivity(in);
				MainActivity.this.finish();
			} else {
				mErrorMessage.setText("Login in progress ...");
				setAllNotVisible();
				loadingLayout.setVisibility(View.VISIBLE);
				// should set other elements invisible
				userLogin(mUserEmail, mPassword);
			}
		}
	}

	public void userLogin(String userMail, String userPassword) {

		mKinveyClient.user().login(userMail, userPassword,
				new KinveyUserCallback() {
					public void onFailure(Throwable t) {
						CharSequence text = "Wrong username or password";
						mErrorMessage.setText("Wrong username or password");
						loadingLayout.setVisibility(View.INVISIBLE);
						// onAuthenticationResult(null);
					}

					public void onSuccess(User u) {
						CharSequence text = "Logged in.";
						mErrorMessage.setText(text);
						String userName = u.getUsername();

						Intent in = new Intent(MainActivity.this,
								GameMenuActivity.class);
						in.putExtra(Utils.COME_FROM, 0);
						in.putExtra(Utils.USERDATA, userName);
						loadingLayout.setVisibility(View.INVISIBLE);
						MainActivity.this.startActivity(in);
						MainActivity.this.finish();
					}

				});
	}

	public void userSignup(View view) {
		mUserEmail = mEditUserEmail.getText().toString();
		mPassword = mEditPassword.getText().toString();
		if (TextUtils.isEmpty(mUserEmail) || TextUtils.isEmpty(mPassword)
				|| !isEmailValid(mUserEmail)) {
			mErrorMessage
					.setText("Please enter a valid username and password.");
		} else {
			mErrorMessage.setText("Signing in Progress!");
			setAllNotVisible();
			loadingLayout.setVisibility(View.VISIBLE);
			mKinveyClient.user().create(mUserEmail, mPassword,
					new KinveyUserCallback() {
						public void onFailure(Throwable t) {
							CharSequence text = "Could not sign up or User already exist";
							mErrorMessage.setText(text);
							loadingLayout.setVisibility(View.INVISIBLE);
							setAllVisible();
						}
						public void onSuccess(User u) {
							mKinveyClient.user().put("email", u.getUsername());
							mKinveyClient.user().put("imageID", imageID);
							mKinveyClient.user().update(new KinveyUserCallback() {
						    @Override
						    public void onFailure(Throwable e) {
						    	mErrorMessage.setText("Errore nel salvare i dati");
						    }
						    @Override
						    public void onSuccess(User u) {
						    	CharSequence text = u.getUsername()
										+ ", your account has been created.";
								mErrorMessage.setText(text);
								String userName = u.getUsername();
								Intent in = new Intent(MainActivity.this,
										GameMenuActivity.class);
								in.putExtra(Utils.COME_FROM, 0);
								in.putExtra(Utils.USERDATA, userName);
								loadingLayout.setVisibility(View.INVISIBLE);
								MainActivity.this.startActivity(in);
								MainActivity.this.finish();
						    }
						    });
							
						}
					});
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Ask the user if they want to quit
			new AlertDialog.Builder(this)
					.setIcon(R.drawable.crumb)
					.setTitle("Alert")
					.setMessage("Really quitting?")
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// Stop the activity
									MainActivity.this.finish();
								}

							}).setNegativeButton(R.string.cancel, null).show();

			return true;
		} else if (keyCode == KeyEvent.KEYCODE_ENTER) {
			mUserEmail = mEditUserEmail.getText().toString();
			mPassword = mEditPassword.getText().toString();
			if (TextUtils.isEmpty(mUserEmail) || TextUtils.isEmpty(mPassword)
					|| !isEmailValid(mUserEmail)) {
				mErrorMessage
						.setText("Please enter a valid username and password.");
			} else {
				userLogin(mUserEmail, mPassword);
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getParent());
		// Add the buttons
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog

					}
				});
		// Set other dialog properties

		builder.setTitle("Caution!");
		builder.setMessage("Are you already leaving?");
		// Create the AlertDialog
		AlertDialog dialog = builder.create();
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */
	protected void error(ProgressDialog progressDialog, String error) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		Log.d(TAG, "Error " + error);
	}

	// called after onStart()
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		mUserEmail = savedInstanceState.getString(Utils.USERMAIL);
		mPassword = savedInstanceState.getString(Utils.USERPWD);
		// set by default with last password and last username
		mEditUserEmail.setText(mUserEmail);
		mEditPassword.setText(mPassword);

	}

	// called before onDestroy()
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString(Utils.USERMAIL, mUserEmail);
		savedInstanceState.putString(Utils.USERPWD, mPassword);

	}

}
