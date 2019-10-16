package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class ExpertAcademyListBean {

    /**
     * returnCode : 0
     * data : [{"academyId":"1","academyName":"北京大学"},{"academyId":"2","academyName":"清华大学"},{"academyId":"3","academyName":"四川大学"},{"academyId":"4","academyName":"南开大学"},{"academyId":"5","academyName":"人民大学"}]
     * returnMsg : 操作成功
     */

    private String returnCode;
    private String returnMsg;
    /**
     * academyId : 1
     * academyName : 北京大学
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
        private String academyId;
        private String academyName;

        public String getAcademyId() {
            return academyId;
        }

        public void setAcademyId(String academyId) {
            this.academyId = academyId;
        }

        public String getAcademyName() {
            return academyName;
        }

        public void setAcademyName(String academyName) {
            this.academyName = academyName;
        }
    }
}
