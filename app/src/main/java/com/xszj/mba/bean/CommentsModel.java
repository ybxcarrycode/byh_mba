package com.xszj.mba.bean;

import org.json.JSONObject;

public class CommentsModel extends BaseModel {

	private static final long serialVersionUID = -3499219360989969299L;
	public String content = "";
	public String commentDate = "";
	public String headImgUrl = "";
	public String nickName = "";
	public String uId = "";
	public String userTypeId = "";
	public String roleTag1 = "";
	public String roleTag2 = "";
	public String createTimeStr = "";
	public int likeCount;
	public String postCommentId;
	public boolean hasLiked;
	public String commentToNickName;
	public String commentTouId;

	public static CommentsModel createFromJson(JSONObject json) {
		CommentsModel ad = new CommentsModel();
		ad.postCommentId = json.optString("id");
		ad.headImgUrl = json.optString("headImgUrl");
		ad.uId = json.optString("uId");
		ad.nickName = json.optString("nickName");
		ad.userTypeId = json.optString("userTypeId");
		ad.roleTag1 = json.optString("roleTag1");
		ad.roleTag2 = json.optString("roleTag2");
		ad.commentDate = json.optString("createTime");
		ad.content = json.optString("content");
		ad.createTimeStr = json.optString("createTimeStr");
		ad.likeCount = json.optInt("likeCount");
		ad.hasLiked = 1 == json.optInt("attitudeStatus");
		ad.commentToNickName = json.optString("commentToNickName");
		ad.commentTouId = json.optString("commentTouId");
		return ad;
	}
}
