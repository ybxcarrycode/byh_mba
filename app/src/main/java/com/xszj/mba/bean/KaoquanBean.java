package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class KaoquanBean {


    /**
     * returnCode : 0
     * data : [{"isFocus":"0","focusNum":"0","nickName":"44444444","subjectClassify":"101","subjectId":"10005","personalSign":"","subjectContent":"Dsfgsgsdgfsfdgsggsgsfgsdg","headPic":"http://byh-mobile.oss-cn-shanghai.aliyuncs.com/byh-mobile/2017/04/20/15/1492672745055.jpg","createDate":"2017-04-28 18:12:28","replyNum":"0"},{"isFocus":"0","focusNum":"0","nickName":"徐老师","subjectClassify":"102","subjectId":"10004","personalSign":"导读导读收到的","subjectContent":"突然曲曲折折如iOS有些人","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-04-28 17:20:09","replyNum":"0"},{"isFocus":"0","focusNum":"0","nickName":"徐老师","subjectClassify":"101","subjectId":"10003","personalSign":"导读导读收到的","subjectContent":"装饰趋势真心实意朋友全心全意","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-04-28 17:19:23","replyNum":"0"},{"isFocus":"0","focusNum":"0","nickName":"徐老师","subjectClassify":"101","subjectId":"10002","personalSign":"导读导读收到的","subjectContent":"sdfasdfasd","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-04-28 17:05:21","replyNum":"0"},{"isFocus":"0","focusNum":"0","nickName":"徐老师","subjectClassify":"101","subjectId":"10001","personalSign":"导读导读收到的","subjectContent":"sdfasdfasd","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-04-28 17:04:37","replyNum":"0"},{"isFocus":"0","focusNum":"0","nickName":"徐老师","subjectClassify":"101","subjectId":"10000","personalSign":"导读导读收到的","subjectContent":"sdfasdfasd","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-04-28 17:02:16","replyNum":"0"}]
     * returnMsg : 操作成功
     */

    private String returnCode;
    private String returnMsg;
    /**
     * isFocus : 0
     * focusNum : 0
     * nickName : 44444444
     * subjectClassify : 101
     * subjectId : 10005
     * personalSign :
     * subjectContent : Dsfgsgsdgfsfdgsggsgsfgsdg
     * headPic : http://byh-mobile.oss-cn-shanghai.aliyuncs.com/byh-mobile/2017/04/20/15/1492672745055.jpg
     * createDate : 2017-04-28 18:12:28
     * replyNum : 0
     */

    private List<DataBean> data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String isFocus;
        private String focusNum;
        private String nickName;
        private String subjectClassify;
        private String subjectId;
        private String personalSign;
        private String subjectContent;
        private String headPic;
        private String createDate;
        private String replyNum;
        private List<String> imageUrls;

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

        public String getSubjectClassify() {
            return subjectClassify;
        }

        public void setSubjectClassify(String subjectClassify) {
            this.subjectClassify = subjectClassify;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
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
