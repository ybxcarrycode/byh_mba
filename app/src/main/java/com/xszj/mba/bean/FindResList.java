package com.xszj.mba.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ybx on 2016/12/27.
 */

public class FindResList implements Serializable {

    /**
     * returnCode : 0
     * data : [{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.baidu.com","newsId":"5","newsAuthor":"测试2","newsTitle":"咨询标题2"},{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.tmall.com","newsId":"4","newsAuthor":"测试1","newsTitle":"咨询标题1"},{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.jd.com","newsId":"3","newsAuthor":"测试2","newsTitle":"咨询标题2"},{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.vipshop.com","newsId":"1","newsAuthor":"测试1","newsTitle":"咨询标题1"}]
     * returnMsg : 操作成功
     */

    private String returnCode;
    private String returnMsg;
    private List<FindBean> data;

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

    public List<FindBean> getData() {
        return data;
    }

    public void setData(List<FindBean> data) {
        this.data = data;
    }

}
