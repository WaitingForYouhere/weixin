package com.example.lenovo.weixing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.weixing.R;

import java.util.List;

/**
 * Created by lenovo on 2017/4/15.
 */

public class LetterAdapter extends ArrayAdapter<String> {
    private final List<String> mData;
    private final int resourceId;


    public LetterAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        this.resourceId=resource;
        this.mData=data;

    }

private TextView mTextView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.letter_item,null);
        mTextView= (TextView) view.findViewById(R.id.letter_text);
        mTextView.setText(mData.get(position));
        return view;
    }
}
