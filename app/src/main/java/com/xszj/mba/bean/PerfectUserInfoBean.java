package com.xszj.mba.bean;

/**
 * Created by Ybx on 2017/3/6.
 */

public class PerfectUserInfoBean  {

    /**
     * data : {"userBean":{"admitStudentSchoolId":"-1","createDate":"","createDateBegin":"","createDateChar":"","createDateCharAll":"","createDateEnd":"","email":"","emailLike":"","enterpriseType":"-1","graduationShoolId":"-1","graduationSpecialities":"","graduationSpecialitiesLike":"","headPic":"","headPicLike":"","imUser":"","imUserLike":"","imUserPassword":"","imUserPasswordLike":"","nickName":"","nickNameLike":"","orderBy":"","password":"","passwordLike":"","personalSign":"","personalSignLike":"","phone":"","phoneLike":"","remark":"","remarkLike":"","teacherPersonalCover":"","teacherPersonalCoverLike":"","teacherPersonalIntroduce":"","teacherPersonalIntroduceLike":"","teacherTitle":"","teacherTitleLike":"","tpId":"","tpNickName":"","tpType":"-1","trueName":"","trueNameLike":"","userId":"10014","userType":"-1","willSchoolId":"25"}}
     * returnCode : 0
     * returnMsg : 操作成功
     */

    private DataBean data;
    private String returnCode;
    private String returnMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * userBean : {"admitStudentSchoolId":"-1","createDate":"","createDateBegin":"","createDateChar":"","createDateCharAll":"","createDateEnd":"","email":"","emailLike":"","enterpriseType":"-1","graduationShoolId":"-1","graduationSpecialities":"","graduationSpecialitiesLike":"","headPic":"","headPicLike":"","imUser":"","imUserLike":"","imUserPassword":"","imUserPasswordLike":"","nickName":"","nickNameLike":"","orderBy":"","password":"","passwordLike":"","personalSign":"","personalSignLike":"","phone":"","phoneLike":"","remark":"","remarkLike":"","teacherPersonalCover":"","teacherPersonalCoverLike":"","teacherPersonalIntroduce":"","teacherPersonalIntroduceLike":"","teacherTitle":"","teacherTitleLike":"","tpId":"","tpNickName":"","tpType":"-1","trueName":"","trueNameLike":"","userId":"10014","userType":"-1","willSchoolId":"25"}
         */

        private UserBeanBean userBean;

        public UserBeanBean getUserBean() {
            return userBean;
        }

        public void setUserBean(UserBeanBean userBean) {
            this.userBean = userBean;
        }

        public static class UserBeanBean {
            /**
             * admitStudentSchoolId : -1
             * createDate :
             * createDateBegin :
             * createDateChar :
             * createDateCharAll :
             * createDateEnd :
             * email :
             * emailLike :
             * enterpriseType : -1
             * graduationShoolId : -1
             * graduationSpecialities :
             * graduationSpecialitiesLike :
             * headPic :
             * headPicLike :
             * imUser :
             * imUserLike :
             * imUserPassword :
             * imUserPasswordLike :
             * nickName :
             * nickNameLike :
             * orderBy :
             * password :
             * passwordLike :
             * personalSign :
             * personalSignLike :
             * phone :
             * phoneLike :
             * remark :
             * remarkLike :
             * teacherPersonalCover :
             * teacherPersonalCoverLike :
             * teacherPersonalIntroduce :
             * teacherPersonalIntroduceLike :
             * teacherTitle :
             * teacherTitleLike :
             * tpId :
             * tpNickName :
             * tpType : -1
             * trueName :
             * trueNameLike :
             * userId : 10014
             * userType : -1
             * willSchoolId : 25
             */

            private String admitStudentSchoolId;
            private String createDate;
            private String createDateBegin;
            private String createDateChar;
            private String createDateCharAll;
            private String createDateEnd;
            private String email;
            private String emailLike;
            private String enterpriseType;
            private String graduationShoolId;
            private String graduationSpecialities;
            private String graduationSpecialitiesLike;
            private String headPic;
            private String headPicLike;
            private String imUser;
            private String imUserLike;
            private String imUserPassword;
            private String imUserPasswordLike;
            private String nickName;
            private String nickNameLike;
            private String orderBy;
            private String password;
            private String passwordLike;
            private String personalSign;
            private String personalSignLike;
            private String phone;
            private String phoneLike;
            private String remark;
            private String remarkLike;
            private String teacherPersonalCover;
            private String teacherPersonalCoverLike;
            private String teacherPersonalIntroduce;
            private String teacherPersonalIntroduceLike;
            private String teacherTitle;
            private String teacherTitleLike;
            private String tpId;
            private String tpNickName;
            private String tpType;
            private String trueName;
            private String trueNameLike;
            private String userId;
            private String userType;
            private String willSchoolId;

            public String getAdmitStudentSchoolId() {
                return admitStudentSchoolId;
            }

