package com.example.paintdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class PaintView extends View {

    private Bitmap  mBitmap;
    private int h,w;
    private Canvas  mCanvas;
    
	public PaintView(Context context) {
		super(context);
	}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// �e��
		Paint paint = new Paint();

		// �e������
		mCanvas.drawColor(Color.WHITE);
		canvas.drawColor(Color.WHITE);
		// �e����(��)
		paint.setColor(Color.GRAY);
		// �e��
		mCanvas.drawCircle(160, 160, 150, paint);
		canvas.drawCircle(160, 160, 150, paint);
		// �e��
		paint.setColor(Color.BLUE);
		Rect rect = new Rect(100, 110, 120, 130);
		mCanvas.drawRect(rect, paint);
		canvas.drawRect(rect, paint);
		// �e�ꨤ��
		paint.setColor(Color.GREEN);
		RectF rectf = new RectF(200, 110, 220, 130);
		mCanvas.drawRoundRect(rectf, 7, 7, paint);
		canvas.drawRoundRect(rectf, 7, 7, paint);
		// �e��
		paint.setColor(Color.YELLOW);
		RectF oval = new RectF(50, 150, 270, 250);
		mCanvas.drawArc(oval, 180, -180, true, paint);
		canvas.drawArc(oval, 180, -180, true, paint);
		// �e�r
		paint.setColor(Color.BLACK);
		mCanvas.drawText("Andy", 160, 350, paint);
		canvas.drawArc(oval, 180, -180, true, paint);
		
	}
	
	public Bitmap getBitmap(){
		return mBitmap;
	}
}
