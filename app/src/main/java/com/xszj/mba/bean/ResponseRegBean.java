package com.xszj.mba.bean;

/**
 * Created by Administrator on 2016/7/1.
 */
public class ResponseRegBean {

    /**
     * returnCode : 0
     * data : {"returnCode":"0","phone":"18500513364","nickName":"qwwq","userId":"14","imUserId":"ysx1486525800014test0000","userType":"12","imUserPwd":"d5315d5f82b92d085d3ce028483cdfa9"}
     * returnMsg : 操作成功
     */

    private String returnCode;
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
        /**
         * returnCode : 0
         * phone : 18500513364
         * nickName : qwwq
         * userId : 14
         * imUserId : ysx1486525800014test0000
         * userType : 12
         * imUserPwd : d5315d5f82b92d085d3ce028483cdfa9
         */

        private String returnCode;
        private String phone;
        private String nickName;
        private String userId;
        private String imUserId;
        private String userType;
        private String imUserPwd;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getImUserId() {
            return imUserId;
        }

        public void setImUserId(String imUserId) {
            this.imUserId = imUserId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getImUserPwd() {
            return imUserPwd;
        }

        public void setImUserPwd(String imUserPwd) {
            this.imUserPwd = imUserPwd;
        }
    }
}
