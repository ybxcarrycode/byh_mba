package com.xszj.mba.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.xszj.mba.R;


/***************************************************************************
 * <pre></pre>
 * 
 * @文件名称: OTPCountDown.java
 * @包 路 径： com.project.ysxandroid.utils
 * 
 * @类描述: 短信验证码按钮倒计时工具类
 * @版本: V1.5
 * @创建人： wangzhan
 * @创建时间：2015-7-30 上午1:38:43
 * 
 * 
 * 
 ***************************************************************************/

public class OTPCountDown extends CountDownTimer {

	private Button btnOtp;
	private Context context;

	/**
	 * 构造函数
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param btnOtp
	 *            显示倒计时的按钮对象
	 * @param context
	 * @param millisInFuture
	 *            倒计时时间,单位为毫秒
	 * @param countDownInterval
	 *            间隔,单位为毫秒
	 */
	public OTPCountDown(Button btnOtp, Context context, long millisInFuture,
						long countDownInterval) {

		super(millisInFuture, countDownInterval);
		this.btnOtp = btnOtp;
		this.context = context;

	}

	/**
	 * 倒计时结束
	 */
	@Override
	public void onFinish() {
		btnOtp.setText(context.getResources()
				.getString(R.string.str_btn_getOtp));
		btnOtp.setEnabled(true);

	}

	/**
	 * 倒计时触发
	 */
	@Override
	public void onTick(long millisUntilFinished) {

		btnOtp.setText(String.valueOf(millisUntilFinished / 1000)
				+ context.getResources().getString(R.string.tip_otp));
	}

}
