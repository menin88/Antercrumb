package com.example.antercrumb;

import com.example.antercrumb.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends Activity{

	

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		
		

	}
	

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    //Handle the back button
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent in = new Intent(ProfileActivity.this, GameMenuActivity.class);	
	    //	in.putExtra(Utils.COME_FROM, 1);
		//	ProfileActivity.this.startActivity(in);
			ProfileActivity.this.finish();
	    	return true;
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }

	} 
	public void onStart(){
		super.onStart();
		
	}
	public void onPause(){
		super.onPause();
		
	}
	public void onResume(){
		super.onResume();
		
	}
	public void onDestroy(){
		super.onDestroy();
		
	}

}
