/** 
* @ProjectName:CYFrameAndroid  
* @Title: Loadable.java 
* @Package com.xszj.stu.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author liuqi qiliu_17173@cyou-inc.com   
* @date 2014年7月6日 下午4:13:28 
* @version V1.0   
* Copyright (c) 2014搜狐公司-版权所有
*/ 
package com.xszj.mba.fragment;



/** 
 * @ClassName: Loadable 
 * @Description: 加载接口
 * @author liuqi qiliu_17173@cyou-inc.com
 * @date 2014年7月6日 下午4:13:28  
 */
public interface Loadable {
	<T> void onLoad(T t, int type);
}