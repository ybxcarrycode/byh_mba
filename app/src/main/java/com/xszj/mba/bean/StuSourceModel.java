package com.xszj.mba.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 生源所占比率模型
 * */
public class StuSourceModel extends BaseModel {
	public String collegeId = "";
	public String stuArea = "";
	public double stuRatio = 0;
	public String status = "";

	public static ArrayList<StuSourceModel> parseList(JSONArray ja) {
		ArrayList<StuSourceModel> ssm = null;
		if (null != ja && 0 < ja.length()) {
			ssm = new ArrayList<StuSourceModel>();
			for (int i = 0; i < ja.length(); ++i) {
				ssm.add(newInstance(ja.optJSONObject(i)));
			}
		}
		return ssm;
	}

	public static StuSourceModel newInstance(JSONObject json) {
		StuSourceModel s = new StuSourceModel();
		s.collegeId = json.optString("collegeId");
		s.sid = s.collegeId;
		s.status = json.optString("status");
		s.stuArea = json.optString("stuArea");
		s.stuRatio = json.optDouble("stuRatio");
		return s;
	}
}
