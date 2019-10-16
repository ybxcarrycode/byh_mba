package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class ExperNewBean {

    /**
     * returnCode : 0
     * data : {"pageCount":"1","expertList":[{"isFollow":"0","focusNum":"4","nickName":"学长1","teacherTitle":"MBA资深专家","userId":"2","academyName":"北京大学","personalSign":"导读导读收到的","admitStudentSchoolId":"1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""},{"isFollow":"0","focusNum":"0","nickName":"学长2","teacherTitle":"MBA资深专家","userId":"3","academyName":"清华大学","personalSign":"导读导读收到的","admitStudentSchoolId":"2","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""},{"isFollow":"0","focusNum":"0","nickName":"名校老师1","teacherTitle":"MBA资深专家","userId":"4","academyName":"北京大学","personalSign":"导读导读收到的","admitStudentSchoolId":"1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""},{"isFollow":"0","focusNum":"0","nickName":"名校老师2","teacherTitle":"MBA资深专家","userId":"5","academyName":"清华大学","personalSign":"导读导读收到的","admitStudentSchoolId":"2","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""}]}
     * returnMsg : 操作成功
     */

    private String returnCode;
    /**
     * pageCount : 1
     * expertList : [{"isFollow":"0","focusNum":"4","nickName":"学长1","teacherTitle":"MBA资深专家","userId":"2","academyName":"北京大学","personalSign":"导读导读收到的","admitStudentSchoolId":"1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""},{"isFollow":"0","focusNum":"0","nickName":"学长2","teacherTitle":"MBA资深专家","userId":"3","academyName":"清华大学","personalSign":"导读导读收到的","admitStudentSchoolId":"2","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""},{"isFollow":"0","focusNum":"0","nickName":"名校老师1","teacherTitle":"MBA资深专家","userId":"4","academyName":"北京大学","personalSign":"导读导读收到的","admitStudentSchoolId":"1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""},{"isFollow":"0","focusNum":"0","nickName":"名校老师2","teacherTitle":"MBA资深专家","userId":"5","academyName":"清华大学","personalSign":"导读导读收到的","admitStudentSchoolId":"2","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","imUser":""}]
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
