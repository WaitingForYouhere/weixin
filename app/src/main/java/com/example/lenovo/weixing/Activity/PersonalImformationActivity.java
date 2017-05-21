package com.example.lenovo.weixing.Activity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.weixing.MyView.TopBarBaseActivity;
import com.example.lenovo.weixing.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalImformationActivity extends TopBarBaseActivity {


    @Bind(R.id.head_icon_setting)
    ImageButton headIconSetting;
    @Bind(R.id.my_nickname_setting)
    TextView myNicknameSetting;
    @Bind(R.id.my_nickname_layout)
    LinearLayout myNicknameLayout;
    @Bind(R.id.my_id_setting)
    TextView myIdSetting;
    @Bind(R.id.my_id_layout)
    LinearLayout myIdLayout;
    @Bind(R.id.my_QR_code)
    ImageView myQRCode;
    @Bind(R.id.my_QR_code_layout)
    LinearLayout myQRCodeLayout;
    @Bind(R.id.my_address_setting)
    TextView myAddressSetting;
    @Bind(R.id.my_address_layout)
    LinearLayout myAddressLayout;
    @Bind(R.id.my_sex_setting)
    TextView mySexSetting;
    @Bind(R.id.my_sex_layout)
    LinearLayout mySexLayout;
    @Bind(R.id.my_area_setting)
    TextView myAreaSetting;
    @Bind(R.id.my_area_layout)
    LinearLayout myAreaLayout;
    @Bind(R.id.my_signature_setting)
    TextView mySignatureSetting;
    @Bind(R.id.my_signature_layout)
    LinearLayout mySignatureLayout;
    @Bind(R.id.my_icon_layout)
    RelativeLayout myIconLayout;


    private static final int CHOOSE_PHOTO=3;

    @Override
    protected void init(Bundle savedInstanceState) {
        setTopLeftButton(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(PersonalImformationActivity.this, "返回", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        setTitle("个人信息");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_imformation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.my_icon_layout,R.id.head_icon_setting, R.id.my_nickname_layout, R.id.my_id_layout, R.id.my_QR_code_layout, R.id.my_address_layout, R.id.my_sex_layout, R.id.my_area_layout, R.id.my_signature_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_icon_setting:
                break;
            case R.id.my_icon_layout:
                Intent intent1=new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("image/*");
                startActivityForResult(intent1,CHOOSE_PHOTO);
                break;
            case R.id.my_nickname_layout:
                break;
            case R.id.my_id_layout:
                break;
            case R.id.my_QR_code_layout:
                break;
            case R.id.my_address_layout:
                break;
            case R.id.my_sex_layout:
                break;
            case R.id.my_area_layout:
                break;
            case R.id.my_signature_layout:
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

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
                String selection= MediaStore.Images.Media._ID+"="+id;
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
        Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
        headIconSetting.setImageBitmap(bitmap);
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
}
