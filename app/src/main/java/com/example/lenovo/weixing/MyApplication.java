package com.example.lenovo.weixing;

import android.app.Application;
import android.os.Environment;

import com.yixia.camera.VCamera;

import java.io.File;

/**
 * Created by lenovo on 2017/5/1.
 */

public class MyApplication extends Application {

    public static String VIDEO_PATH= Environment.getExternalStorageDirectory().getPath()+"/TestRecord/video/";
    @Override
    public void onCreate() {

        VIDEO_PATH+=String.valueOf(System.currentTimeMillis());
        File file=new File(VIDEO_PATH);
        if(!file.exists())file.mkdirs();
        VCamera.setVideoCachePath(VIDEO_PATH);
        VCamera.setDebugMode(true);
        VCamera.initialize(this);
    }
}
