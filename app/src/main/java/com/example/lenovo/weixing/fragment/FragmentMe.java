package com.example.lenovo.weixing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.weixing.Activity.PersonalImformationActivity;
import com.example.lenovo.weixing.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentMe extends Fragment {

    @Bind(R.id.my_head_icon)
    ImageView myHeadIcon;
    @Bind(R.id.my_nickname)
    TextView myNickname;
    @Bind(R.id.my_id)
    TextView myId;
    @Bind(R.id.my_QR_code)
    ImageButton myQRCode;
    @Bind(R.id.my_setting_layout)
    LinearLayout mySettingLayout;
    @Bind(R.id.album)
    LinearLayout album;
    @Bind(R.id.collection)
    LinearLayout collection;
    @Bind(R.id.wallet)
    LinearLayout wallet;
    @Bind(R.id.card_bag)
    LinearLayout cardBag;
    @Bind(R.id.expression)
    LinearLayout expression;
    @Bind(R.id.setting)
    LinearLayout setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.my_QR_code, R.id.my_setting_layout, R.id.album, R.id.collection, R.id.wallet, R.id.card_bag, R.id.expression, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_QR_code:
                Toast.makeText(getContext(),"erweima",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_setting_layout:
                Intent intent1=new Intent(getContext(), PersonalImformationActivity.class);
                startActivity(intent1);
                break;
            case R.id.album:
                break;
            case R.id.collection:
                break;
            case R.id.wallet:
                break;
            case R.id.card_bag:
                break;
            case R.id.expression:
                break;
            case R.id.setting:
                break;
        }
    }
}
