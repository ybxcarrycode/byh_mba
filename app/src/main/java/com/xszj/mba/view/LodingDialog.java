package com.xszj.mba.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.xszj.mba.R;


/**
 * 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * 
 * 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 **/
public class LodingDialog extends Dialog {

	private Context context = null;
	private static LodingDialog customProgressDialog = null;

	private LodingDialog(Context context) {
		super(context);
		this.context = context;
	}

	private LodingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public static LodingDialog createDialog(Context context) {
		customProgressDialog = new LodingDialog(context, R.style.LodingDialog);
		customProgressDialog.setContentView(R.layout.dialog_loding);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		return customProgressDialog;
	}

	public static LodingDialog createUploadPicDialog(Context context) {
		customProgressDialog = new LodingDialog(context, R.style.LodingDialog);
		customProgressDialog.setContentView(R.layout.dialog_loding);
		TextView id_tv_loadingmsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		id_tv_loadingmsg.setText("正在上传...");
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}
		//		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		//		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		//		animationDrawable.start();
		Animation anim = null;
		ImageView loadingView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		anim = AnimationUtils.loadAnimation(context, R.anim.round_loading);
		loadingView.startAnimation(anim);
	}

	/**
	 * 
	 * setMessage 提示内容
	 * 
	 * @param strMessage
	 * 
	 * @return
	 */
	public LodingDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}
		return customProgressDialog;
	}

	@Override
	public void show() {
		super.show();
		Animation anim = null;
		//		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		//		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		//		animationDrawable.start();
		ImageView loadingView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		//			animation = (AnimationDrawable) loadingView.getBackground();
		Log.i("yxj", context + "---" + R.anim.round_loading);
		anim = AnimationUtils.loadAnimation(context, R.anim.round_loading);

		//			mProgressBar.setIndeterminate(true);
		loadingView.startAnimation(anim);
	}

}
