package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class CourseDetailBean {

    /**
     * returnCode : 0
     * data : {"mbaCommentList":[{"content":"老师很漂亮","nickName":"学长1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-02-06 17:32:08"}],"teacherInfo":{"teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","nickName":"普通用户1","teacherTitle":"MBA资深专家","userId":"1","personalSign":"导读导读收到的","imUser":"ysx1480408356164test0000"},"isfocus":"0","courseSectionList":[{"chapterName":"章节一","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节一小节一"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节一小节二"}]},{"chapterName":"章节二","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节二小节一"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节二小节二"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节二小节三"}]},{"chapterName":"章节三","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节三小节一"}]},{"chapterName":"章节四","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节四小节一"}]},{"chapterName":"章节五","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节五小节一"}]}]}
     * returnMsg : 操作成功
     */

    private String returnCode;
    /**
     * mbaCommentList : [{"content":"老师很漂亮","nickName":"学长1","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"2017-02-06 17:32:08"}]
     * teacherInfo : {"teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","nickName":"普通用户1","teacherTitle":"MBA资深专家","userId":"1","personalSign":"导读导读收到的","imUser":"ysx1480408356164test0000"}
     * isfocus : 0
     * courseSectionList : [{"chapterName":"章节一","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节一小节一"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节一小节二"}]},{"chapterName":"章节二","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节二小节一"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节二小节二"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节二小节三"}]},{"chapterName":"章节三","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节三小节一"}]},{"chapterName":"章节四","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节四小节一"}]},{"chapterName":"章节五","chapterSectionList":[{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节五小节一"}]}]
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
        /**
         * teacherPersonalIntroduce : 毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家“博雅汇教育”（www.byhmba.com）专注名校MBA申请，通过率90%。一家“考哪儿”专注在线教育领域，2016年已获得Pre-A轮投资。
         * nickName : 普通用户1
         * teacherTitle : MBA资深专家
         * userId : 1
         * personalSign : 导读导读收到的
         * imUser : ysx1480408356164test0000
         */

        private TeacherInfoBean teacherInfo;
        private String isfocus;
        private String isShow;
        private String courseCover;
        /**
         * content : 老师很漂亮
         * nickName : 学长1
         * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
         * createDate : 2017-02-06 17:32:08
         */

        private List<MbaCommentListBean> mbaCommentList;
        /**
         * chapterName : 章节一
         * chapterId : 1
         * chapterSectionList : [{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节一小节一"},{"chapterSectionDuration":"一小时","thirdPartyId":"111","chapterSectionName":"章节一小节二"}]
         */

        private List<CourseSectionListBean> courseSectionList;

        public TeacherInfoBean getTeacherInfo() {
            return teacherInfo;
        }

        public void setTeacherInfo(TeacherInfoBean teacherInfo) {
            this.teacherInfo = teacherInfo;
        }

        public String getCourseCover() {
            return courseCover;
        }

        public void setCourseCover(String courseCover) {
            this.courseCover = courseCover;
        }

        public String getIsfocus() {
            return isfocus;
        }

        public void setIsfocus(String isfocus) {
            this.isfocus = isfocus;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public List<MbaCommentListBean> getMbaCommentList() {
            return mbaCommentList;
        }

        public void setMbaCommentList(List<MbaCommentListBean> mbaCommentList) {
            this.mbaCommentList = mbaCommentList;
        }

        public List<CourseSectionListBean> getCourseSectionList() {
            return courseSectionList;
        }

        public void setCourseSectionList(List<CourseSectionListBean> courseSectionList) {
            this.courseSectionList = courseSectionList;
        }

        public static class TeacherInfoBean {
            private String teacherPersonalIntroduce;
            private String nickName;
            private String teacherTitle;
            private String userId;
            private String personalSign;
            private String imUser;
            private String teacherPersonalCover;

            public String getTeacherPersonalCover() {
                return teacherPersonalCover;
            }

            public void setTeacherPersonalCover(String teacherPersonalCover) {
                this.teacherPersonalCover = teacherPersonalCover;
            }

            public String getTeacherPersonalIntroduce() {
                return teacherPersonalIntroduce;
            }

            public void setTeacherPersonalIntroduce(String teacherPersonalIntroduce) {
                this.teacherPersonalIntroduce = teacherPersonalIntroduce;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getTeacherTitle() {
                return teacherTitle;
            }

            public void setTeacherTitle(String teacherTitle) {
                this.teacherTitle = teacherTitle;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getPersonalSign() {
                return personalSign;
            }

            public void setPersonalSign(String personalSign) {
                this.personalSign = personalSign;
            }

            public String getImUser() {
                return imUser;
            }

            public void setImUser(String imUser) {
                this.imUser = imUser;
            }
        }

        public static class CourseSectionListBean {
            private String chapterName;
            private String chapterId;
            /**
             * chapterSectionDuration : 一小时
             * thirdPartyId : 111
             * chapterSectionId :14
             * chapterSectionName : 章节一小节一
             */

            public String getChapterId() {
                return chapterId;
            }

            public void setChapterId(String chapterId) {
                this.chapterId = chapterId;
            }
            private List<ChapterSectionListBean> chapterSectionList;

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public List<ChapterSectionListBean> getChapterSectionList() {
                return chapterSectionList;
            }

            public void setChapterSectionList(List<ChapterSectionListBean> chapterSectionList) {
                this.chapterSectionList = chapterSectionList;
            }

            public static class ChapterSectionListBean {
                private String chapterSectionDuration;
                private String thirdPartyId;
                private String chapterSectionName;
                private String chapterSectionId;

                public String getChapterSectionId() {
                    return chapterSectionId;
                }

                public void setChapterSectionId(String chapterSectionId) {
                    this.chapterSectionId = chapterSectionId;
                }

                public String getChapterSectionDuration() {
                    return chapterSectionDuration;
                }

                public void setChapterSectionDuration(String chapterSectionDuration) {
                    this.chapterSectionDuration = chapterSectionDuration;
                }

                public String getThirdPartyId() {
                    return thirdPartyId;
                }

                public void setThirdPartyId(String thirdPartyId) {
                    this.thirdPartyId = thirdPartyId;
                }

                public String getChapterSectionName() {
                    return chapterSectionName;
                }

                public void setChapterSectionName(String chapterSectionName) {
                    this.chapterSectionName = chapterSectionName;
                }
            }
        }
    }
}
