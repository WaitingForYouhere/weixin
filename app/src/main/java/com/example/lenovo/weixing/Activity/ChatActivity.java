package com.example.lenovo.weixing.Activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.weixing.MyView.RecordButton;
import com.example.lenovo.weixing.MyView.TopBarBaseActivity;
import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.adapter.ChatingAdapter;
import com.example.lenovo.weixing.object.Messagedata;
import com.example.lenovo.weixing.others.AudioRecorder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends TopBarBaseActivity implements RecordButton.RecordListener,
        View.OnClickListener,AdapterView.OnItemClickListener,ChatingAdapter.OnSoundClickListener {

    private int[] iconId=new int[]{R.drawable.king,R.drawable.queen};
    private int[] typeList=new int[]{1,2,3,4};
    private String[] soundList=new String[]{"李玉刚 - 刚好遇见你.mp3","赵雷 - 成都.mp3",
            "郁可唯 - 时间煮雨.mp3","朴树 - 平凡之路(电影版).mp3"};

    private ChatingAdapter adapter;
    private List<Messagedata> dataList=new ArrayList<Messagedata>();
    private Messagedata data;
    private boolean isKeyboard=true;
    private boolean isInMore=false;

    private TextView text_line;
    private ListView sayingList;
    private EditText input_message;
    private ImageButton bt_sound;
    private String fromWho;
    private LinearLayout activitychat;
    private LinearLayout kitlayout;
    private ImageButton bt_smile;
    private ImageButton bt_more;
    private ImageButton bt_personal_area;
    private ImageButton btchatmic;
    private ImageButton btchatcard;
    private ImageButton btchattransfer;
    private ImageButton btchatregbag;
    private ImageButton btchatlocation;
    private ImageButton btchatvidicon;
    private ImageButton btchatcamera;
    private ImageButton btchatphoto;
    private Button btsend;
    private RecordButton recordButton;

    private MediaPlayer mp;
    private int state;
    private AudioRecorder audioRecorder;
    InputMethodManager imm;

    private static final int CHOOSE_PHOTO=3;


    private LinearLayout kit_area;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        fromWho=getIntent().getStringExtra("nickname");
//        initView();
//        initData();
//        adapter=new ChatingAdapter(this,R.layout.item_others_sound,dataList);
//        sayingList.setAdapter(adapter);
//        adapter.setOnSoundClickListener(this);
//        sayingList.setOnItemClickListener(this);
//        imm = (InputMethodManager)ChatActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//    }


        //初始化页面
    @Override
    protected void init(Bundle savedInstanceState) {
        fromWho=getIntent().getStringExtra("nickname");
        initView();
        initData();
        adapter=new ChatingAdapter(this,R.layout.item_others_sound,dataList);
        sayingList.setAdapter(adapter);
        adapter.setOnSoundClickListener(this);
        sayingList.setOnItemClickListener(this);
        imm = (InputMethodManager)ChatActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

        //顶部标题栏
        setTitle("cst");
        setTopLeftButton(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(ChatActivity.this,"cst点击了返回按键",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        setTopRightButton("Button", R.drawable.mine,new OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(ChatActivity.this,"点击了右上角按钮",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

//    初始化listview数据
    private void initData() {
        for (int i = 0; i <10 ; i++) {
            data=new Messagedata();
            data.setText("你好");
            int type=i%4;
            data.setImageViewId(iconId[type%2]);
            data.setType(typeList[type]);
            data.setSoundLength(3+i);
            data.setPath(Environment.getExternalStorageDirectory().getPath()+"/TestRecord/"+soundList[type]);
            dataList.add(data);
        }
    }

//    初始化控件及其绑定
    private void initView() {
        input_message= (EditText) findViewById(R.id.input_message);
        kit_area= (LinearLayout) findViewById(R.id.kit_layout);
        activitychat = (LinearLayout) findViewById(R.id.activity_chat);
        kitlayout = (LinearLayout) findViewById(R.id.kit_layout);

        recordButton= (RecordButton) findViewById(R.id.press_to_say);
        text_line= (TextView) findViewById(R.id.text_line);
        sayingList= (ListView) findViewById(R.id.saying_list_chatting);
        btsend= (Button) findViewById(R.id.bt_send);
        bt_sound=(ImageButton) findViewById(R.id.sound_or_input);
        bt_smile=(ImageButton) findViewById(R.id.smile_face);
        bt_more=(ImageButton) findViewById(R.id.more_kit);
        btchatmic = (ImageButton) findViewById(R.id.bt_chat_mic);
        btchatcard = (ImageButton) findViewById(R.id.bt_chat_card);
        btchattransfer = (ImageButton) findViewById(R.id.bt_chat_transfer);
        btchatregbag = (ImageButton) findViewById(R.id.bt_chat_regbag);
        btchatlocation = (ImageButton) findViewById(R.id.bt_chat_location);
        btchatvidicon = (ImageButton) findViewById(R.id.bt_chat_vidicon);
        btchatcamera = (ImageButton) findViewById(R.id.bt_chat_camera);
        btchatphoto = (ImageButton) findViewById(R.id.bt_chat_photo);

        bt_sound.setOnClickListener(this);
        bt_smile.setOnClickListener(this);
        bt_more.setOnClickListener(this);
        btsend.setOnClickListener(this);
        btchatmic.setOnClickListener(this);
        btchatcard.setOnClickListener(this);
        btchattransfer.setOnClickListener(this);
        btchatregbag.setOnClickListener(this);
        btchatlocation.setOnClickListener(this);
        btchatvidicon.setOnClickListener(this);
        btchatcamera.setOnClickListener(this);
        btchatphoto.setOnClickListener(this);

        audioRecorder=new AudioRecorder();
        recordButton.setAudioRecord(audioRecorder);
        recordButton.setRecordListener(this);
        input_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(input_message.getText().toString().trim())){
                    btsend.setVisibility(View.GONE);
                    bt_more.setVisibility(View.VISIBLE);
                }
                else {
                    btsend.setVisibility(View.VISIBLE);
                    bt_more.setVisibility(View.GONE);

                }
            }
        });
    }

//聊天页面中各按键点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sound_or_input:
                bt_sound.requestFocus();
                kit_area.setVisibility(View.GONE);
                if(isKeyboard){
                    bt_sound.setBackground(getResources().getDrawable(R.drawable.keyboard,null));
                    input_message.setVisibility(View.GONE);
                    text_line.setVisibility(View.GONE);
                    recordButton.setVisibility(View.VISIBLE);
                    imm.hideSoftInputFromWindow(input_message.getWindowToken(),0);
                    isKeyboard=false;
                }else {
                    bt_sound.setFocusable(false);
                    text_line.setVisibility(View.GONE);
                    input_message.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.GONE);
                    bt_sound.setBackground(getResources().getDrawable(R.drawable.sound,null));
                    input_message.requestFocus();
                    imm.showSoftInput(input_message,InputMethodManager.SHOW_FORCED);

                    isKeyboard=true;
                }
                break;
            case R.id.smile_face:
                break;
            case R.id.more_kit:
                if(isInMore){
                    kit_area.setVisibility(View.GONE);
                    imm.showSoftInput(input_message,InputMethodManager.SHOW_FORCED);
                    input_message.requestFocus();
                    isInMore=false;
                }else {
                    kit_area.setVisibility(View.VISIBLE);
                    imm.hideSoftInputFromWindow(input_message.getWindowToken(),0);
                    isInMore=true;
                }
                break;
            case R.id.bt_send:
                data=new Messagedata();
                data.setText(input_message.getText().toString());
                data.setImageViewId(iconId[0]);
                data.setType(1);
                dataList.add(data);
                input_message.setText("");
                adapter.notifyDataSetChanged();
                sayingList.setSelection(dataList.size()-1);
                break;
            case R.id.bt_chat_photo:
                Intent intent1=new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("image/*");
                startActivityForResult(intent1,CHOOSE_PHOTO);
                break;
            case R.id.bt_chat_camera:
                break;
            case R.id.bt_chat_vidicon:
                break;
            case R.id.bt_chat_location:
                break;
            case R.id.bt_chat_regbag:
                break;
            case R.id.bt_chat_transfer:
                break;
            case R.id.bt_chat_card:
                break;
            case R.id.bt_chat_mic:
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
//从相册选择照片时处理返回的照片路径
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

    //显示返回路径对应的照片
    private void displayImage(String imagePath) {
        Log.e("share",imagePath);
//        ImageBean imageBean = new ImageBean(imagePath);
//        imagelist.add(imageBean);
//        pickerView.addData(imageBean);
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



    //listview中item点击事件
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"position="+position,Toast.LENGTH_SHORT).show();
    }

    //录音结束时调用往listview中添加新发送的消息
    @Override
    public void recordEnd(String filePath) {
        data=new Messagedata();
        data.setPath(audioRecorder.getFilePath());
        data.setSoundLength(recordButton.getRecordlength());
        data.setImageViewId(iconId[0]);
        data.setType(3);
        dataList.add(data);
        adapter.notifyDataSetChanged();
        sayingList.setSelection(dataList.size()-1);

    }
    //初始化媒体播放器
    private void initMediaPlayer(MediaPlayer mp, int state, List<Messagedata> mData) {
        try {
            File file=new File(mData.get(state).getPath());
            mp.setDataSource(file.getPath());
            Log.e("pathnow",file.getPath());
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //播放录音
    @Override
    public void onSoundButtonClickListener(View v, List<Messagedata> mData) {

        if(mp==null){

        mp=new MediaPlayer();}
        state= (int) v.getTag();
        Log.e("ChatActivity",mData.get(state).getPath());
        if(!mp.isPlaying()){
            initMediaPlayer(mp,state,mData);
            mp.start();
        }else {
            mp.stop();
            mp.reset();

        }
    }
}
