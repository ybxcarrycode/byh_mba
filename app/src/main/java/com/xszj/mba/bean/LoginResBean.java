package com.xszj.mba.bean;

import java.io.Serializable;

/**
 * Created by Ybx on 2017/2/9.
 */

public class LoginResBean implements Serializable {

    /**
     * returnCode : 0
     * data : {"returnCode":"0","phone":"18500513364","nickName":"qwwq","userId":"14","headPic":"","imUserId":"ysx1486525800014test0000","userType":"12","imUserPwd":"983C9DF163323791218F71CAC4B4414D"}
     * returnMsg : 操作成功
     */

    private String returnCode;
    private UserInfoBean data;
    private String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

}
