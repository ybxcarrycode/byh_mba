package com.xszj.mba.bean;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class PublishBbsModel extends BaseModel {


	private static final long serialVersionUID = -3499219360989969299L;
	
	
	public String twitterContent = "";
	public String userid = "";
	public String imaglist = "";
	public String topicId = "";
	public ArrayList<Bitmap> paths = new ArrayList<Bitmap>();
	
	public static PublishBbsModel copy(PublishBbsModel src){
		PublishBbsModel pb = new PublishBbsModel();
		pb.sid = src.sid;
		pb.twitterContent = src.twitterContent;
		pb.userid = src.userid;
		pb.imaglist = src.imaglist;
		pb.paths = src.paths;
		pb.topicId = src.topicId;
		return pb;
	}
}
