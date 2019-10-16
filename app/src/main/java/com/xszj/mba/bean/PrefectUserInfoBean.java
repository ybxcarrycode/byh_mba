package com.xszj.mba.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class PrefectUserInfoBean implements Serializable{


    /**
     * returnCode : 0
     * data : {"userInfo":{"returnCode":"0","enterpriseType":"","phone":"13333333333","trueName":"学长1","nickName":"学长1","userId":"2","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","graduationShoolId":"","willSchoolId":"","userType":"13"},"baseData":{"enterpriseData":[{"dictionaryName":"民营企业","dictionaryType":"6","sortCode":"1","dictionaryId":"34"},{"dictionaryName":"外企","dictionaryType":"6","sortCode":"2","dictionaryId":"33"},{"dictionaryName":"央企/国企","dictionaryType":"6","sortCode":"3","dictionaryId":"32"},{"dictionaryName":"自主创业","dictionaryType":"6","sortCode":"4","dictionaryId":"31"},{"dictionaryName":"世界500强","dictionaryType":"6","sortCode":"5","dictionaryId":"30"}],"willSchoolData":[{"dictionaryName":"北京大学","dictionaryType":"4","sortCode":"1","dictionaryId":"21"},{"dictionaryName":"清华大学","dictionaryType":"4","sortCode":"2","dictionaryId":"22"},{"dictionaryName":"四川大学","dictionaryType":"4","sortCode":"3","dictionaryId":"23"},{"dictionaryName":"南开大学","dictionaryType":"4","sortCode":"4","dictionaryId":"24"},{"dictionaryName":"其他","dictionaryType":"4","sortCode":"5","dictionaryId":"25"}],"graduationShoolData":[{"dictionaryName":"985/211院校","dictionaryType":"5","sortCode":"1","dictionaryId":"26"},{"dictionaryName":"海外院校","dictionaryType":"5","sortCode":"2","dictionaryId":"27"},{"dictionaryName":"重点院校","dictionaryType":"5","sortCode":"3","dictionaryId":"28"},{"dictionaryName":"普通院校","dictionaryType":"5","sortCode":"4","dictionaryId":"29"}]}}
     * returnMsg : 操作成功
     */

    private String returnCode;
    /**
     * userInfo : {"returnCode":"0","enterpriseType":"","phone":"13333333333","trueName":"学长1","nickName":"学长1","userId":"2","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","graduationShoolId":"","willSchoolId":"","userType":"13"}
     * baseData : {"enterpriseData":[{"dictionaryName":"民营企业","dictionaryType":"6","sortCode":"1","dictionaryId":"34"},{"dictionaryName":"外企","dictionaryType":"6","sortCode":"2","dictionaryId":"33"},{"dictionaryName":"央企/国企","dictionaryType":"6","sortCode":"3","dictionaryId":"32"},{"dictionaryName":"自主创业","dictionaryType":"6","sortCode":"4","dictionaryId":"31"},{"dictionaryName":"世界500强","dictionaryType":"6","sortCode":"5","dictionaryId":"30"}],"willSchoolData":[{"dictionaryName":"北京大学","dictionaryType":"4","sortCode":"1","dictionaryId":"21"},{"dictionaryName":"清华大学","dictionaryType":"4","sortCode":"2","dictionaryId":"22"},{"dictionaryName":"四川大学","dictionaryType":"4","sortCode":"3","dictionaryId":"23"},{"dictionaryName":"南开大学","dictionaryType":"4","sortCode":"4","dictionaryId":"24"},{"dictionaryName":"其他","dictionaryType":"4","sortCode":"5","dictionaryId":"25"}],"graduationShoolData":[{"dictionaryName":"985/211院校","dictionaryType":"5","sortCode":"1","dictionaryId":"26"},{"dictionaryName":"海外院校","dictionaryType":"5","sortCode":"2","dictionaryId":"27"},{"dictionaryName":"重点院校","dictionaryType":"5","sortCode":"3","dictionaryId":"28"},{"dictionaryName":"普通院校","dictionaryType":"5","sortCode":"4","dictionaryId":"29"}]}
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

    public static class DataBean implements Serializable{
        /**
         * returnCode : 0
         * enterpriseType :
         * phone : 13333333333
         * trueName : 学长1
         * nickName : 学长1
         * userId : 2
         * personalSign : 导读导读收到的
         * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
         * graduationShoolId :
         * willSchoolId :
         * userType : 13
         */

        private UserInfoBean userInfo;
        private BaseDataBean baseData;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public BaseDataBean getBaseData() {
            return baseData;
        }

        public void setBaseData(BaseDataBean baseData) {
            this.baseData = baseData;
        }


        public static class BaseDataBean implements Serializable{
            /**
             * dictionaryName : 民营企业
             * dictionaryType : 6
             * sortCode : 1
             * dictionaryId : 34
             */

            private List<PerfectSchoolAndTypeBean> enterpriseData;
            /**
             * dictionaryName : 北京大学
             * dictionaryType : 4
             * sortCode : 1
             * dictionaryId : 21
             */

            private List<PerfectSchoolAndTypeBean> willSchoolData;
            /**
             * dictionaryName : 985/211院校
             * dictionaryType : 5
             * sortCode : 1
             * dictionaryId : 26
             */

            private List<PerfectSchoolAndTypeBean> graduationShoolData;

            public List<PerfectSchoolAndTypeBean> getEnterpriseData() {
                return enterpriseData;
            }

            public void setEnterpriseData(List<PerfectSchoolAndTypeBean> enterpriseData) {
                this.enterpriseData = enterpriseData;
            }

            public List<PerfectSchoolAndTypeBean> getWillSchoolData() {
                return willSchoolData;
            }

            public void setWillSchoolData(List<PerfectSchoolAndTypeBean> willSchoolData) {
                this.willSchoolData = willSchoolData;
            }

            public List<PerfectSchoolAndTypeBean> getGraduationShoolData() {
                return graduationShoolData;
            }

            public void setGraduationShoolData(List<PerfectSchoolAndTypeBean> graduationShoolData) {
                this.graduationShoolData = graduationShoolData;
            }


        }
    }
}
