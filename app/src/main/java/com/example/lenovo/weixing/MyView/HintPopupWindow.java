package com.example.lenovo.weixing.MyView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.weixing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/18.
 * 弹出动画的popupwindow
 */
public class HintPopupWindow {

    private Activity activity;
    private WindowManager.LayoutParams params;
    private boolean isShow;
    private WindowManager windowManager;
    private ViewGroup rootView;
    private ViewGroup linearLayout;

    private int[] imaList={R.drawable.newgroup,R.drawable.addfriend,R.drawable.scan,R.drawable.getmoney,R.drawable.help};

    private final int animDuration = 250;//动画执行时间
    private boolean isAniming;//动画是否在执行

    /**
     * @param contentList 点击item的内容文字
     * @param clickList 点击item的事件
     * 文字和click事件的list是对应绑定的
     */
    public HintPopupWindow(Activity activity, List<String> contentList, List<View.OnClickListener> clickList){

        this.activity = activity;
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);

        initLayout(contentList, clickList);
    }

    /**
     * @param contentList 点击item内容的文字
     * @param clickList 点击item的事件
     */
    public void initLayout(List<String> contentList, List<View.OnClickListener> clickList){

        //这是根布局
        rootView = (ViewGroup) View.inflate(activity, R.layout.item_root_hintpopupwindow, null);
        linearLayout = (ViewGroup) rootView.findViewById(R.id.linearLayout);

        //格式化点击item, 将文字和click事件一一绑定上去
        List<View> list = new ArrayList<>();
        for(int x=0; x<contentList.size(); x++){
            View view = View.inflate(activity, R.layout.item_hint_popupwindow, null);
            ImageView imageView= (ImageView) view.findViewById(R.id.iv_content);
            TextView textView = (TextView) view.findViewById(R.id.tv_content);
            View v_line = view.findViewById(R.id.v_line);
            imageView.setImageResource(imaList[x]);
            textView.setText(contentList.get(x));
            linearLayout.addView(view);
            list.add(view);
            if(x == 0){
                v_line.setVisibility(View.INVISIBLE);
            }else{
                v_line.setVisibility(View.VISIBLE);
            }
        }
        for (int x=0; x<list.size(); x++){
            list.get(x).setOnClickListener(clickList.get(x));
        }

        //这里给你根布局设置背景透明, 为的是让他看起来和activity的布局一样
        params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.RGBA_8888;//背景透明
        params.gravity = Gravity.LEFT | Gravity.TOP;

        //当点击根布局时, 隐藏
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupWindow();
            }
        });

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //如果是显示状态那么隐藏视图
                if(keyCode == KeyEvent.KEYCODE_BACK && isShow) dismissPopupWindow();
                return isShow;
            }
        });
    }

    /**
     * 弹出选项弹窗
     * @param locationView 默认在该view的下方弹出, 和popupWindow类似
     */
    public void showPopupWindow(View locationView){
        Log.i("Log.i", "showPopupWindow: "+isAniming);
        if(!isAniming) {
            isAniming = true;
            isShow=true;
            try {
                //这个步骤是得到该view相对于屏幕的坐标, 注意不是相对于父布局哦!
                int[] arr = new int[2];
                locationView.getLocationOnScreen(arr);

                linearLayout.measure(0, 0);
                Rect frame = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);//得到状态栏高度
                float x = arr[0] + locationView.getWidth() - linearLayout.getMeasuredWidth();
                float y = ((View) locationView.getParent()).getHeight();
                linearLayout.setX(x);
                linearLayout.setY(y);



                //这里就是使用WindowManager直接将我们处理好的view添加到屏幕最前端
                windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                windowManager.addView(rootView, params);

                //这一步就是有回弹效果的弹出动画, 我用属性动画写的, 很简单
                showAnim(linearLayout, 0, 1, animDuration, true);

                //视图被弹出来时得到焦点, 否则就捕获不到Touch事件
                rootView.setFocusable(true);
                rootView.setFocusableInTouchMode(true);
                rootView.requestFocus();
                rootView.requestFocusFromTouch();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }








    public void dismissPopupWindow(){
        Log.i("Log.i", "dismissPopupWindow: "+isAniming);
        if(!isAniming) {
            isAniming = true;
            isShow = false;
            goneAnim(linearLayout, 0.95f, 1, animDuration / 3, true);
        }
    }

    public WindowManager.LayoutParams getLayoutParams(){
        return params;
    }

    public ViewGroup getLayout(){
        return linearLayout;
    }

    /**
     * popupwindow是否是显示状态
     */
    public boolean isShow(){
        return isShow;
    }

    private void alphaAnim(final View view, int start, int end, int duration){

        ValueAnimator va = ValueAnimator.ofFloat(start, end).setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value);
            }
        });
        va.start();
    }

    private void showAnim(final View view, float start, final float end, int duration, final boolean isWhile) {

        ValueAnimator va = ValueAnimator.ofFloat(start, end).setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setPivotX(view.getWidth());
                view.setPivotY(0);
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isWhile) {
                    showAnim(view, end, 0.95f, animDuration / 3, false);
                }else{
                    isAniming = false;
                }
            }
        });
        va.start();
    }

    public void goneAnim(final View view, float start, final float end, int duration, final boolean isWhile){

        ValueAnimator va = ValueAnimator.ofFloat(start, end).setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setPivotX(view.getWidth());
                view.setPivotY(0);
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(isWhile){
//                    alphaAnim(rootView, 1, 0, animDuration);
                    goneAnim(view, end, 0f, animDuration, false);
                }else{
                    try {
                        windowManager.removeViewImmediate(rootView);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    isAniming = false;
                }
            }
        });
        va.start();
    }
}