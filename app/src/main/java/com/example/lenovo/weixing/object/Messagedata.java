package com.example.lenovo.weixing.object;

/**
 * Created by lenovo on 2017/3/28.
 */

public class Messagedata {
    private String text;
    private int type;
    private int imageViewId;
    private int soundLength;
    private int soundId;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public Messagedata(String text, int type, int imageViewId,int soundLength,int soundId) {
//        this.text = text;
//        this.type = type;
//        this.imageViewId = imageViewId;
//        this.soundLength=soundLength;
//    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public int getSoundLength() {
        return soundLength;
    }

    public void setSoundLength(int soundLength) {
        this.soundLength = soundLength;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }
}
