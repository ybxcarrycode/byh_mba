package com.xszj.mba.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/10.
 * 意向学校 毕业院校，企业类别类
 *
 */


public class PerfectSchoolAndTypeBean implements Serializable{

    private String dictionaryName;
    private String dictionaryType;
    private String sortCode;
    private String dictionaryId;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
}
