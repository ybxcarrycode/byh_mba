package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BbsDetailBean {


    /**
     * returnCode : 0
     * data : {"classify":"高考答疑","isFocus":"1","focusNum":"1","nickName":"徐老师","personalSign":"导读导读收到的","subjectContent":"装饰趋势真心实意朋友全心全意","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-04-28 17:19:23","replyNum":"3"}
     * returnMsg : 操作成功
     */

    private String returnCode;
    /**
     * classify : 高考答疑
     * isFocus : 1
     * focusNum : 1
     * nickName : 徐老师
     * personalSign : 导读导读收到的
     * subjectContent : 装饰趋势真心实意朋友全心全意
     * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
     * createDate : 2017-04-28 17:19:23
     * replyNum : 3
     */

    private DataBean data;
    private String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public static class DataBean {
        private String classify;
        private String isFocus;
        private String focusNum;
        private String nickName;
        private String personalSign;
        private String subjectContent;
        private String headPic;
        private String createDate;
        private String replyNum;
        private List<String> imageUrls;

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public String getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(String isFocus) {
            this.isFocus = isFocus;
        }

        public String getFocusNum() {
            return focusNum;
        }

        public void setFocusNum(String focusNum) {
            this.focusNum = focusNum;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPersonalSign() {
            return personalSign;
        }

        public void setPersonalSign(String personalSign) {
            this.personalSign = personalSign;
        }

        public String getSubjectContent() {
            return subjectContent;
        }

        public void setSubjectContent(String subjectContent) {
            this.subjectContent = subjectContent;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(String replyNum) {
            this.replyNum = replyNum;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
}
