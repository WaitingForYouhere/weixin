package com.example.lenovo.weixing.MyView;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.weixing.MyInterface.RecordStrategy;
import com.example.lenovo.weixing.R;

/**
 * Created by lenovo on 2017/4/10.
 */

public class RecordButton extends Button {

    private static final int MIN_RECORD_TIME = 1; // 最短录音时间，单位秒
    private static final int RECORD_OFF = 0; // 不在录音
    private static final int RECORD_ON = 1; // 正在录音

    private Dialog mRecordDialog;
    private RecordStrategy mAudioRecorder;
    private Thread mRecordThread;
    private RecordListener listener;

    private int recordState = 0; // 录音状态
    private float recodeLength = 0.0f; // 录音时长，如果录音时间太短则录音失败
    private double voiceValue = 0.0; // 录音的音量值
    private boolean isCanceled = false; // 是否取消录音
    private float downY;

    private TextView dialogTextView;
    private ImageView dialogImg;
    private Context mContext;
    public RecordButton(Context context) {
        super(context);
        Init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);

    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context);

    }

    private void Init(Context context) {
        mContext=context;
        this.setText("按住说话");
    }
    public void setAudioRecord(RecordStrategy record) {
        this.mAudioRecorder = record;
    }

    public void setRecordListener(RecordListener listener) {
        this.listener = listener;
    }
    public int getRecordlength(){
        return (int) recodeLength;
    }
    // 录音时间太短时提示
    private void showWarnToast(String toastText) {
        Toast toast = new Toast(mContext);
        View warnView = LayoutInflater.from(mContext).inflate(
                R.layout.toast_tooshort_dialog, null);
        toast.setView(warnView);
        toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间
        toast.show();
    }
    private void showVoiceDialog(int flag) {
        if (mRecordDialog == null) {
            mRecordDialog = new Dialog(mContext, R.style.Dialogstyle);
            mRecordDialog.setContentView(R.layout.record_layout);
            dialogImg = (ImageView) mRecordDialog
                    .findViewById(R.id.record_dialog_img);
            dialogTextView = (TextView) mRecordDialog
                    .findViewById(R.id.record_dialog_test);
        }
        switch (flag) {
            case 1:
                dialogImg.setImageResource(R.drawable.cancelsend);
                dialogTextView.setText("松开手指,取消发送");
                this.setText("松开手指,取消发送");
                break;

            default:
                dialogImg.setImageResource(R.drawable.soundlevel1);
                dialogTextView.setText("手指上滑，取消发送");
                this.setText("松开结束");
                break;
        }
        dialogTextView.setTextSize(14);
        mRecordDialog.show();
    }
    float starty;


    private void startRecordThread() {
        mRecordThread=new Thread(RecorderTheard);
        mRecordThread.start();
    }
    private void changeSoundLevelImage(){
        if(voiceValue<1000.0){
            dialogImg.setImageResource(R.drawable.soundlevel1);
        }else if(voiceValue>1000.0&&voiceValue<3000.0){
            dialogImg.setImageResource(R.drawable.soundlevel2);
        }else if(voiceValue>3000.0&&voiceValue<5000.0){
            dialogImg.setImageResource(R.drawable.soundlevel3);
        }else if(voiceValue>5000.0&&voiceValue<7000.0){
            dialogImg.setImageResource(R.drawable.soundlevel4);
        }else if(voiceValue>7000.0&&voiceValue<9000.0){
            dialogImg.setImageResource(R.drawable.soundlevel5);
        }else if(voiceValue>9000.0&&voiceValue<11000.0){
            dialogImg.setImageResource(R.drawable.soundlevel6);
        }else if(voiceValue>11000.0){
            dialogImg.setImageResource(R.drawable.soundlevel7);
        }
    }

    private Runnable RecorderTheard=new Runnable() {
        @Override
        public void run() {
            recodeLength=0.0f;
            while (recordState==RECORD_ON){
                try {
                    Thread.sleep(100);
                    recodeLength+=0.1;
                    if(!isCanceled){
                        voiceValue=mAudioRecorder.getAmplitude();
                        recordHandler.sendEmptyMessage(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler recordHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            changeSoundLevelImage();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (recordState != RECORD_ON) {
                    showVoiceDialog(0);
                    starty = event.getY();
                    if (mAudioRecorder != null) {
                        mAudioRecorder.ready();
                        recordState = RECORD_ON;
                        mAudioRecorder.start();
                        startRecordThread();
                    }
                }


                break;
            case MotionEvent.ACTION_MOVE:
                if (starty - event.getY() > 50) {
                    showVoiceDialog(1);
                    isCanceled = true;
                } else if (starty - event.getY() < 20) {
                    showVoiceDialog(0);
                    isCanceled = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (recordState == RECORD_ON) {
                    recordState = RECORD_OFF;
                    if (mRecordDialog.isShowing()) {
                        mRecordDialog.dismiss();
                    }
                    mAudioRecorder.stop();
                    mRecordThread.interrupt();

                voiceValue = 0.0;
                if (isCanceled) {
                    mAudioRecorder.deleteOldFile();
                } else {
                    if (recodeLength < MIN_RECORD_TIME) {
                        showWarnToast("时间太短 ,录音失败");
                        mAudioRecorder.deleteOldFile();
                    } else {
                        if (listener != null) {
                            listener.recordEnd(mAudioRecorder.getFilePath());
                        }
                    }
                }
                isCanceled = false;
                this.setText("按住说话");
            }
                break;
        }
        return true;
    }

    public interface RecordListener {
        public void recordEnd(String filePath);
    }
}
