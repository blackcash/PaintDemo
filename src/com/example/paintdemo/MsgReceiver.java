package com.example.paintdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MsgReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction() == "com.example.receiver.msg1"){
			Log.d("MsgReceiver1", intent.getStringExtra("msg"));
			Toast.makeText(context, intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
		}
	}

}
