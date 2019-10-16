package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class HomeDataBean {

    /**
     * returnCode : 0
     * data : {"bannerList":[{"linkUrl":"www.baidu.com","bannerId":"1","bannerPosition":"0","bannerCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/banner1.jpg"},{"linkUrl":"www.baidu.com","bannerId":"2","bannerPosition":"0","bannerCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/banner2.jpg"},{"linkUrl":"www..baidu.com","bannerId":"3","bannerPosition":"0","bannerCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/banner3.jpg"}],"liveList":[{"liveName":"鍚嶅笀鐩存挱","liveTime":"14:12-15:12","liveDate":"2017.02.07","endTime":"2017-02-07 15:12:26","videoName":"asdfsdf","showtype":"0","startTime":"2017-02-07 14:12:23","cover":"www.baidu.com","liveVideoId":"37","description":"asdf","countnum":"0","liveNotice":"asdfasdf","mobileUrl":"www.baidu.com","liveType":"0","viewSpectator":"230"},{"liveName":"寰愬溅瓒呯洿鎾�","liveTime":"20:12-21:12","liveDate":"2017.02.07","endTime":"2017-02-07 21:12:26","videoName":"asdfsdf","showtype":"2","startTime":"2017-02-07 20:12:23","cover":"www.baidu.com","liveVideoId":"38","description":"asdf","countnum":"1","liveNotice":"asdfasdf","mobileUrl":"www.baidu.com","liveType":"0","viewSpectator":"230"}],"writtenExaminationList":[{"parentId":"3","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E8%8B%B1%E8%AF%AD.png","dictionaryId":"8"},{"parentId":"3","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E6%95%B0%E5%AD%A6.png","dictionaryId":"9"},{"parentId":"3","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E9%80%BB%E8%BE%91.png","dictionaryId":"10"},{"parentId":"3","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E5%86%99%E4%BD%9C.png","dictionaryId":"11"}],"academyList":[{"academyId":"1","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png"},{"academyId":"3","academyLogo":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png"},{"academyId":"5","academyLogo":"http://www.gaokaopai.com/Public/Uploads/53477df2c4fba.jpg"}],"interviewList":[{"parentId":"2","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E9%9D%A2%E8%AF%95%E9%80%9A%E5%85%B3-%E4%B8%8D%E5%B8%A6%E6%96%87%E5%AD%97.png","dictionaryId":"4"},{"parentId":"2","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E9%9D%A2%E8%AF%95%E9%80%9A%E5%85%B3-%E4%B8%8D%E5%B8%A6%E6%96%87%E5%AD%97.png","dictionaryId":"5"},{"parentId":"2","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E9%9D%A2%E8%AF%95%E9%80%9A%E5%85%B3-%E4%B8%8D%E5%B8%A6%E6%96%87%E5%AD%97.png","dictionaryId":"6"},{"parentId":"2","dictionaryRemark":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E9%9D%A2%E8%AF%95%E9%80%9A%E5%85%B3-%E4%B8%8D%E5%B8%A6%E6%96%87%E5%AD%97.png","dictionaryId":"7"}]}
     * returnMsg : 鎿嶄綔鎴愬姛
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
         * linkUrl : www.baidu.com
         * bannerId : 1
         * bannerPosition : 0
         * bannerCover : http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/banner1.jpg
         */

        private List<BannerListBean> bannerList;
        /**
         * liveName : 鍚嶅笀鐩存挱
         * liveTime : 14:12-15:12
         * liveDate : 2017.02.07
         * endTime : 2017-02-07 15:12:26
         * videoName : asdfsdf
         * showtype : 0
         * startTime : 2017-02-07 14:12:23
         * cover : www.baidu.com
         * liveVideoId : 37
         * description : asdf
         * countnum : 0
         * liveNotice : asdfasdf
         * mobileUrl : www.baidu.com
         * liveType : 0
         * viewSpectator : 230
         */

        private List<LiveListBean> liveList;
        /**
         * parentId : 3
         * dictionaryRemark : http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E8%8B%B1%E8%AF%AD.png
         * dictionaryId : 8
         */

        private List<WrittenExaminationListBean> writtenExaminationList;
        /**
         * academyId : 1
         * academyLogo : http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/22/17/1479807663499.png
         */

        private List<AcademyListBean> academyList;
        /**
         * parentId : 2
         * dictionaryRemark : http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/%E9%9D%A2%E8%AF%95%E9%80%9A%E5%85%B3-%E4%B8%8D%E5%B8%A6%E6%96%87%E5%AD%97.png
         * dictionaryId : 4
         */

        private List<InterviewListBean> interviewList;

        public List<BannerListBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListBean> bannerList) {
            this.bannerList = bannerList;
        }

        public List<LiveListBean> getLiveList() {
            return liveList;
        }

        public void setLiveList(List<LiveListBean> liveList) {
            this.liveList = liveList;
        }

        public List<WrittenExaminationListBean> getWrittenExaminationList() {
            return writtenExaminationList;
        }

        public void setWrittenExaminationList(List<WrittenExaminationListBean> writtenExaminationList) {
            this.writtenExaminationList = writtenExaminationList;
        }

        public List<AcademyListBean> getAcademyList() {
            return academyList;
        }

        public void setAcademyList(List<AcademyListBean> academyList) {
            this.academyList = academyList;
        }

        public List<InterviewListBean> getInterviewList() {
            return interviewList;
        }

        public void setInterviewList(List<InterviewListBean> interviewList) {
            this.interviewList = interviewList;
        }

        public static class BannerListBean {
            private String linkUrl;
            private String bannerId;
            private String bannerPosition;
            private String bannerCover;

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getBannerId() {
                return bannerId;
            }

            public void setBannerId(String bannerId) {
                this.bannerId = bannerId;
            }

            public String getBannerPosition() {
                return bannerPosition;
            }

            public void setBannerPosition(String bannerPosition) {
                this.bannerPosition = bannerPosition;
            }

            public String getBannerCover() {
                return bannerCover;
            }

            public void setBannerCover(String bannerCover) {
                this.bannerCover = bannerCover;
            }
        }

        public static class LiveListBean {
            private String liveName;
            private String liveTime;
            private String liveDate;
            private String endTime;
            private String videoName;
            private String showtype;
            private String startTime;
            private String cover;
            private String liveVideoId;
            private String description;
            private String countnum;
            private String liveNotice;
            private String mobileUrl;
            private String liveType;
            private String viewSpectator;

            public String getLiveName() {
                return liveName;
            }

            public void setLiveName(String liveName) {
                this.liveName = liveName;
            }

            public String getLiveTime() {
                return liveTime;
            }

            public void setLiveTime(String liveTime) {
                this.liveTime = liveTime;
            }

            public String getLiveDate() {
                return liveDate;
            }

            public void setLiveDate(String liveDate) {
                this.liveDate = liveDate;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getVideoName() {
                return videoName;
            }

            public void setVideoName(String videoName) {
                this.videoName = videoName;
            }

            public String getShowtype() {
                return showtype;
            }

            public void setShowtype(String showtype) {
                this.showtype = showtype;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getLiveVideoId() {
                return liveVideoId;
            }

            public void setLiveVideoId(String liveVideoId) {
                this.liveVideoId = liveVideoId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCountnum() {
                return countnum;
            }

            public void setCountnum(String countnum) {
                this.countnum = countnum;
            }

            public String getLiveNotice() {
                return liveNotice;
            }

            public void setLiveNotice(String liveNotice) {
                this.liveNotice = liveNotice;
            }

            public String getMobileUrl() {
                return mobileUrl;
            }

            public void setMobileUrl(String mobileUrl) {
                this.mobileUrl = mobileUrl;
            }

            public String getLiveType() {
                return liveType;
            }

            public void setLiveType(String liveType) {
                this.liveType = liveType;
            }

            public String getViewSpectator() {
                return viewSpectator;
            }

            public void setViewSpectator(String viewSpectator) {
                this.viewSpectator = viewSpectator;
            }
        }

        public static class WrittenExaminationListBean {
            private String parentId;
            private String dictionaryRemark;
            private String dictionaryId;
            private String dictionaryName;

            public String getDictionaryName() {
                return dictionaryName;
            }

            public void setDictionaryName(String dictionaryName) {
                this.dictionaryName = dictionaryName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getDictionaryRemark() {
                return dictionaryRemark;
            }

            public void setDictionaryRemark(String dictionaryRemark) {
                this.dictionaryRemark = dictionaryRemark;
            }

            public String getDictionaryId() {
                return dictionaryId;
            }

            public void setDictionaryId(String dictionaryId) {
                this.dictionaryId = dictionaryId;
            }
        }

        public static class AcademyListBean {
            private String academyId;
            private String academyLogo;
            private String academyName;

            public String getAcademyName() {
                return academyName;
            }

            public void setAcademyName(String academyName) {
                this.academyName = academyName;
            }

            public String getAcademyId() {
                return academyId;
            }

            public void setAcademyId(String academyId) {
                this.academyId = academyId;
            }

            public String getAcademyLogo() {
                return academyLogo;
            }

            public void setAcademyLogo(String academyLogo) {
                this.academyLogo = academyLogo;
            }
        }

        public static class InterviewListBean {
            private String parentId;
            private String dictionaryRemark;
            private String dictionaryId;
            private String dictionaryName;

            public String getDictionaryName() {
                return dictionaryName;
            }

            public void setDictionaryName(String dictionaryName) {
                this.dictionaryName = dictionaryName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getDictionaryRemark() {
                return dictionaryRemark;
            }

            public void setDictionaryRemark(String dictionaryRemark) {
                this.dictionaryRemark = dictionaryRemark;
            }

            public String getDictionaryId() {
                return dictionaryId;
            }

            public void setDictionaryId(String dictionaryId) {
                this.dictionaryId = dictionaryId;
            }
        }
    }
}
