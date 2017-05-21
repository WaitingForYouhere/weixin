package com.example.lenovo.weixing.MyView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lenovo.weixing.R;

/**
 * Created by lenovo on 2017/4/27.
 * 顶部标题栏的封装，使用只需几行代码即可定制
 */

public abstract class TopBarBaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout viewContent;
    Button btTitle;
    TextView tvTitle;
    OnClickListener onClickListenerTopLeft;
    OnClickListener onClickListenerTopRight;
    OnClickListener onClickListenerRight;
    Menu mMenu;
    int menuResId;
    String menuStr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        viewContent= (FrameLayout) findViewById(R.id.viewContent);
        btTitle= (Button) findViewById(R.id.btTitle);
        tvTitle= (TextView) findViewById(R.id.tvTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater.from(TopBarBaseActivity.this).inflate(getContentView(),viewContent);
        init(savedInstanceState);
        btTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenerRight.onClick();
            }
        });
    }
    protected void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
    }
    protected void setTopLeftButton(int resId,OnClickListener  onClickListener){
        toolbar.setNavigationIcon(resId);
        this.onClickListenerTopLeft=onClickListener;
    }

    protected void setTopRightButton(String menuStr,OnClickListener onClickListener){
        this.menuStr=menuStr;
        this.onClickListenerTopRight=onClickListener;
    }
    protected void setTopRightButton(String menuStr,int menuResId,OnClickListener onClickListener){
        this.menuStr=menuStr;
        this.menuResId=menuResId;
        this.onClickListenerTopRight=onClickListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu=menu;
        if(menuResId!=0||!TextUtils.isEmpty(menuStr)){
            getMenuInflater().inflate(R.menu.menu_activtiy_base_top_bar,menu);
        }
        return true;
    }
    public void setRightButton(String btStr,OnClickListener onClickListener){
        this.onClickListenerRight=onClickListener;
        if(!TextUtils.isEmpty(btStr)){
            btTitle.setText(btStr);
        btTitle.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menuResId!=0){
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if(!TextUtils.isEmpty(menuStr)){
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onClickListenerTopLeft.onClick();
        }
        if(item.getItemId()==R.id.menu_1){
            onClickListenerTopRight.onClick();
        }
        return true;
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getContentView();

    public interface OnClickListener{
        void onClick();
    }
}
