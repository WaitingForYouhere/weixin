package com.example.lenovo.weixing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.weixing.MyView.TopBarBaseActivity;
import com.example.lenovo.weixing.R;

import java.util.Arrays;
import java.util.List;

public class WhoCanSeeActivity extends TopBarBaseActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton selectBt;
    private String selectStr;
    private Bundle bundle;
    int resultcode;
    List<String> list= Arrays.asList("公开","私密","部分可见","不给谁看");
    int[] idList={R.id.radioButton1,R.id.radioButton2,R.id.radioButton3,R.id.radioButton4};
    @Override
    protected void init(Bundle savedInstanceState) {
        final Intent intent=new Intent();
        bundle=new Bundle();
        setTitle("谁可以看");
        setTopLeftButton(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick() {
                finish();

            }
        });
        setRightButton("完成 ",new OnClickListener() {
            @Override
            public void onClick() {
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        selectStr=getIntent().getStringExtra("status");
        Log.e("whocansee",selectStr);
        int index=list.indexOf(selectStr);
        radioButton= (RadioButton) findViewById(idList[index]);
        radioButton.setChecked(true);

        radioGroup= (RadioGroup) findViewById(R.id.radio_group_wocansee);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectBt= (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                selectStr=selectBt.getText().toString();
                bundle.putString("result", selectStr);

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_who_can_see;
    }
}
