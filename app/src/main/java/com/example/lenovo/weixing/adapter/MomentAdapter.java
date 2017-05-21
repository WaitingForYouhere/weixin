package com.example.lenovo.weixing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.object.Man;
import com.example.lenovo.weixing.object.Moment;

import java.util.List;

/**
 * Created by lenovo on 2017/5/2.
 */

public class MomentAdapter extends ArrayAdapter<Man> {
    private int resourceId;
    private List<Moment> manList;

    public MomentAdapter(Context context, int resource,List<Moment> data) {
        super(context, resource);
        this.resourceId=resource;
        manList=data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        view= LayoutInflater.from(getContext()).inflate(R.layout.item_checked_view,null);

        return view;
    }
    class ViewHolder{
        ImageView icon;
        TextView name;
        TextView content;
        TextView all_or_part;
    }
}
