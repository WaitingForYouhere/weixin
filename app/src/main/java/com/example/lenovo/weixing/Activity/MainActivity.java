package com.example.lenovo.weixing.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.weixing.MyView.HintPopupWindow;
import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.fragment.FragmentAddress;
import com.example.lenovo.weixing.fragment.FragmentChat;
import com.example.lenovo.weixing.fragment.FragmentFind;
import com.example.lenovo.weixing.fragment.FragmentMe;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton search;
    private ImageButton more;
    private RadioGroup tabMenu;
    private RadioButton tabChat;
    private RadioButton tabAddress;
    private RadioButton tabFind;
    private RadioButton tabMe;

    private FragmentChat chat;
    private FragmentAddress contact;
    private FragmentFind find;
    private FragmentMe me;
    private HintPopupWindow hintPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabMenu= (RadioGroup) findViewById(R.id.tab_menu);
        tabChat= (RadioButton) findViewById(R.id.bt_weixin);
        tabAddress= (RadioButton) findViewById(R.id.bt_tongxunlu);
        tabFind= (RadioButton) findViewById(R.id.bt_faxian);
        tabMe= (RadioButton) findViewById(R.id.bt_wo);

        Drawable drawableWeiHui = getResources().getDrawable(R.drawable.tab_selector_weixing);
        drawableWeiHui.setBounds(0, 0, 130, 110);//第一0是距左右边距离，第二0是距上下边距离，第三长度,第四宽度
        tabChat.setCompoundDrawables(null, drawableWeiHui, null, null);//只放上面

         Drawable drawableAddress = getResources().getDrawable(R.drawable.tab_selector_tongxunlu);
        drawableAddress.setBounds(0, 0, 130, 110);
        tabAddress.setCompoundDrawables(null, drawableAddress, null, null);//只放上面

         Drawable drawableFind = getResources().getDrawable(R.drawable.tab_selector_faxian);
        drawableFind.setBounds(0, 0, 130, 110);
        tabFind.setCompoundDrawables(null, drawableFind, null, null);//只放上面

        Drawable drawableMe = getResources().getDrawable(R.drawable.tab_selector_wo);
        drawableMe.setBounds(0, 0, 130, 110);
        tabMe.setCompoundDrawables(null, drawableMe, null, null);//只放上面

        search= (ImageButton) findViewById(R.id.seatch);
        more= (ImageButton) findViewById(R.id.add);
        search.setOnClickListener(this);
        more.setOnClickListener(this);


        initMenu();
        initContentView();

    }

    private void initMenu() {
        ArrayList<String> strList=new ArrayList<>();
        strList.add("发起群聊");
        strList.add("添加朋友");
        strList.add("扫一扫");
        strList.add("收付款");
        strList.add("帮助与反馈");

        ArrayList<View.OnClickListener> clickList=new ArrayList<>();
        View.OnClickListener clickListener1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点击了“发起群聊”按键！",Toast.LENGTH_SHORT).show();
            }
        };
        clickList.add(clickListener1);
        View.OnClickListener clickListener2=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点击了“添加朋友”按键！",Toast.LENGTH_SHORT).show();
            }
        };
        clickList.add(clickListener2);
        View.OnClickListener clickListener3=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点击了“扫一扫”按键！",Toast.LENGTH_SHORT).show();
            }
        };
        clickList.add(clickListener3);
        View.OnClickListener clickListener4=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点击了“收付款”按键！",Toast.LENGTH_SHORT).show();
            }
        };
        clickList.add(clickListener4);
        View.OnClickListener clickListener5=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点击了“帮助与反馈”按键！",Toast.LENGTH_SHORT).show();
            }
        };
        clickList.add(clickListener5);

        hintPopupWindow=new HintPopupWindow(this,strList,clickList);
    }

    private void initContentView() {
        chat=new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,chat).commit();

        tabMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bt_weixin:
                        chat=new FragmentChat();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,chat).commit();

                        break;
                    case R.id.bt_tongxunlu:
                        contact=new FragmentAddress();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,contact).commit();
                        break;
                    case R.id.bt_faxian:
                        find=new FragmentFind();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,find).commit();
                        break;
                    case R.id.bt_wo:
                        me=new FragmentMe();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,me).commit();
                        break;
                    default:break;
                }
            }
        });
    }



    private void setOverflowShowingAlways()
    {
        try
        {
            // true if a permanent menu key is present, false otherwise.
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seatch:
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                hintPopupWindow.showPopupWindow(v);
                break;
        }
    }
}
