package com.xszj.mba.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ybx on 2017/2/7.
 */

public class AboutSchoolDetTeachBean implements Serializable {
    /**
     * returnCode : 0
     * data : {"pageCount":"1","list":[{"teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","focusNum":"3","userTypeName":"名校学长","nickName":"学长1","teacherTitle":"MBA资深专家","userId":"2","academyName":"北京大学","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg"}]}
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
         * pageCount : 1
         * list : [{"teacherPersonalIntroduce":"毕业于北京大学光华管理学院，华盛顿大学Foster商学院校友，涉猎IT、金融、互联网、房地产等多个行业，曾任IBM金融行业服务经理、申银万国非标业务北方区负责人、隆基泰和（01281.HK）集团董秘，北大光华最年轻的MBA。现专注教育领域，创办两家公司，一家\u201c博雅汇教育\u201d（www.byhmba.com）专注名校MBA申请，通过率90%。一家\u201c考哪儿\u201d专注在线教育领域，2016年已获得Pre-A轮投资。","focusNum":"3","userTypeName":"名校学长","nickName":"学长1","teacherTitle":"MBA资深专家","userId":"2","academyName":"北京大学","headPic":"http://api.51kkww.com/images/user_head/1934125132705_1440664526837.jpg"}]
         */

        private String pageCount;
        private List<AboutTechUpperBean> list;

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public List<AboutTechUpperBean> getList() {
            return list;
        }

        public void setList(List<AboutTechUpperBean> list) {
            this.list = list;
        }
    }
}
