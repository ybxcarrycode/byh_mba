package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BbsDetailCommentListBean {

    /**
     * data : [{"createDate":"2017-05-02 10:12:50","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","nickName":"徐老师","personalSign":"导读导读收到的","replyContent":"sfasd","replyUserId":"1"}]
     * returnCode : 0
     * returnMsg : 操作成功
     */

    private String returnCode;
    private String returnMsg;
    /**
     * createDate : 2017-05-02 10:12:50
     * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
     * nickName : 徐老师
     * personalSign : 导读导读收到的
     * replyContent : sfasd
     * replyUserId : 1
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
        private String createDate;
        private String headPic;
        private String nickName;
        private String personalSign;
        private String replyContent;
        private String replyUserId;
        private String replyId;
        private String userType;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
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

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(String replyUserId) {
            this.replyUserId = replyUserId;
        }
    }
}
