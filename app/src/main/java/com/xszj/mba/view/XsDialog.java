package com.xszj.mba.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.xszj.mba.R;

/**
 * 
* @ClassName: XsDialog 
* @Description: 统一用这个弹出框支持自定义内容,按钮,以及按钮文字,支持是否不再提醒
* @author liuqi qiliu_17173@cyou-inc.com
* @date 2014年7月26日 下午12:17:59
 */
public class XsDialog extends Dialog {
    
    private Context mContext;
    
	private TextView tv_title;
	
	private TextView tv_content;
	
	private Button btn_ok;
	
	private Button btn_cancel;
	
	private BtnOKListener btnOklistener;
	
	private BtnCancelListener btnCancelListener;
	
	private TextView tv_msg;//存放如：版本更新的详细信息
	
	private View button_line;
	
	private LinearLayout dialog_bt_layout;
	
	private CheckBox checkBox;
	

	public void setBtnOklistener(BtnOKListener btnOklistener) {
		this.btnOklistener = btnOklistener;
	}
	
	public void setBtnCancelListener(BtnCancelListener btnCancelListener) {
		this.btnCancelListener = btnCancelListener;
	}

	public XsDialog(final Context context, String title, String content, boolean needBtnOk, boolean needBtnCancel, boolean isShowTitle) {
		super(context, R.style.xs_dialog);
		if(!isShowTitle){
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		this.setCancelable(true);	
		this.setContentView(R.layout.layout_xsdialog);
		mContext = context;
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		lp.gravity = Gravity.CENTER_HORIZONTAL;
		window.setAttributes(lp);
		buildView();
		tv_title.setText(title);
		tv_content.setText(content);
		//按钮都不使用时，隐藏分割线
		if(!needBtnCancel && !needBtnOk) {
			dialog_bt_layout.setVisibility(View.GONE);
		}
		if(needBtnCancel){
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					XsDialog.this.dismiss();
					if(btnCancelListener!=null){
						btnCancelListener.clickCancel();
					}
				}
			});
		}else{
			btn_cancel.setVisibility(View.GONE);
			button_line.setVisibility(View.GONE);
		}
		if(needBtnOk){
			btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(btnOklistener==null){
						XsDialog.this.dismiss();
						return;
					}
					btnOklistener.clickOk();
					XsDialog.this.dismiss();
				}
			});
		}else{
			btn_ok.setVisibility(View.GONE);
			button_line.setVisibility(View.GONE);
		}
	}
	
	private void buildView(){
		tv_title=(TextView) findViewById(R.id.tv_dialog_title);
		tv_content=(TextView) findViewById(R.id.tv_dialog_content);
		btn_ok=(Button) findViewById(R.id.btn_dialog_ok);
		btn_cancel=(Button) findViewById(R.id.btn_dialog_cancel);
		tv_msg=(TextView) findViewById(R.id.tv_dialog_message);
		button_line = findViewById(R.id.separator);
		dialog_bt_layout = (LinearLayout) findViewById(R.id.dialog_bt_layout);
		checkBox = (CheckBox) findViewById(R.id.tv_dialog_check);
	}
	public void setMessage(String msg){
		tv_msg.setVisibility(View.VISIBLE);
		tv_msg.setText(msg);
	}
	
	public static interface BtnOKListener{
		void clickOk();
		
	}
	public static interface BtnCancelListener{
		void clickCancel();
		
	}
	
	public static interface CheckChangeListener{
        void checkChange();
        
    }
	
	public void setBtnOkText(String text) {
		btn_ok.setText(text);
	}
	
	public void setBtnCancelText(String text) {
		btn_cancel.setText(text);
	}
	
	public void setBtnOkText(int resId) {
		btn_ok.setText(resId);
	}
	
	public void setBtnCancelText(int resId) {
		btn_cancel.setText(resId);
	}
	
	public void hideCheckBox(){
	    checkBox.setVisibility(View.GONE);
	}
	
	public void showCheckBox(final String checkFlag){
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }
	
	/**
	 * 将view放进对话框中
	 */
	public XsDialog setView(View view) {
		// 1.获得message所在的布局
		ViewGroup messageLayout = (ViewGroup)findViewById(R.id.messageLl);
		// 2.移除TextView
		messageLayout.removeAllViews();
		// 3.添加新的View
		LinearLayout.LayoutParams layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		messageLayout.addView(view, layoutParamsFW);
		return this;
	}
}
