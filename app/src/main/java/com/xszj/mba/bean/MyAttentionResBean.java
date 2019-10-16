package com.xszj.mba.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ybx on 2017/2/13.
 */

public class MyAttentionResBean implements Serializable {

    /**
     * returnCode : 0
     * data : {"expertList":[{"focusNum":"3","nickName":"普通用户1","teacherTitle":"MBA资深专家","academyName":"","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":"ysx1480408356164test0000","userType":"12"}]}
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

        private String pageCount;
        private List<AboutTechUpperBean> expertList;

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public List<AboutTechUpperBean> getExpertList() {
            return expertList;
        }

        public void setExpertList(List<AboutTechUpperBean> expertList) {
            this.expertList = expertList;
        }
    }
}
