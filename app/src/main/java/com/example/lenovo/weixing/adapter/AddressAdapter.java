package com.example.lenovo.weixing.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.object.ContractsData;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2017/4/19.
 */

public class AddressAdapter extends ArrayAdapter<ContractsData> {

    private List<Character> name= Arrays.asList('A','B','C','D','E','F','G','H','I','J'
            ,'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','#');
    private boolean[] letterHead=new boolean[27];
    private ContractsData contractsData;
    private List<ContractsData> contractsList;
    private boolean hasSetType=false;
    public AddressAdapter(Context context, int resource, List<ContractsData> objects) {
        super(context, resource, objects);
        this.contractsList=objects;
        for (int i = 0; i < 27; i++) {
            letterHead[i]=false;
        }
    }
    private void setType() {
        for (int i = 0; i <contractsList.size(); i++) {
        ContractsData contData=contractsList.get(i);
        char head=contData.getName().substring(0,1).toCharArray()[0];
        if(Character.isLowerCase(head)){
            Character.toUpperCase(head);
        }
        if(!letterHead[name.indexOf(head)]){
            //还没显示letter
            letterHead[name.indexOf(head)]=true;
            contData.setType(true);
            contractsList.set(i,contData);
        }
        }
        hasSetType=true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(!hasSetType){
            //设置是否显示组头字母
            setType();
        }
        contractsData=contractsList.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_address,null);
            viewHolder=new ViewHolder();
            viewHolder.letterLayout= (LinearLayout) convertView.findViewById(R.id.letter_layout);
            viewHolder.firstLetter= (TextView) convertView.findViewById(R.id.first_letter);
            viewHolder.image= (ImageView) convertView.findViewById(R.id.address_pic);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.address_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Log.e("Addressadapter",contractsData.getName());
        if(contractsData.getType()){
            viewHolder.letterLayout.setVisibility(View.VISIBLE);
            viewHolder.firstLetter.setText(contractsData.getName().substring(0,1).toCharArray()[0]+"");
        }else {
            viewHolder.letterLayout.setVisibility(View.GONE);

        }
        viewHolder.image.setImageResource(contractsData.getResId());
        viewHolder.textView.setText(contractsData.getName());
        return convertView;
    }



    class ViewHolder{
        LinearLayout letterLayout;
        ImageView image;
        TextView textView;
        TextView firstLetter;
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = contractsList.get(i).getName().substring(0,1).toCharArray()[0]+"";
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }
}
