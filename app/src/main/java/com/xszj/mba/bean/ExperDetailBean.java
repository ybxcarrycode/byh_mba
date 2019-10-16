package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class ExperDetailBean {


    /**
     * returnCode : 0
     * data : {"isFollow":"1","expertDetail":{"userId":"2","nickName":"学长1","trueName":"学长1","phone":"13333333333","password":"123456","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","personalSign":"导读导读收到的","graduationShoolId":"-1","graduationSpecialities":"","enterpriseType":"-1","willSchoolId":"-1","teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","createDate":"2017-02-06 11:58:06","teacherTitle":"MBA资深专家","imUser":"","imUserPassword":"","userType":"13","email":"","remark":"","teacherPersonalCover":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/19/11/1479525832538.jpg","admitStudentSchoolId":"1","orderBy":"","nickNameLike":"","trueNameLike":"","phoneLike":"","passwordLike":"","headPicLike":"","personalSignLike":"","graduationSpecialitiesLike":"","teacherPersonalIntroduceLike":"","createDateBegin":"","createDateEnd":"","teacherTitleLike":"","imUserLike":"","imUserPasswordLike":"","emailLike":"","remarkLike":"","teacherPersonalCoverLike":"","createDateChar":"2017-02-06","createDateCharAll":"2017-02-06 11:58:06"},"commentList":[{"content":"PK去去去up哦WPS去我去取去","nickName":"学长1","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"24分钟前"},{"content":"很好的，GPU去去","nickName":"学长1","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"27分钟前"},{"content":"哦用","nickName":"学长1","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"27分钟前"}]}
     * returnMsg : 操作成功
     */

    private String returnCode;
    /**
     * isFollow : 1
     * expertDetail : {"userId":"2","nickName":"学长1","trueName":"学长1","phone":"13333333333","password":"123456","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","personalSign":"导读导读收到的","graduationShoolId":"-1","graduationSpecialities":"","enterpriseType":"-1","willSchoolId":"-1","teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","createDate":"2017-02-06 11:58:06","teacherTitle":"MBA资深专家","imUser":"","imUserPassword":"","userType":"13","email":"","remark":"","teacherPersonalCover":"http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/19/11/1479525832538.jpg","admitStudentSchoolId":"1","orderBy":"","nickNameLike":"","trueNameLike":"","phoneLike":"","passwordLike":"","headPicLike":"","personalSignLike":"","graduationSpecialitiesLike":"","teacherPersonalIntroduceLike":"","createDateBegin":"","createDateEnd":"","teacherTitleLike":"","imUserLike":"","imUserPasswordLike":"","emailLike":"","remarkLike":"","teacherPersonalCoverLike":"","createDateChar":"2017-02-06","createDateCharAll":"2017-02-06 11:58:06"}
     * commentList : [{"content":"PK去去去up哦WPS去我去取去","nickName":"学长1","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"24分钟前"},{"content":"很好的，GPU去去","nickName":"学长1","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"27分钟前"},{"content":"哦用","nickName":"学长1","personalSign":"导读导读收到的","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg","createDate":"27分钟前"}]
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
        private String isFollow;
        /**
         * userId : 2
         * nickName : 学长1
         * trueName : 学长1
         * phone : 13333333333
         * password : 123456
         * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
         * personalSign : 导读导读收到的
         * graduationShoolId : -1
         * graduationSpecialities :
         * enterpriseType : -1
         * willSchoolId : -1
         * teacherPersonalIntroduce : 毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家“博雅汇教育”（www.byhmba.com）专注名校MBA申请，通过率90%。一家“考哪儿”专注在线教育领域，2016年已获得Pre-A轮投资。
         * createDate : 2017-02-06 11:58:06
         * teacherTitle : MBA资深专家
         * imUser :
         * imUserPassword :
         * userType : 13
         * email :
         * remark :
         * teacherPersonalCover : http://oss-ysx-pic.yunshuxie.com/head-icon/2016/11/19/11/1479525832538.jpg
         * admitStudentSchoolId : 1
         * orderBy :
         * nickNameLike :
         * trueNameLike :
         * phoneLike :
         * passwordLike :
         * headPicLike :
         * personalSignLike :
         * graduationSpecialitiesLike :
         * teacherPersonalIntroduceLike :
         * createDateBegin :
         * createDateEnd :
         * teacherTitleLike :
         * imUserLike :
         * imUserPasswordLike :
         * emailLike :
         * remarkLike :
         * teacherPersonalCoverLike :
         * createDateChar : 2017-02-06
         * createDateCharAll : 2017-02-06 11:58:06
         */

        private ExpertDetailBean expertDetail;
        /**
         * content : PK去去去up哦WPS去我去取去
         * nickName : 学长1
         * personalSign : 导读导读收到的
         * headPic : http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg
         * createDate : 24分钟前
         */

        private List<MbaCommentListBean> commentList;

        public String getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(String isFollow) {
            this.isFollow = isFollow;
        }

        public ExpertDetailBean getExpertDetail() {
            return expertDetail;
        }

        public void setExpertDetail(ExpertDetailBean expertDetail) {
            this.expertDetail = expertDetail;
        }

        public List<MbaCommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<MbaCommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class ExpertDetailBean {
            private String userId;
            private String nickName;
            private String trueName;
            private String phone;
            private String password;
            private String headPic;
            private String personalSign;
            private String graduationShoolId;
            private String graduationSpecialities;
            private String enterpriseType;
            private String willSchoolId;
            private String teacherPersonalIntroduce;
            private String createDate;
            private String teacherTitle;
            private String imUser;
            private String imUserPassword;
            private String userType;
            private String email;
            private String remark;
            private String teacherPersonalCover;
            private String admitStudentSchoolId;
            private String orderBy;
            private String nickNameLike;
            private String trueNameLike;
            private String phoneLike;
            private String passwordLike;
            private String headPicLike;
            private String personalSignLike;
            private String graduationSpecialitiesLike;
            private String teacherPersonalIntroduceLike;
            private String createDateBegin;
            private String createDateEnd;
            private String teacherTitleLike;
            private String imUserLike;
            private String imUserPasswordLike;
            private String emailLike;
            private String remarkLike;
            private String teacherPersonalCoverLike;
            private String createDateChar;
            private String createDateCharAll;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getTrueName() {
                return trueName;
            }

            public void setTrueName(String trueName) {
                this.trueName = trueName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getPersonalSign() {
                return personalSign;
            }

            public void setPersonalSign(String personalSign) {
                this.personalSign = personalSign;
            }

            public String getGraduationShoolId() {
                return graduationShoolId;
            }

            public void setGraduationShoolId(String graduationShoolId) {
                this.graduationShoolId = graduationShoolId;
            }

            public String getGraduationSpecialities() {
                return graduationSpecialities;
            }

            public void setGraduationSpecialities(String graduationSpecialities) {
                this.graduationSpecialities = graduationSpecialities;
            }

            public String getEnterpriseType() {
                return enterpriseType;
            }

            public void setEnterpriseType(String enterpriseType) {
                this.enterpriseType = enterpriseType;
            }

            public String getWillSchoolId() {
                return willSchoolId;
            }

            public void setWillSchoolId(String willSchoolId) {
                this.willSchoolId = willSchoolId;
            }

            public String getTeacherPersonalIntroduce() {
                return teacherPersonalIntroduce;
            }

            public void setTeacherPersonalIntroduce(String teacherPersonalIntroduce) {
                this.teacherPersonalIntroduce = teacherPersonalIntroduce;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getTeacherTitle() {
                return teacherTitle;
            }

            public void setTeacherTitle(String teacherTitle) {
                this.teacherTitle = teacherTitle;
            }

            public String getImUser() {
                return imUser;
            }

            public void setImUser(String imUser) {
                this.imUser = imUser;
            }

            public String getImUserPassword() {
                return imUserPassword;
            }

            public void setImUserPassword(String imUserPassword) {
                this.imUserPassword = imUserPassword;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getTeacherPersonalCover() {
                return teacherPersonalCover;
            }

            public void setTeacherPersonalCover(String teacherPersonalCover) {
                this.teacherPersonalCover = teacherPersonalCover;
            }

            public String getAdmitStudentSchoolId() {
                return admitStudentSchoolId;
            }

            public void setAdmitStudentSchoolId(String admitStudentSchoolId) {
                this.admitStudentSchoolId = admitStudentSchoolId;
            }

            public String getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
            }

            public String getNickNameLike() {
                return nickNameLike;
            }

            public void setNickNameLike(String nickNameLike) {
                this.nickNameLike = nickNameLike;
            }

            public String getTrueNameLike() {
                return trueNameLike;
            }

            public void setTrueNameLike(String trueNameLike) {
                this.trueNameLike = trueNameLike;
            }

            public String getPhoneLike() {
                return phoneLike;
            }

            public void setPhoneLike(String phoneLike) {
                this.phoneLike = phoneLike;
            }

            public String getPasswordLike() {
                return passwordLike;
            }

            public void setPasswordLike(String passwordLike) {
                this.passwordLike = passwordLike;
            }

            public String getHeadPicLike() {
                return headPicLike;
            }

            public void setHeadPicLike(String headPicLike) {
                this.headPicLike = headPicLike;
            }

            public String getPersonalSignLike() {
                return personalSignLike;
            }

            public void setPersonalSignLike(String personalSignLike) {
                this.personalSignLike = personalSignLike;
            }

            public String getGraduationSpecialitiesLike() {
                return graduationSpecialitiesLike;
            }

            public void setGraduationSpecialitiesLike(String graduationSpecialitiesLike) {
                this.graduationSpecialitiesLike = graduationSpecialitiesLike;
            }

            public String getTeacherPersonalIntroduceLike() {
                return teacherPersonalIntroduceLike;
            }

            public void setTeacherPersonalIntroduceLike(String teacherPersonalIntroduceLike) {
                this.teacherPersonalIntroduceLike = teacherPersonalIntroduceLike;
            }

            public String getCreateDateBegin() {
                return createDateBegin;
            }

            public void setCreateDateBegin(String createDateBegin) {
                this.createDateBegin = createDateBegin;
            }

            public String getCreateDateEnd() {
                return createDateEnd;
            }

            public void setCreateDateEnd(String createDateEnd) {
                this.createDateEnd = createDateEnd;
            }

            public String getTeacherTitleLike() {
                return teacherTitleLike;
            }

            public void setTeacherTitleLike(String teacherTitleLike) {
                this.teacherTitleLike = teacherTitleLike;
            }

            public String getImUserLike() {
                return imUserLike;
            }

            public void setImUserLike(String imUserLike) {
                this.imUserLike = imUserLike;
            }

            public String getImUserPasswordLike() {
                return imUserPasswordLike;
            }

            public void setImUserPasswordLike(String imUserPasswordLike) {
                this.imUserPasswordLike = imUserPasswordLike;
            }

            public String getEmailLike() {
                return emailLike;
            }

            public void setEmailLike(String emailLike) {
                this.emailLike = emailLike;
            }

            public String getRemarkLike() {
                return remarkLike;
            }

            public void setRemarkLike(String remarkLike) {
                this.remarkLike = remarkLike;
            }

            public String getTeacherPersonalCoverLike() {
                return teacherPersonalCoverLike;
            }

            public void setTeacherPersonalCoverLike(String teacherPersonalCoverLike) {
                this.teacherPersonalCoverLike = teacherPersonalCoverLike;
            }

            public String getCreateDateChar() {
                return createDateChar;
            }

            public void setCreateDateChar(String createDateChar) {
                this.createDateChar = createDateChar;
            }

            public String getCreateDateCharAll() {
                return createDateCharAll;
            }

            public void setCreateDateCharAll(String createDateCharAll) {
                this.createDateCharAll = createDateCharAll;
            }
        }
    }
}
