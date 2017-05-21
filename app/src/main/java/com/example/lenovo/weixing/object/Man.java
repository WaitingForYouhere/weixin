package com.example.lenovo.weixing.object;

/**
 * Created by lenovo on 2017/5/2.
 */

public class Man {
    int ImageId;
    String str;

    public Man(int imageId, String str) {
        this.ImageId = imageId;
        this.str = str;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        this.ImageId = imageId;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
