package com.xszj.mba.bean;

import java.io.Serializable;

/**
 * Created by Ybx on 2017/2/8.
 */

public class MyMsgBean implements Serializable {

    /**
     * messageUrl : https://www.baidu.com
     * messageReceiveUser : 1
     * messageContent : 提前面试攻略技巧
     * messageReadDate : 2017-02-06 18:55:21
     * messageType : 3
     * messageId : 8
     * messageStatus : 0
     */

    private String messageUrl;
    private String messageReceiveUser;
    private String messageContent;
    private String messageReadDate;
    private String messageType;
    private String messageId;
    private String messageStatus;

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String getMessageReceiveUser() {
        return messageReceiveUser;
    }

    public void setMessageReceiveUser(String messageReceiveUser) {
        this.messageReceiveUser = messageReceiveUser;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageReadDate() {
        return messageReadDate;
    }

    public void setMessageReadDate(String messageReadDate) {
        this.messageReadDate = messageReadDate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

}
