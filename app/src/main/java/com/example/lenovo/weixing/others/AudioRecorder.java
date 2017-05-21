package com.example.lenovo.weixing.others;

import android.media.MediaRecorder;
import android.os.Environment;

import com.example.lenovo.weixing.MyInterface.RecordStrategy;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017/4/11.
 */

public class AudioRecorder implements RecordStrategy{

    private MediaRecorder recorder;
    private String filename;
    private String fileFolder= Environment.getExternalStorageDirectory().getPath()+"/TestRecord";
    private boolean isRecording = false;
    private String filePath;
    @Override
    public void ready() {
        File file=new File(fileFolder);
        if(!file.exists()){
            file.mkdir();
        }
        filename=getCurrentDate();
        recorder=new MediaRecorder();
        filePath=fileFolder+"/"+filename+".amr";
        recorder.setOutputFile(filePath);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
    @Override
    public void start() {
        if(!isRecording){
            try {
                recorder.prepare();
                recorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
            isRecording=true;
        }

    }

    @Override
    public void stop() {
        if(isRecording){
            recorder.stop();
            recorder.release();
            isRecording=false;
        }
    }

    @Override
    public void deleteOldFile() {

        File file=new File(filePath);
        file.deleteOnExit();
    }

    //获取音量
    @Override
    public double getAmplitude() {
        if (!isRecording) {
            return 0;
        }
        return recorder.getMaxAmplitude();
    }

    @Override
    public String getFilePath() {
        return filePath;
    }
}
