/**
* @author yinxuejian
* @version 创建时间：2015-12-9 下午4:51:36
* 
*/

package com.xszj.mba.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HisEnrollNumBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String enrollYear;
	public String admitNum;

	public static ArrayList<HisEnrollNumBean> parseList(JSONArray ja) {
		ArrayList<HisEnrollNumBean> ssm = null;
		if (null != ja && 0 < ja.length()) {
			ssm = new ArrayList<HisEnrollNumBean>();
			for (int i = 0; i < ja.length(); ++i) {
				ssm.add(newInstance(ja.optJSONObject(i)));
			}
		}
		return ssm;
	}

	public static HisEnrollNumBean newInstance(JSONObject json) {
		HisEnrollNumBean s = new HisEnrollNumBean();
		s.enrollYear = json.optString("enrollYear");
		s.admitNum = json.optString("admitNum");
		return s;
	}
}
