package com.example.paintdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {

	private String TAG = "LocalService";
	
	public LocalBinder myBinder = new LocalBinder(); 
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "LocalService onBind");
		return myBinder;
	}

	public void sayHelloWorld() {
		Log.d(TAG, "LocalService sayHelloWorld");
		Toast.makeText(this.getApplicationContext(),
				"Hello World Local Service!", Toast.LENGTH_SHORT).show();
	}

    @Override 
    public void onCreate() { 
        super.onCreate(); 
        Log.d(TAG, "MainService onCreate");
    } 

    @Override 
    public int onStartCommand(Intent intent, int flags, int startId) { 
        Log.d(TAG, "MainService onStartCommand");
        return super.onStartCommand(intent, flags, startId); 
    } 

    @Override 
    public void onDestroy() { 
        super.onDestroy(); 
        Log.d(TAG, "MainService onDestroy");
    } 
    
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "LocalService onUnbind");
		return super.onUnbind(intent);
	}

	public class LocalBinder extends Binder {
		LocalService getService() {
			return LocalService.this;
		}
	}
}
