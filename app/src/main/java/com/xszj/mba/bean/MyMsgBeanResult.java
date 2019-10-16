package com.xszj.mba.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ybx on 2017/2/8.
 */

public class MyMsgBeanResult implements Serializable {

    /**
     * returnCode : 0
     * data : {"condition":{"userId":"1"},"page":"1","pageSize":"8","pageCount":"1","total":"8","list":[{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"3","messageId":"8","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"3","messageId":"7","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"3","messageId":"6","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"2","messageId":"5","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"2","messageId":"4","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"1","messageId":"3","messageStatus":"1"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"1","messageId":"2","messageStatus":"1"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-07 18:38:24","messageType":"1","messageId":"1","messageStatus":"1"}],"mysqlStartRow":"0"}
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
         * condition : {"userId":"1"}
         * page : 1
         * pageSize : 8
         * pageCount : 1
         * total : 8
         * list : [{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"3","messageId":"8","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"3","messageId":"7","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"3","messageId":"6","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"2","messageId":"5","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"2","messageId":"4","messageStatus":"0"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"1","messageId":"3","messageStatus":"1"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-06 18:55:21","messageType":"1","messageId":"2","messageStatus":"1"},{"messageUrl":"https://www.baidu.com","messageReceiveUser":"1","messageContent":"提前面试攻略技巧","messageReadDate":"2017-02-07 18:38:24","messageType":"1","messageId":"1","messageStatus":"1"}]
         * mysqlStartRow : 0
         */

        private ConditionBean condition;
        private String page;
        private String pageSize;
        private String pageCount;
        private String total;
        private String mysqlStartRow;
        private List<MyMsgBean> list;

        public ConditionBean getCondition() {
            return condition;
        }

        public void setCondition(ConditionBean condition) {
            this.condition = condition;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getMysqlStartRow() {
            return mysqlStartRow;
        }

        public void setMysqlStartRow(String mysqlStartRow) {
            this.mysqlStartRow = mysqlStartRow;
        }

        public List<MyMsgBean> getList() {
            return list;
        }

        public void setList(List<MyMsgBean> list) {
            this.list = list;
        }

        public static class ConditionBean {
            /**
             * userId : 1
             */

            private String userId;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }


    }
}
