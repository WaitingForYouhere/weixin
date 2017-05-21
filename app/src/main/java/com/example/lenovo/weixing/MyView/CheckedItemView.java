package com.example.lenovo.weixing.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.example.lenovo.weixing.object.Man;

import java.io.InputStream;

/**
 * Created by lenovo on 2017/5/2.
 */

public class CheckedItemView extends CheckedTextView {

    //自定义选择item

    private int[] idList;
    private String[] strList;
    private Man man;
    Bitmap bitmap;
    Paint mPaint;
    CheckedTextView cktv;

    public CheckedItemView(Context context) {
        this(context,null);
    }

    public CheckedItemView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CheckedItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        cktv=new CheckedTextView(getContext());
        mPaint = new Paint();
        if(man!=null){
            InputStream is = getResources().openRawResource(man.getImageId());
            bitmap = BitmapFactory.decodeStream(is);
            cktv.setText(man.getStr());
        }


    }






    public void setMan(Man man){
        this.man=man;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 40, 40, mPaint);
        canvas.translate(60,0);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
