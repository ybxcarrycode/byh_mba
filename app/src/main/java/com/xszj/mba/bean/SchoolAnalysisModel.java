package com.xszj.mba.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 学校分析学校模型
 * */
public class SchoolAnalysisModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id = "";
	public String name = "";
	public String engName;
	public String image = "";
	public String logo;
	public String backGroundImg = "";
	public String rank = "";
	public String buildTime = "";
	public String keyFieldCount = "";
	public String stuCount = "";
	public String masterCount = "";
	public String doctorCount = "";
	public String academicianCount = "";
	//学校概况
	public String summaryShort = "";
	/**
	 * 学费信息
	 * */
	public String tuitionDescShort = "";
	/**
	 * 就业信息
	 * */
	public String employmentDescShort = "";
	public ArrayList<String> features = null;
	/**
	 * 男生比例
	 * */
	public double boyRate = 0;
	public String aveScoreWen = "";//平均文
	public String aveScoreLi = "";//平均理
	//就业率
	public String jobLevel;
	public String preLimitScore;

	public ArrayList<StuSourceModel> stuSources = new ArrayList<StuSourceModel>();
	public ArrayList<String> admitBatches;
	public ArrayList<HisEnrollNumBean> hisEnrollNumBeans;

	public static SchoolAnalysisModel newInstance2(JSONObject json) {
		SchoolAnalysisModel sam = new SchoolAnalysisModel();
		sam.academicianCount = json.optString("academicianCount");
		sam.backGroundImg = json.optString("backGroundImg");
		sam.buildTime = json.optString("buildTime");
		sam.doctorCount = json.optString("doctorCount");
		sam.engName = json.optString("engName");
		JSONArray ja = json.optJSONArray("features");
		if (null != ja && 0 < ja.length()) {
			sam.features = new ArrayList<String>();
			for (int i = 0; i < ja.length(); ++i) {
				sam.features.add(ja.optString(i));
			}
		}
		sam.id = json.optString("id");
		sam.keyFieldCount = json.optString("keyFieldCount");
		sam.logo = json.optString("logo");
		sam.masterCount = json.optString("masterCount");
		sam.name = json.optString("name");
		sam.rank = json.optString("rank");
		sam.sid = sam.id;
		sam.stuCount = json.optString("stuCount");
		sam.stuSources = StuSourceModel.parseList(json.optJSONArray("stuSources"));
		sam.aveScoreLi = json.optString("liScore");
		sam.aveScoreWen = json.optString("wenScore");
		sam.hisEnrollNumBeans = HisEnrollNumBean.parseList(json.optJSONArray("admitCounts"));
		return sam;
	}

	public static ArrayList<SchoolAnalysisModel> parseList2(String content) {
		ArrayList<SchoolAnalysisModel> sams = null;

		try {
			JSONObject json = new JSONObject(content);
			json = json.optJSONObject("data");
			JSONArray ja = json.optJSONArray("colleges");
			if (null != ja && 0 < ja.length()) {
				sams = new ArrayList<SchoolAnalysisModel>();
				for (int i = 0; i < ja.length(); ++i) {
					sams.add(newInstance2(ja.optJSONObject(i)));
				}
			}

		} catch (Exception e) {
		}
		return sams;
	}

	public static SchoolAnalysisModel newInstance4Dt(String content, SchoolAnalysisModel source) {
		SchoolAnalysisModel sam = source;
		if (null == source) {
			sam = source = new SchoolAnalysisModel();
		}
		try {
			JSONObject json = new JSONObject(content);
			json = json.optJSONObject("data").optJSONObject("college");
			sam.id = json.optString("id");
			sam.name = json.optString("name");
			sam.logo = json.optString("logo");
			sam.engName = json.optString("engName");
			sam.image = json.optString("image");
			JSONArray ja = json.optJSONArray("features");
			if (null != ja && 0 < ja.length()) {
				sam.features = new ArrayList<String>();
				for (int i = 0; i < ja.length(); ++i) {
					sam.features.add(ja.optString(i));
				}
			}
			JSONArray batchArray = json.optJSONArray("admitBatches");
			if (null != batchArray && batchArray.length() > 0) {
				sam.admitBatches = new ArrayList<String>();
				for (int i = 0; i < batchArray.length(); i++) {
					sam.admitBatches.add(batchArray.optString(i));
				}
			}
			sam.summaryShort = json.optString("summaryShort");
			sam.buildTime = json.optString("buildTime");
			sam.keyFieldCount = json.optString("keyFieldCount");
			sam.stuCount = json.optString("stuNum");
			sam.doctorCount = json.optString("doctorNum");
			sam.academicianCount = json.optString("acadNum");
			sam.masterCount = json.optString("masterNum");
			sam.rank = json.optString("rank");
			sam.tuitionDescShort = json.optString("tuitionDescShort");
			sam.employmentDescShort = json.optString("employmentDescShort");
			sam.boyRate = json.optDouble("boyRate");
			sam.stuSources = StuSourceModel.parseList(json.optJSONArray("stuSources"));
			sam.aveScoreLi = json.optString("liScore");
			sam.aveScoreWen = json.optString("wenScore");
			sam.jobLevel = json.optString("jobLevel");
			sam.hisEnrollNumBeans = HisEnrollNumBean.parseList(json.optJSONArray("admitCounts"));
			sam.preLimitScore = json.optString("preLimitScore");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sam;
	}
}
