package com.xszj.mba.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xszj.mba.activity.LoginNewActivity;
import com.xszj.mba.view.XsDialog;


public class CommonUtil {

	/**
	 * @param context
	 * @return 当前版本号
	 */
	public static String getVersionName(Context context) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "unknown";
	}

	/**
	 * @param context
	 * @return 当前版本号
	 */
	public static int getVersionCode(Context context) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void showLoginddl(final Context context) {
		final XsDialog noticeDialog = new XsDialog(context, "系统提示:", "亲,请先登录!", true, true, true);
		noticeDialog.setBtnOklistener(new XsDialog.BtnOKListener() {
			@Override
			public void clickOk() {
				context.startActivity(new Intent(context, LoginNewActivity.class));
			}
		});
		noticeDialog.setBtnCancelListener(new XsDialog.BtnCancelListener() {
			@Override
			public void clickCancel() {

			}
		});
		noticeDialog.show();
	}

	//判断网络是否连接
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}
