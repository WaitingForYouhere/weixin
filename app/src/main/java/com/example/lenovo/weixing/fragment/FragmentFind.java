package com.example.lenovo.weixing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.weixing.Activity.MomentsActivity;
import com.example.lenovo.weixing.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentFind extends Fragment {

    @Bind(R.id.last_icon)
    ImageView lastIcon;
    @Bind(R.id.neight_redpoint)
    ImageView neightRedpoint;
    @Bind(R.id.friend_neighbor)
    LinearLayout friendNeighbor;
    @Bind(R.id.hava_a_scan)
    LinearLayout havaAScan;
    @Bind(R.id.have_a_shake)
    LinearLayout haveAShake;
    @Bind(R.id.neight_man)
    LinearLayout neightMan;
    @Bind(R.id.bottle)
    LinearLayout bottle;
    @Bind(R.id.shopping)
    LinearLayout shopping;
    @Bind(R.id.game)
    LinearLayout game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.friend_neighbor, R.id.hava_a_scan, R.id.have_a_shake, R.id.neight_man, R.id.bottle, R.id.shopping, R.id.game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friend_neighbor:
                Intent intent=new Intent(getContext(), MomentsActivity.class);
                startActivity(intent);
                break;
            case R.id.hava_a_scan:
                break;
            case R.id.have_a_shake:
                break;
            case R.id.neight_man:
                break;
            case R.id.bottle:
                break;
            case R.id.shopping:
                break;
            case R.id.game:
                break;
        }
    }
}
