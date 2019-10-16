package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */

public class SchoolListBean {


    /**
     * returnCode : 0
     * data : {"famousList":[{"academyId":"1","recruitStudentsNumber":"2000","academyName":"北京大学","learnCost":"20000","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png"},{"academyId":"2","recruitStudentsNumber":"2000","academyName":"清华大学","learnCost":"20000","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png"}],"hotList":[{"academyId":"3","recruitStudentsNumber":"2000","academyName":"四川大学","learnCost":"20000","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png"},{"academyId":"4","recruitStudentsNumber":"2000","academyName":"南开大学","learnCost":"20000","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png"},{"academyId":"5","recruitStudentsNumber":"2000","academyName":"人民大学","learnCost":"","academyLogo":"http://www.gaokaopai.com/Public/Uploads/53477df2c4fba.jpg"}]}
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
         * academyId : 1
         * recruitStudentsNumber : 2000
         * academyName : 北京大学
         * learnCost : 20000
         * academyLogo : http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png
         */

        private List<FamousListBean> famousList;
        /**
         * academyId : 3
         * recruitStudentsNumber : 2000
         * academyName : 四川大学
         * learnCost : 20000
         * academyLogo : http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png
         */

        private List<HotListBean> hotList;

        public List<FamousListBean> getFamousList() {
            return famousList;
        }

        public void setFamousList(List<FamousListBean> famousList) {
            this.famousList = famousList;
        }

        public List<HotListBean> getHotList() {
            return hotList;
        }

        public void setHotList(List<HotListBean> hotList) {
            this.hotList = hotList;
        }

        public static class FamousListBean {
            private String academyId;
            private String recruitStudentsNumber;
            private String academyName;
            private String learnCost;
            private String academyLogo;

            public String getAcademyId() {
                return academyId;
            }

            public void setAcademyId(String academyId) {
                this.academyId = academyId;
            }

            public String getRecruitStudentsNumber() {
                return recruitStudentsNumber;
            }

            public void setRecruitStudentsNumber(String recruitStudentsNumber) {
                this.recruitStudentsNumber = recruitStudentsNumber;
            }

            public String getAcademyName() {
                return academyName;
            }

            public void setAcademyName(String academyName) {
                this.academyName = academyName;
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
        }

        public static class HotListBean {
            private String academyId;
            private String recruitStudentsNumber;
            private String academyName;
            private String learnCost;
            private String academyLogo;

            public String getAcademyId() {
                return academyId;
            }

            public void setAcademyId(String academyId) {
                this.academyId = academyId;
            }

            public String getRecruitStudentsNumber() {
                return recruitStudentsNumber;
            }

            public void setRecruitStudentsNumber(String recruitStudentsNumber) {
                this.recruitStudentsNumber = recruitStudentsNumber;
            }

            public String getAcademyName() {
                return academyName;
            }

            public void setAcademyName(String academyName) {
                this.academyName = academyName;
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
        }
    }
}
