package com.xszj.mba.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xszj.mba.R;


/**
 * Created by zht on 2015/8/9.
 */
public class DialogDoubleHelper extends Dialog implements View.OnClickListener {

    protected Context context;
    private Activity activity;
    private TextView msg;
    private TextView left;
    private TextView right;
    private TextView vice_msg;

    private DialogDoubleHelperOnclickListener listener;

    public void setDialogOnclickListener(
            DialogDoubleHelperOnclickListener listener) {
        this.listener = listener;
    }

    public interface DialogDoubleHelperOnclickListener {
        public void setLeftOnClickListener(Dialog dialog);

        public void setRightOnClickListener(Dialog dialog);
    }

    public DialogDoubleHelper(Context context) {
        super(context, R.style.CommonRemindDialog);
        this.context = context;
        setHelperOwnerActivity();
        initView();
    }

    private void initView() {
        setContentView(R.layout.helper_dialog_double_view_layout);
        msg = (TextView) findViewById(R.id.dilaog_helper_double_msg);
        vice_msg = (TextView) findViewById(R.id.dialog_vice_msg);
        left = (TextView) findViewById(R.id.dilaog_helper_double_left);
        left.setOnClickListener(this);
        right = (TextView) findViewById(R.id.dilaog_helper_double_right);
        right.setOnClickListener(this);
    }


    private void setHelperOwnerActivity() {
        if (context != null) {
            try {
                activity = (Activity) context;
            } catch (Exception e) {
                activity = null;
                e.printStackTrace();
            }
        }
        if (activity != null) {
            setOwnerActivity(activity);
        }
    }


    @Override
    public void show() {
        if (activity != null && !activity.isFinishing() && !isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (activity != null && !activity.isFinishing() && isShowing()) {
            super.dismiss();
        }
    }

    public void setMsgTxt(String txt) {
        if (txt != null) {
            msg.setText(txt);
        }
    }

    public void setViceMsgTxt(String txt) {
        if (txt != null) {
            vice_msg.setVisibility(View.VISIBLE);
            vice_msg.setText(txt);
        }
    }

    public void setLeftTxt(CharSequence txt) {
        if (txt != null) {
            left.setText(txt);
        }
    }

    public void setRightTxt(CharSequence txt) {
        if (txt != null) {
            right.setText(txt);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dilaog_helper_double_left:
                if (listener != null) {
                    listener.setLeftOnClickListener(this);
                }
                break;
            case R.id.dilaog_helper_double_right:
                if (listener != null) {
                    listener.setRightOnClickListener(this);
                }
                break;
            default:
                break;
        }
    }
}
