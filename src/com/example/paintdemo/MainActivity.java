package com.example.paintdemo;

import com.example.paintdemo.LocalService.LocalBinder;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	DrawingView dv;
	PaintView pv;
	LinearLayout layout;
	FrameLayout frame;
	TextView tv;
	Button btn1, btn2, btn3, btn4;
	ImageView iv;
	ServiceConnection mSc;
	public LocalService myService;
	private MainReceiver receiver = new MainReceiver();

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				int counter = (int) msg.obj;
				tv.setText(counter + "秒");
				break;
			case 1:
				Bitmap background,
				front;
				front = (Bitmap) msg.obj;
				background = pv.getBitmap();
				// iv.setImageBitmap((Bitmap)msg.obj);
				// iv.setImageBitmap(pv.getBitmap());
				Bitmap newbmp = Bitmap.createBitmap(background.getWidth(),
						background.getHeight(), Config.ARGB_8888);
				Canvas cv = new Canvas(newbmp);
				cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
				cv.drawBitmap(front, 0, 0, null);// 在 0，0坐标开始画入fg ，可以从任意位置画入
				cv.save(Canvas.ALL_SAVE_FLAG);// 保存
				cv.restore();// 存储
				iv.setImageBitmap(newbmp);
				break;
			default:
				break;
			}

		};
	};
	DefaultSetting setting = new DefaultSetting() {

		@Override
		public void setBitmap(Bitmap mBitmap) {
			Log.d("setbitmap", "");
			Message msg = new Message();
			msg = mHandler.obtainMessage(1, mBitmap);
			mHandler.sendMessage(msg);
		}

		@Override
		void getBitmap() {
			Log.d("getBitmap", "=====================");

		}

		@Override
		public void setSec(int sec) {
			Log.d("setbitmap", "");
			Message msg = new Message();
			msg = mHandler.obtainMessage(0, sec);
			mHandler.sendMessage(msg);
		}
	};

	// BitmapInterface bif = new BitmapInterface() {
	//
	// @Override
	// public void setBitmap(Bitmap mBitmap) {
	// Log.d("setbitmap", "");
	// Message msg = new Message();
	// msg = mHandler.obtainMessage(1, mBitmap);
	// mHandler.sendMessage(msg);
	// }
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		findViews();
		setting.timeStart();
		mSc = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				myService = null;
				Log.d("mSc", "service disconnected");
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d("mSc", "service connected");
				myService = ((LocalBinder) service).getService();

			}
		};
		IntentFilter filter = new IntentFilter("com.example.receiver.msg2");
		this.registerReceiver(receiver, filter);
	}

	private void findViews() {
		frame = (FrameLayout) findViewById(R.id.frame);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		iv = (ImageView) findViewById(R.id.imageButton1);
		tv = new TextView(this);
		dv = new DrawingView(this, setting);
		pv = new PaintView(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		iv.setOnClickListener(this);
		frame.addView(pv);
		frame.addView(dv);
		frame.addView(tv);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Log.d("onClick", "");
		if (v == btn1) {
			dv.setColor(Color.RED);
			Intent intent = new Intent("com.example.receiver.msg2");
			intent.putExtra("msg", "RED");
			this.sendBroadcast(intent);
		} else if (v == btn2) {
			dv.setColor(Color.GREEN);
			Intent intent = new Intent("com.example.receiver.msg2");
			intent.putExtra("msg", "GREEN");
			this.sendBroadcast(intent);
			// if (myService != null)
			// myService.sayHelloWorld();
		} else if (v == btn3) {
			dv.setColor(Color.BLUE);
			Intent intent = new Intent("com.example.receiver.msg2");
			intent.putExtra("msg", "BLUE");
			this.sendBroadcast(intent);

			// if (myService != null)
			// this.unbindService(mSc);
		} else if (v == btn4) {
			dv.setColor(Color.BLACK);
			Intent intent = new Intent("com.example.receiver.msg2");
			intent.putExtra("msg", "BLACK");
			this.sendBroadcast(intent);
			// Intent service = new Intent(this, LocalService.class);
			// this.bindService(service, mSc, Context.BIND_AUTO_CREATE);
		} else if (v == iv) {
			Log.d("ClearBitmap", "");
			dv.ClearBitmap();
			setting.setSecCounter(0);
			Intent intent = new Intent("com.example.receiver.msg1");
			intent.putExtra("msg", "MSG1");
			this.sendBroadcast(intent);
		}
	}

	class MainReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == "com.example.receiver.msg2") {
				Log.d("MainReceiver", intent.getStringExtra("msg"));
				Toast.makeText(context, intent.getStringExtra("msg"),
						Toast.LENGTH_SHORT).show();
			}// TODO Auto-generated method stub

		}

	}
}
