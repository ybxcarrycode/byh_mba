package com.xszj.mba.bean;

import java.io.Serializable;

/**
 * Created by Ybx on 2017/2/6.
 */

public class AboutSchoolDetBean implements Serializable {
    /**
     * returnCode : 0
     * data : {"recruitStudentsNumber":"2000","signupTimesImg":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png","majorDirection":"专业方向","pupilsClassify":"生源分类","academyName":"北京大学","isAdvanceInterview":"1","learnCost":"20000","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png","projectCategory":"项目列表"}
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
         * recruitStudentsNumber : 2000
         * signupTimesImg : http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png
         * majorDirection : 专业方向
         * pupilsClassify : 生源分类
         * academyName : 北京大学
         * isAdvanceInterview : 1
         * learnCost : 20000
         * academyLogo : http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png
         * projectCategory : 项目列表
         */

        private String recruitStudentsNumber;
        private String signupTimesImg;
        private String majorDirection;
        private String pupilsClassify;
        private String academyName;
        private String isAdvanceInterview;
        private String learnCost;
        private String academyLogo;
        private String projectCategory;

        public String getRecruitStudentsNumber() {
            return recruitStudentsNumber;
        }

        public void setRecruitStudentsNumber(String recruitStudentsNumber) {
            this.recruitStudentsNumber = recruitStudentsNumber;
        }

        public String getSignupTimesImg() {
            return signupTimesImg;
        }

        public void setSignupTimesImg(String signupTimesImg) {
            this.signupTimesImg = signupTimesImg;
        }

        public String getMajorDirection() {
            return majorDirection;
        }

        public void setMajorDirection(String majorDirection) {
            this.majorDirection = majorDirection;
        }

        public String getPupilsClassify() {
            return pupilsClassify;
        }

        public void setPupilsClassify(String pupilsClassify) {
            this.pupilsClassify = pupilsClassify;
        }

        public String getAcademyName() {
            return academyName;
        }

        public void setAcademyName(String academyName) {
            this.academyName = academyName;
        }

        public String getIsAdvanceInterview() {
            return isAdvanceInterview;
        }

        public void setIsAdvanceInterview(String isAdvanceInterview) {
            this.isAdvanceInterview = isAdvanceInterview;
        }

        public String getLearnCost() {
            return learnCost;
        }

        public void setLearnCost(String learnCost) {
            this.learnCost = learnCost;
        }

        public String getAcademyLogo() {
            return academyLogo;
        }

        public void setAcademyLogo(String academyLogo) {
            this.academyLogo = academyLogo;
        }

        public String getProjectCategory() {
            return projectCategory;
        }

        public void setProjectCategory(String projectCategory) {
            this.projectCategory = projectCategory;
        }
    }
}
