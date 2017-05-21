package com.example.lenovo.weixing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.object.Friend;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class ChatListAdapter extends ArrayAdapter<Friend> {

    private int resourceId;
    private List<Friend> list;

    public ChatListAdapter(Context context, int resourceId, List<Friend> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
        this.list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Friend friend=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.image= (ImageView) convertView.findViewById(R.id.image_icon);
            viewHolder.nickname= (TextView) convertView.findViewById(R.id.nickname);
            viewHolder.latestChat= (TextView) convertView.findViewById(R.id.latest);
            viewHolder.chatTime= (TextView) convertView.findViewById(R.id.chat_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(friend.getImageId());
        viewHolder.nickname.setText(friend.getNickname());
        viewHolder.latestChat.setText(friend.getLatestMessage());
        viewHolder.chatTime.setText(friend.getChatTime());
        return convertView;
    }

    class ViewHolder{
        private ImageView image;
        private TextView nickname;
        private TextView latestChat;
        private TextView chatTime;
    }
}
