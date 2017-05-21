package com.example.lenovo.weixing.Activity;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.weixing.R;

/**
 * Created by lenovo on 2017/5/1.
 */

public class ImageBaseActivity extends Activity {
    private AlertDialog progressDialog;
    public TextView showProgressDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view= View.inflate(this, R.layout.image_record_dialog_layout,null);
        builder.setView(view);
        ProgressBar pb_loading= (ProgressBar) view.findViewById(R.id.pb_loading);
        TextView tv_hint= (TextView) view.findViewById(R.id.tv_hint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pb_loading.setIndeterminateTintList(ContextCompat.getColorStateList(this, R.color.dialog_pro_color));
        }
        tv_hint.setText("视频编译中");
        builder.create();
        builder.show();
        return tv_hint;
    }

    public void closeProgressDialog(){
        try {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
