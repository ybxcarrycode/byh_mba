package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class EvaluateTeacherListBean {


    /**
     * returnCode : 0
     * data : [{"content":"fasdfasd","isPraise":"0","praiseCount":"0","nickName":"普通用户1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"33分钟前","commentId":"6"},{"content":"ä½ å¥½","isPraise":"0","praiseCount":"0","nickName":"普通用户1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"33分钟前","commentId":"5"},{"content":"ä½ å¥½","isPraise":"0","praiseCount":"0","nickName":"普通用户1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"34分钟前","commentId":"4"},{"content":"ä½ å¥½","isPraise":"0","praiseCount":"0","nickName":"普通用户1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"35分钟前","commentId":"3"},{"content":"老师很不漂亮","isPraise":"1","praiseCount":"2","nickName":"学长1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-02-08 16:32:08","commentId":"2"},{"content":"老师很漂亮","isPraise":"0","praiseCount":"1","nickName":"学长1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-02-06 17:32:08","commentId":"1"}]
     * returnMsg : 操作成功
     */

    private String returnCode;
    private String returnMsg;
    /**
     * content : fasdfasd
     * isPraise : 0
     * praiseCount : 0
     * nickName : 普通用户1
     * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
     * createDate : 33分钟前
     * commentId : 6
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
        private String content;
        private String isPraise;
        private String praiseCount;
        private String nickName;
        private String headPic;
        private String createDate;
        private String commentId;
        private String personalSign;

        public String getPersonalSign() {
            return personalSign;
        }

        public void setPersonalSign(String personalSign) {
            this.personalSign = personalSign;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(String isPraise) {
            this.isPraise = isPraise;
        }

        public String getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(String praiseCount) {
            this.praiseCount = praiseCount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }
    }
}
