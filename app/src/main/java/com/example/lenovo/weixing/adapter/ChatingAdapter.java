package com.example.lenovo.weixing.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.object.Messagedata;

import java.util.List;

/**
 * Created by lenovo on 2017/3/28.
 */

public class ChatingAdapter extends ArrayAdapter<Messagedata> implements View.OnClickListener{
    private Messagedata message;
    public List<Messagedata> mData;
    private int state;
    private int resourceId;
    private OnSoundClickListener onSoundClickListener;
    private MediaPlayer mp=new MediaPlayer();


    public ChatingAdapter(Context context, int resource, List<Messagedata> data) {
        super(context, resource, data);
        this.resourceId=resource;
        this.mData=data;

    }

    @Override
    public int getPosition(Messagedata item) {
        int position=super.getPosition(item);
        state=position;
        return position;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }

    @Override
    public int getItemViewType(int position) {
        message=mData.get(position);
        return message.getType();
    }
private int count=0;
    public View InitView(View convertView,int type,ViewHolder holder){
        count++;
        Log.e("ChatingAdapter",String.valueOf(count));
        if (type==1||type==3){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_my_saying,null);
            holder.icon= (ImageView) convertView.findViewById(R.id.my_icon);
            holder.sayingtext= (TextView) convertView.findViewById(R.id.my_text);
            holder.mSoundLayout= (LinearLayout) convertView.findViewById(R.id.my_sound_layout);
            holder.soundlength=(TextView)convertView.findViewById(R.id.my_sound_length);
            holder.soundbutton= (Button) convertView.findViewById(R.id.my_sound);

        }
        else{
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_others_sound,null);
            holder.icon= (ImageView) convertView.findViewById(R.id.others_icon);
            holder.sayingtext= (TextView) convertView.findViewById(R.id.others_text);
            holder.mSoundLayout= (LinearLayout) convertView.findViewById(R.id.others_sound_layout);
            holder.soundlength=(TextView)convertView.findViewById(R.id.others_sound_length);
            holder.soundbutton= (Button) convertView.findViewById(R.id.others_sound);

        }
        convertView.setTag(holder);
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type=getItemViewType(position);
        if (convertView==null){
            holder=new ViewHolder();
            convertView=InitView(convertView,type,holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
            if((type==1&&holder.type!=1&&holder.type!=3)||(type==2&&holder.type!=2&&holder.type!=4)){
                holder=new ViewHolder();
                convertView=InitView(convertView,type,holder);
            }
        }
        holder.type=type;
        holder.icon.setImageResource(mData.get(position).getImageViewId());
        if(type==1||type==2){
            holder.mSoundLayout.setVisibility(View.GONE);
            holder.sayingtext.setVisibility(View.VISIBLE);
        }
        else{
            holder.mSoundLayout.setVisibility(View.VISIBLE);
            holder.sayingtext.setVisibility(View.GONE);
        }

        if(type==1||type==2) {
            holder.sayingtext.setText(mData.get(position).getText());
        }else {
            holder.soundlength.setText(String.valueOf(mData.get(position).getSoundLength())+"\"");
            holder.soundbutton.setTag(position);
            holder.soundbutton.setOnClickListener(this);
        }
        return convertView;
    }



    @Override
    public void onClick(View v) {
        OnSoundClickListener listener=onSoundClickListener;
        listener.onSoundButtonClickListener(v,mData);

    }


    class ViewHolder{
        int type;
        ImageView icon;
        TextView sayingtext;
        TextView soundlength;
        Button soundbutton;
        LinearLayout mSoundLayout;
    }

    public void setOnSoundClickListener(OnSoundClickListener onSoundClickListener){
        this.onSoundClickListener=onSoundClickListener;
    }
    public interface OnSoundClickListener{
        public void onSoundButtonClickListener(View v,List<Messagedata> mData);
    }
}
