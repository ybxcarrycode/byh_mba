package com.xszj.mba.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xszj.mba.R;


/**
 * Created by hehe on 2015/9/21.
 */
public class DialogProgressHelper extends Dialog {

    private AnimationDrawable spinner;
    protected Context context;
    private Activity activity;

    public DialogProgressHelper(Context context) {
        super(context);
    }

    public DialogProgressHelper(Context context, int theme) {
        super(context, theme);
        this.context = context;
        setHelperOwnerActivity();
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

    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public static DialogProgressHelper show(Context context, CharSequence message, boolean isload, boolean indeterminate, boolean cancelable,
                                            OnCancelListener cancelListener) {
        DialogProgressHelper dialog = new DialogProgressHelper(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.activity_dialog_progress);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        if (!isload) {
            dialog.findViewById(R.id.spinnerImageView).setVisibility(View.GONE);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //dialog.show();
        return dialog;
    }

    @Override
    public void dismiss() {
        if (activity != null && !activity.isFinishing() && isShowing()) {
            super.dismiss();
            if (spinner != null) {
                spinner.stop();
                spinner = null;
            }
        }

    }


}