            public void setAdmitStudentSchoolId(String admitStudentSchoolId) {
                this.admitStudentSchoolId = admitStudentSchoolId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getCreateDateBegin() {
                return createDateBegin;
            }

            public void setCreateDateBegin(String createDateBegin) {
                this.createDateBegin = createDateBegin;
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

            public String getCreateDateEnd() {
                return createDateEnd;
            }

            public void setCreateDateEnd(String createDateEnd) {
                this.createDateEnd = createDateEnd;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getEmailLike() {
                return emailLike;
            }

            public void setEmailLike(String emailLike) {
                this.emailLike = emailLike;
            }

            public String getEnterpriseType() {
                return enterpriseType;
            }

            public void setEnterpriseType(String enterpriseType) {
                this.enterpriseType = enterpriseType;
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

            public String getGraduationSpecialitiesLike() {
                return graduationSpecialitiesLike;
            }

            public void setGraduationSpecialitiesLike(String graduationSpecialitiesLike) {
                this.graduationSpecialitiesLike = graduationSpecialitiesLike;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getHeadPicLike() {
                return headPicLike;
            }

            public void setHeadPicLike(String headPicLike) {
                this.headPicLike = headPicLike;
            }

            public String getImUser() {
                return imUser;
            }

            public void setImUser(String imUser) {
                this.imUser = imUser;
            }

            public String getImUserLike() {
                return imUserLike;
            }

            public void setImUserLike(String imUserLike) {
                this.imUserLike = imUserLike;
            }

            public String getImUserPassword() {
                return imUserPassword;
            }

            public void setImUserPassword(String imUserPassword) {
                this.imUserPassword = imUserPassword;
            }

            public String getImUserPasswordLike() {
                return imUserPasswordLike;
            }

            public void setImUserPasswordLike(String imUserPasswordLike) {
                this.imUserPasswordLike = imUserPasswordLike;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getNickNameLike() {
                return nickNameLike;
            }

            public void setNickNameLike(String nickNameLike) {
                this.nickNameLike = nickNameLike;
            }

            public String getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPasswordLike() {
                return passwordLike;
            }

            public void setPasswordLike(String passwordLike) {
                this.passwordLike = passwordLike;
            }

            public String getPersonalSign() {
                return personalSign;
            }

            public void setPersonalSign(String personalSign) {
                this.personalSign = personalSign;
            }

            public String getPersonalSignLike() {
                return personalSignLike;
            }

            public void setPersonalSignLike(String personalSignLike) {
                this.personalSignLike = personalSignLike;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhoneLike() {
                return phoneLike;
            }

            public void setPhoneLike(String phoneLike) {
                this.phoneLike = phoneLike;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getRemarkLike() {
                return remarkLike;
            }

            public void setRemarkLike(String remarkLike) {
                this.remarkLike = remarkLike;
            }

            public String getTeacherPersonalCover() {
                return teacherPersonalCover;
            }

            public void setTeacherPersonalCover(String teacherPersonalCover) {
                this.teacherPersonalCover = teacherPersonalCover;
            }

            public String getTeacherPersonalCoverLike() {
                return teacherPersonalCoverLike;
            }

            public void setTeacherPersonalCoverLike(String teacherPersonalCoverLike) {
                this.teacherPersonalCoverLike = teacherPersonalCoverLike;
            }

            public String getTeacherPersonalIntroduce() {
                return teacherPersonalIntroduce;
            }

            public void setTeacherPersonalIntroduce(String teacherPersonalIntroduce) {
                this.teacherPersonalIntroduce = teacherPersonalIntroduce;
            }

            public String getTeacherPersonalIntroduceLike() {
                return teacherPersonalIntroduceLike;
            }

            public void setTeacherPersonalIntroduceLike(String teacherPersonalIntroduceLike) {
                this.teacherPersonalIntroduceLike = teacherPersonalIntroduceLike;
            }

            public String getTeacherTitle() {
                return teacherTitle;
            }

            public void setTeacherTitle(String teacherTitle) {
                this.teacherTitle = teacherTitle;
            }

            public String getTeacherTitleLike() {
                return teacherTitleLike;
            }

            public void setTeacherTitleLike(String teacherTitleLike) {
                this.teacherTitleLike = teacherTitleLike;
            }

            public String getTpId() {
                return tpId;
            }

            public void setTpId(String tpId) {
                this.tpId = tpId;
            }

            public String getTpNickName() {
                return tpNickName;
            }

            public void setTpNickName(String tpNickName) {
                this.tpNickName = tpNickName;
            }

            public String getTpType() {
                return tpType;
            }

            public void setTpType(String tpType) {
                this.tpType = tpType;
            }

            public String getTrueName() {
                return trueName;
            }

            public void setTrueName(String trueName) {
                this.trueName = trueName;
            }

            public String getTrueNameLike() {
                return trueNameLike;
            }

            public void setTrueNameLike(String trueNameLike) {
                this.trueNameLike = trueNameLike;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getWillSchoolId() {
                return willSchoolId;
            }

            public void setWillSchoolId(String willSchoolId) {
                this.willSchoolId = willSchoolId;
            }
        }
    }
}
