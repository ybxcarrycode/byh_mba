package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */

public class TeacherDetailFeagmentBean {


    /**
     * returnCode : 0
     * data : {"liveVideoDetail":{"liveName":"徐彦超直播","teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","teacherPersonalCover":"","nickName":"普通用户1","liveVideoId":"38","userId":"1","teacherTitle":"MBA资深专家","liveType":"0","mobileUrl":"www.baidu.com","imUser":"ysx1480408356164test0000"},"mbaCommentList":[{"content":"老师很漂亮","nickName":"学长1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-02-06 17:32:08"}],"isfocus":"0"}
     * returnMsg : 操作成功
     */

    private String returnCode;
    /**
     * liveVideoDetail : {"liveName":"徐彦超直播","teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","teacherPersonalCover":"","nickName":"普通用户1","liveVideoId":"38","userId":"1","teacherTitle":"MBA资深专家","liveType":"0","mobileUrl":"www.baidu.com","imUser":"ysx1480408356164test0000"}
     * mbaCommentList : [{"content":"老师很漂亮","nickName":"学长1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-02-06 17:32:08"}]
     * isfocus : 0
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

    public static class DataBean{
        /**
         * liveName : 徐彦超直播
         * teacherPersonalIntroduce : 毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家“博雅汇教育”（www.byhmba.com）专注名校MBA申请，通过率90%。一家“考哪儿”专注在线教育领域，2016年已获得Pre-A轮投资。
         * teacherPersonalCover :
         * nickName : 普通用户1
         * liveVideoId : 38
         * userId : 1
         * teacherTitle : MBA资深专家
         * liveType : 0
         * mobileUrl : www.baidu.com
         * imUser : ysx1480408356164test0000
         */

        private LiveVideoDetailBean liveVideoDetail;
        private String isfocus;
        /**
         * content : 老师很漂亮
         * nickName : 学长1
         * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
         * createDate : 2017-02-06 17:32:08
         */

        private List<MbaCommentListBean> mbaCommentList;

        public LiveVideoDetailBean getLiveVideoDetail() {
            return liveVideoDetail;
        }

        public void setLiveVideoDetail(LiveVideoDetailBean liveVideoDetail) {
            this.liveVideoDetail = liveVideoDetail;
        }

        public String getIsfocus() {
            return isfocus;
        }

        public void setIsfocus(String isfocus) {
            this.isfocus = isfocus;
        }

        public List<MbaCommentListBean> getMbaCommentList() {
            return mbaCommentList;
        }

        public void setMbaCommentList(List<MbaCommentListBean> mbaCommentList) {
            this.mbaCommentList = mbaCommentList;
        }

        public static class LiveVideoDetailBean {
            private String liveName;
            private String teacherPersonalIntroduce;
            private String teacherPersonalCover;
            private String nickName;
            private String liveVideoId;
            private String userId;
            private String teacherTitle;
            private String liveType;
            private String mobileUrl;
            private String imUser;

            public String getLiveName() {
                return liveName;
            }

            public void setLiveName(String liveName) {
                this.liveName = liveName;
            }

            public String getTeacherPersonalIntroduce() {
                return teacherPersonalIntroduce;
            }

            public void setTeacherPersonalIntroduce(String teacherPersonalIntroduce) {
                this.teacherPersonalIntroduce = teacherPersonalIntroduce;
            }

            public String getTeacherPersonalCover() {
                return teacherPersonalCover;
            }

            public void setTeacherPersonalCover(String teacherPersonalCover) {
                this.teacherPersonalCover = teacherPersonalCover;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getLiveVideoId() {
                return liveVideoId;
            }

            public void setLiveVideoId(String liveVideoId) {
                this.liveVideoId = liveVideoId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getTeacherTitle() {
                return teacherTitle;
            }

            public void setTeacherTitle(String teacherTitle) {
                this.teacherTitle = teacherTitle;
            }

            public String getLiveType() {
                return liveType;
            }

            public void setLiveType(String liveType) {
                this.liveType = liveType;
            }

            public String getMobileUrl() {
                return mobileUrl;
            }

            public void setMobileUrl(String mobileUrl) {
                this.mobileUrl = mobileUrl;
            }

            public String getImUser() {
                return imUser;
            }

            public void setImUser(String imUser) {
                this.imUser = imUser;
            }
        }

    }
}
