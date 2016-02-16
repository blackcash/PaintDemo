package com.example.paintdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

public abstract class DefaultSetting implements BitmapInterface{
    	
	private static int counter = 0;
	private Timer timer = new Timer( );
	private TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			counter++;
			Log.d("sec", counter+"sec");
			setSec(counter);
		}
	}; 
	
	public void setSecCounter(int c){
		counter = c;
	}
	public void getString(){
		Log.d("DefaultSetting", "getString");
	}
	
	public void timeStart(){
		timer.schedule(task,1000,1000);
	}
	
	public void timeStop(){
		timer.cancel();
	}
	
	abstract void getBitmap();
}
