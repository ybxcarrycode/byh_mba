package com.xszj.mba.bean;

import java.util.ArrayList;

public class DlgDataPicker extends BaseModel {

	private static final long serialVersionUID = 1520553931948057039L;
	public String menuStr = "";
	public boolean isSelected = false;
	public ArrayList<DlgDataPicker> datas;
	public DlgDataPicker() {
		super();
	}
	public DlgDataPicker(String menuStr) {
		super();
		this.menuStr = menuStr;
	}


	public DlgDataPicker(String menuStr,String id) {
		super();
		this.menuStr = menuStr;
		this.sid = id;
	}


	@Override
	public String toString() {
		return menuStr;
	}
	
	public DlgDataPicker copy(DlgDataPicker data){
		DlgDataPicker mData = new DlgDataPicker();
		mData.menuStr =data.menuStr;
		mData.sid =data.sid;
		return mData;
	}

	public static DlgDataPicker getSelectFirst(ArrayList<DlgDataPicker> datas){
		DlgDataPicker d = null;
		for(DlgDataPicker dp : datas){
			if(dp.isSelected){
				return dp;
			}
		}
		return d;
	}
	
}
