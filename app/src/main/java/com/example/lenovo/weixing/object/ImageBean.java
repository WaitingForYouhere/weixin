package com.example.lenovo.weixing.object;
import com.yzs.imageshowpickerview.ImageShowPickerBean;
/**
 * Created by lenovo on 2017/4/28.
 */
public class ImageBean extends ImageShowPickerBean {
    public ImageBean(String url) {
        this.url = url;
    }

    public ImageBean(int resId) {
        this.resId = resId;
    }

    private String url;

    private int resId;


    @Override
    public String setImageShowPickerUrl() {
        return url;
    }

    @Override
    public int setImageShowPickerDelRes() {
        return resId;
    }
}