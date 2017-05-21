package com.example.lenovo.weixing.Activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.weixing.MyView.TopBarBaseActivity;
import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.object.ImageBean;
import com.example.lenovo.weixing.others.Loader;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yzs.imageshowpickerview.ImageShowPickerBean;
import com.yzs.imageshowpickerview.ImageShowPickerListener;
import com.yzs.imageshowpickerview.ImageShowPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShareToFriend extends TopBarBaseActivity implements View.OnClickListener,View.OnTouchListener{

    private static final int TAKE_PHOTO=2;
    private static final int CHOOSE_PHOTO=3;
    private static final int CHOOSE_STATUS=4;


    private LinearLayout layout_share_to_friend;
    private LinearLayout nowLocationLayout;
    private LinearLayout whoCanSeeLayout;
    private LinearLayout remindWholayout;
    private EditText editText;
    private TextView statusOfWhoCanSee;
    private ImageButton btStar;
    List<String> statuslist = Arrays.asList("公开", "私密", "部分可见", "不给谁看");

    private boolean isStarBling = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getContentView() {
        return R.layout.activity_share_to_friend;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTopLeftButton(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(ShareToFriend.this, "返回", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        setRightButton("发送 ", new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(ShareToFriend.this, "发送", Toast.LENGTH_SHORT).show();

            }
        });
        initPicker();
        initPage();
    }

    private void initPage() {
        layout_share_to_friend = (LinearLayout) findViewById(R.id.layout_share_to_friend);
        nowLocationLayout = (LinearLayout) findViewById(R.id.my_location_layout_sharepage);
        whoCanSeeLayout = (LinearLayout) findViewById(R.id.who_can_see_sharepage);
        remindWholayout = (LinearLayout) findViewById(R.id.remind_who_sharepage);
        statusOfWhoCanSee = (TextView) findViewById(R.id.status_wocansee);
        editText = (EditText) findViewById(R.id.my_thought_now);
        btStar = (ImageButton) findViewById(R.id.share_star);
        btStar.setOnClickListener(this);

        nowLocationLayout.setOnClickListener(this);
        whoCanSeeLayout.setOnClickListener(this);
        remindWholayout.setOnClickListener(this);


        layout_share_to_friend.requestFocus();
        layout_share_to_friend.setOnTouchListener(this);


    }
    private List<ImageBean> imagelist;
    private ImageShowPickerView pickerView;
    private void initPicker() {
        pickerView= (ImageShowPickerView) findViewById(R.id.it_picker_view);
        imagelist = getData();
        pickerView.setImageLoaderInterface(new Loader());
        pickerView.setNewData(imagelist);
        pickerView.setShowAnim(true);
        pickerView.setPickerListener(new ImageShowPickerListener() {
            @Override
            public void addOnClickListener(int remainNum) {
            AlertDialog.Builder dialog=new AlertDialog.Builder(ShareToFriend.this).setItems(
                        new String[] { "拍摄", "从相册选择" },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intent1=new Intent(ShareToFriend.this,ImageAndDespairActivity.class);
                                        startActivityForResult(intent1,TAKE_PHOTO);
                                        break;
                                    case 1:
                                        Intent intent=new Intent("android.intent.action.GET_CONTENT");
                                        intent.setType("image/*");
                                        startActivityForResult(intent,CHOOSE_PHOTO);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                dialog.show();

//                Toast.makeText(ShareToFriend.this, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
//                ImageBean imageBean = new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg");
//                imagelist.add(imageBean);
//                pickerView.addData(imageBean);
            }

            @Override
            public void picOnClickListener(List<ImageShowPickerBean> list, int position, int remainNum) {
                Toast.makeText(ShareToFriend.this, list.size() + "========" + position + "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void delOnClickListener(int position, int remainNum) {
                imagelist.remove(position);
                Toast.makeText(ShareToFriend.this, "delOnClickListenerremainNum" + remainNum, Toast.LENGTH_SHORT).show();
            }
        });
        pickerView.show();

    }

    private List<ImageBean> getData() {
        List<ImageBean> list = new ArrayList<>();
        list.add(new ImageBean("http://img2.imgtn.bdimg.com/it/u=819201812,3553302270&fm=214&gp=0.jpg"));
        list.add(new ImageBean("http://img02.tooopen.com/images/20140504/sy_60294738471.jpg"));
        list.add(new ImageBean("http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg"));
        list.add(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
        list.add(new ImageBean("http://img05.tooopen.com/images/20150531/tooopen_sy_127457023651.jpg"));
        return list;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_STATUS:
                if(resultCode==RESULT_OK){
                    Bundle b = data.getExtras(); //data为B中回传的Intent
                    String str = b.getString("result");//str即为回传的值
                    statusOfWhoCanSee.setText(str);
                }
                break;

            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                    break;

        }
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(
                    uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }
        displayImage(imagePath);
    }
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        Log.e("share",imagePath);
         ImageBean imageBean = new ImageBean(imagePath);
         imagelist.add(imageBean);
          pickerView.addData(imageBean);
    }

    private String getImagePath(Uri uri, String selection) {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_location_layout_sharepage:
                Toast.makeText(ShareToFriend.this, "现在的位置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.who_can_see_sharepage:
                Intent intent = new Intent(ShareToFriend.this, WhoCanSeeActivity.class);
                intent.putExtra("status", statusOfWhoCanSee.getText().toString());
                startActivityForResult(intent, CHOOSE_STATUS);
                Toast.makeText(ShareToFriend.this, statusOfWhoCanSee.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.remind_who_sharepage:
                Toast.makeText(ShareToFriend.this, "提醒谁来看", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_star:
                if (isStarBling) {
                    btStar.setBackgroundResource(R.drawable.zone_gray);
                    isStarBling = false;
                } else {
                    btStar.setBackgroundResource(R.drawable.zone_yellow);
                    isStarBling = true;
                }
                ;
                break;
        }
    }

//获取焦点的尝试（未成功），点击其他地方关闭输入法
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()!=R.id.my_thought_now){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
        switch (v.getId()){
            case R.id.layout_share_to_friend:break;
            case R.id.my_location_layout_sharepage:break;
            case R.id.who_can_see_sharepage:break;
            case R.id.remind_who_sharepage:break;
            case R.id.status_wocansee:break;
            case R.id.my_thought_now:break;
            case R.id.share_star:break;
            case R.id.it_picker_view:
                pickerView.setFocusable(true);
                pickerView.setFocusableInTouchMode(true);
                pickerView.requestFocus();
                break;
        }
        return false;
    }
}
