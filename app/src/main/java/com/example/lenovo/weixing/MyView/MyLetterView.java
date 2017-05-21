package com.example.lenovo.weixing.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lenovo on 2017/4/15.
 */

public class MyLetterView extends View {
    OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private String[] content={"↑","☆","A","B","C","D","E","F","G","H","I","J"
            ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};

    private boolean showbg=false;
    Paint paint = new Paint();
    public MyLetterView(Context context) {
        super(context);
    }

    public MyLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showbg){
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        int height=getHeight();
        int width=getWidth();
        int singleheight=height/content.length;
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        paint.setTextSize(58);
        for (int i = 0; i < content.length; i++) {
            float xPos = width/2  - paint.measureText(content[i])/2;
            float yPos = singleheight * i + singleheight;
            canvas.drawText(content[i], xPos, yPos, paint);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        OnTouchingLetterChangedListener listener=onTouchingLetterChangedListener;
        final float y = event.getY();
        final int c = (int) (y/getHeight()*content.length);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                showbg=true;
                if(listener!=null){
                    if(c>=0&&c<content.length){
                        listener.onTouchingLetterChanged(content[c]);
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if(c>=0&&c<content.length){
                    listener.onTouchingLetterChanged(content[c]);
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                showbg=false;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangeListener(OnTouchingLetterChangedListener onTouchingLetterChangeListener){
        this.onTouchingLetterChangedListener=onTouchingLetterChangeListener;
    }

    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);
    }
}
