package com.example.lenovo.weixing.Activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.weixing.MyApplication;
import com.example.lenovo.weixing.MyView.ImageAndDespairRecordButton;
import com.example.lenovo.weixing.R;
import com.yixia.camera.MediaRecorderNative;
import com.yixia.camera.VCamera;
import com.yixia.camera.model.MediaObject;
import com.yixia.videoeditor.adapter.UtilityAdapter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ImageAndDespairActivity extends ImageBaseActivity implements SurfaceHolder.Callback{

    private ImageAndDespairRecordButton iadrButton;
    private int maxDuration = 3000;
    private boolean recordedOver;
    private MediaObject mMediaObject;
    private MediaRecorderNative mMediaRecorder;

    private static final int REQUEST_KEY = 100;
    private ImageView iv_finish;
    private ImageView iv_back;
    private float dp100;
    private TextView tv_hint;
    private float backX = -1;
    private TextView textView;
    private SurfaceView surfaceview;
    private SurfaceHolder mSurfaceHolder;

    private Camera mCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_and_despair);
        iadrButton= (ImageAndDespairRecordButton) findViewById(R.id.iadr_button);
        surfaceview= (SurfaceView) findViewById(R.id.surface_view);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_hint = (TextView) findViewById(R.id.tv_hint);


        mSurfaceHolder=surfaceview.getHolder();
        mSurfaceHolder.addCallback(this);

        initMediaRecorder();

        dp100 = getResources().getDimension(R.dimen.dp100);



        iadrButton.setMax(maxDuration);
        iadrButton.setOnGestureListener(new ImageAndDespairRecordButton.OnGestureListener() {
            @Override
            public void onLongClick() {
                mMediaRecorder.startRecord();
                myHandler.sendEmptyMessageDelayed(0,50);
            }

            @Override
            public void onClick() {
                startAnim();
            }

            @Override
            public void onLift() {
                recordedOver = true;
                videoFinsh();
            }

            @Override
            public void onOver() {
                recordedOver = true;
            }
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);

    }


    //通过调用物理相机自定义相机
    public void capture(View view){

    }

    private Camera getCamera(){
        Camera camera;
        try {
            camera=Camera.open();
        } catch (Exception e) {
            camera=null;
            Log.e("Image",1+"");
            e.printStackTrace();
        }

        return camera;
    }

    private void setPreview(Camera camera,SurfaceHolder holder){
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            Log.e("Image",2+"");
            e.printStackTrace();
        }
    }

    private void releaseCamera(){
        if(mCamera!=null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("Image",22+"");
        setPreview(mCamera,mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        Log.e("Image",23+"");
        setPreview(mCamera,holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }





    @Override
    protected void onResume() {
        super.onResume();
        mCamera=getCamera();
        if(mCamera==null){
            mCamera=getCamera();
            if(mSurfaceHolder!=null){
                Log.e("Image",21+"");
                setPreview(mCamera,mSurfaceHolder);
            }
        }
        mMediaRecorder.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();

        mMediaRecorder.stopPreview();
    }


    private void videoFinsh() {
//        textView = showProgressDialog();
        mMediaRecorder.stopRecord();
        //开始合成视频, 异步
        mMediaRecorder.startEncoding();
    }
    float lastValue;
    private void startAnim() {

        iadrButton.setVisibility(View.GONE);
        ValueAnimator va = ValueAnimator.ofFloat(0, dp100).setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                float dis = value-lastValue;
                iv_back.setX(iv_back.getX()-dis);
                iv_finish.setX(iv_finish.getX()+dis);
                lastValue = value;
            }
        });
        va.start();
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(!recordedOver){
                iadrButton.setProgress(mMediaObject.getDuration());
                myHandler.sendEmptyMessageDelayed(0, 50);
                tv_hint.setVisibility(View.GONE);
            }
        }
    };
    private void initMediaRecorder() {
        mMediaRecorder=new MediaRecorderNative();
//        mMediaRecorder.setOnEncodeListener(this);
        String key=String.valueOf(System.currentTimeMillis());
        //设置路径
        mMediaObject=mMediaRecorder.setOutputDirectory(key, VCamera.getVideoCachePath());
        //设置文件预览源
        mMediaRecorder.setSurfaceHolder(surfaceview.getHolder());
        mMediaRecorder.prepare();


        UtilityAdapter.freeFilterParser();
        UtilityAdapter.initFilterParser();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_KEY){
                LinkedList<MediaObject.MediaPart> medaParts = mMediaObject.getMedaParts();
                for (MediaObject.MediaPart part : medaParts){
                    mMediaObject.removePart(part, true);
                }
                deleteDir(MyApplication.VIDEO_PATH);
            }
        }
    }

    /**
     * 删除文件夹下所有文件
     */
    public static void deleteDir(String dirPath){

        File dir = new File(dirPath);
        if(dir.exists() && dir.isDirectory()){
            File[] files = dir.listFiles();
            for(File f: files){
                deleteDir(f.getAbsolutePath());
            }
        }else if(dir.exists()) {
            dir.delete();
        }
    }



    @Override
    public void onBackPressed() {
        closeProgressDialog();
        if(iadrButton.getVisibility() != View.VISIBLE) {
            initMediaRecorderState();
            LinkedList<MediaObject.MediaPart> medaParts = mMediaObject.getMedaParts();
            for (MediaObject.MediaPart part : medaParts) {
                mMediaObject.removePart(part, true);
            }
            mMediaRecorder.startPreview();
        }else{
            super.onBackPressed();
        }
    }
    /**
     * 初始化视频拍摄状态
     */
    private void initMediaRecorderState(){

//        vv_play.setVisibility(View.GONE);
//        vv_play.pause();

        iv_back.setX(backX);
        iv_finish.setX(backX);

        tv_hint.setVisibility(View.VISIBLE);
        iadrButton.setVisibility(View.VISIBLE);

        lastValue = 0;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if(backX == -1) {
            backX = iv_back.getX();
        }
    }
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(!recordedOver){
                iadrButton.setProgress(mMediaObject.getDuration());
                myHandler.sendEmptyMessageDelayed(0, 50);

            }
        }
    };



//    @Override
//    public void onEncodeStart() {
//        Log.i("Log.i", "onEncodeStart");
//    }
//
//    @Override
//    public void onEncodeProgress(int progress) {
//        if(textView!=null){
//            textView.setText("视频编译中"+progress+"%    ");
//        }
//    }
//
//    @Override
//    public void onEncodeComplete() {
//        closeProgressDialog();
//        final String path = mMediaObject.getOutputTempVideoPath();
//        if (!TextUtils.isEmpty(path)) {
//            iv_finish.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(ImageAndDespairActivity.this, EditVideoActivity.class);
//                    intent.putExtra("path", path);
//                    startActivityForResult(intent, REQUEST_KEY);
//                    initMediaRecorderState();
//                }
//            });
//
//            vv_play.setVisibility(View.VISIBLE);
//            vv_play.setVideoPath(path);
//            vv_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    vv_play.setLooping(true);
//                    vv_play.start();
//                }
//            });
//            vv_play.start();
//
//            recordedOver = false;
//            startAnim();
//
//        }
//    }
//
//    @Override
//    public void onEncodeError() {
//        Log.i("Log.i", "onEncodeError");
//    }

}
