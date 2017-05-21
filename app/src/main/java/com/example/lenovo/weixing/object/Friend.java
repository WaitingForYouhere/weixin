package com.example.lenovo.weixing.object;

/**
 * Created by lenovo on 2017/3/22.
 */

public class Friend {
    private int imageId;
    private String nickname;
    private String latestMessage;
    private String chatTime;



    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getImageId() {
        return imageId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public String getChatTime() {
        return chatTime;
    }
}
