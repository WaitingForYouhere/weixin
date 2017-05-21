package com.example.lenovo.weixing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.weixing.MyView.TopBarBaseActivity;
import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.adapter.MomentAdapter;
import com.example.lenovo.weixing.object.Moment;

import java.util.ArrayList;
import java.util.List;

public class MomentsActivity extends TopBarBaseActivity {


    private ListView listview;
    TextView myNickname;
    private MomentAdapter mAdapter;
    private Moment moment;
    private List<Moment> manList=new ArrayList<Moment>();

    @Override
    protected int getContentView() {
        return R.layout.activity_moments;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //顶部标题栏
        setTitle("朋友圈");
        setTopLeftButton(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        setTopRightButton("Button", R.drawable.white_camera,new OnClickListener() {
            @Override
            public void onClick() {
                Intent intent=new Intent(MomentsActivity.this, ShareToFriend.class);
                startActivity(intent);
            }
        });
        initView();



    }

    private void initView() {
        TextView myNickname = (TextView)findViewById(R.id.my_nickname_inMoment);
        TextPaint tp = myNickname.getPaint();
        tp.setFakeBoldText(true);


        listview= (ListView) findViewById(R.id.list_moment);

        for (int i = 0; i < 3; i++) {
            moment=new Moment();
            moment.setIconId(R.drawable.queen);
            moment.setName("cst");
            moment.setContent(getString(R.string.content2));
            manList.add(moment);
        }
        mAdapter=new MomentAdapter(this,R.layout.item_checked_view,manList);
        listview.setAdapter(mAdapter);
    }
}
