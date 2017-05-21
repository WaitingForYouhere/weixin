package com.example.lenovo.weixing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.lenovo.weixing.R;

public class Searchbyclassify extends AppCompatActivity implements View.OnClickListener{
    private ImageButton bt_back;
    private ImageButton bt_search;
    private EditText input;
    private Button weekly_selected;
    private Button shared_music;
    private Button my_reading;

    private LinearLayout find_friend;
    private LinearLayout find_article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbyclassify);
        String from=getIntent().getStringExtra("button");
        initView();
        setpage(from);

    }

    private void initView() {
        bt_back= (ImageButton) findViewById(R.id.back1);
        bt_search= (ImageButton) findViewById(R.id.searchby_input1);

        weekly_selected= (Button) findViewById(R.id.weekly_selected);
        shared_music= (Button) findViewById(R.id.shared_music);
        my_reading= (Button) findViewById(R.id.my_reading);
        input= (EditText) findViewById(R.id.classify);
        find_friend = (LinearLayout) findViewById(R.id.friend_button);
        find_article = (LinearLayout) findViewById(R.id.article_button);

        bt_back.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        weekly_selected.setOnClickListener(this);
        shared_music.setOnClickListener(this);
        my_reading.setOnClickListener(this);
    }

    public void setpage(String from) {
        switch (from){
            case "bt_1":
                input.setHint("搜索朋友圈");
                find_friend.setVisibility(View.VISIBLE);
                break;
            case "bt_2":
                find_article.setVisibility(View.VISIBLE);
                input.setHint("搜索文章");
                break;
            case "bt_3":
                input.setHint("搜索公众号");
                break;
            case "bt_4":
                input.setHint("搜索小说");
                break;
            case "bt_5":
                input.setHint("搜索音乐");
                break;
            case "bt_6":
                input.setHint("搜索表情");
                break;
            case "haha":
                input.setText("有没有");
                break;

        }

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Searchbyclassify.class);
        switch (v.getId()){
            case R.id.back1:
                this.finish();
                break;
            case R.id.searchby_input1:

                break;
            case R.id.weekly_selected:

                break;
            case R.id.shared_music:
                break;


            case R.id.my_reading:
                intent.putExtra("button","bt_1");
                startActivity(intent);
                break;

        }
    }
}
