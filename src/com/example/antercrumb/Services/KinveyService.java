package com.example.antercrumb.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

//Need to use this kind of service for maintain the app connected to the kinvey server
//by calling just one instance of Kinvey Client (running in background)
//and Binding it to the running activity
public class KinveyService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void onCreate() {
    
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		return 0;
    
    }
    
    @Override
    public void onDestroy() {
    	
    }


}
