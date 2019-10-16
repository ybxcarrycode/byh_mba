package com.xszj.mba.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


/***************************************************************************
 * <pre></pre>
 *
 * @文件名称: AndroidUtils.java
 * @包 路 径： com.project.ysxandroid.utils
 * @类描述:Android工具类,负责处理Toast,Dialog,进度框等
 * @版本: V1.5
 * @创建人： wangzhan
 * @创建时间：2015-7-28 上午2:26:31
 ***************************************************************************/

public class AndroidUtils {

    /**
     * Hint处理函数
     * <p>
     * processHint
     * </p>
     *
     * @param edt      EditText对象
     * @param hasFocus 是否有焦点
     * @Description: 处理EditText中hint的显示
     */
    public static void processHint(EditText edt, boolean hasFocus) {
        String hint;
        if (hasFocus) {
            hint = edt.getHint().toString();
            edt.setTag(hint);
            edt.setHint("");
        } else {
            hint = edt.getTag().toString();
            edt.setHint(hint);
        }
    }

    /**
     * 显示Toast
     * <p>
     * showToast
     * </p>
     *
     * @param context
     * @param msg
     * @Description:
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示对话框
     * <p>
     * showTipDialog
     * </p>
     *
     * @param context
     * @param title       标题
     * @param msg         显示的消息
     * @param strBtnPos   按钮文字
     * @param posListener 按钮响应
     * @param cancelable  对话框可取消标识
     * @Description:显示单个按钮的对话框
     */
    public static void showTipDialog(Context context, String title, String msg,
                                     String strBtnPos, OnClickListener posListener, boolean cancelable) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton(strBtnPos, posListener)
                .setCancelable(cancelable).show();
    }

    /**
     * 显示选择对话框
     * <p>
     * showSelectDialog
     * </p>
     *
     * @param context
     * @param title       标题
     * @param msg         显示的消息
     * @param strPosBtn   确认按钮文字
     * @param posListener 确认按钮响应
     * @param strNegBtn   取消按钮文字
     * @param negListener 取消按钮响应
     * @param canceable   对话框可取消标识
     * @Description: 显示供用户选择的两个按钮的对话框
     */
    public static void showSelectDialog(Context context, String title,
                                        String msg, String strPosBtn, OnClickListener posListener,
                                        String strNegBtn, OnClickListener negListener, boolean canceable) {
        if (null == context) {
            return;
        }
        Activity activity = (Activity) context;
        if (null == activity || activity.isFinishing()) {
            return;
        }
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton(strPosBtn, posListener)
                .setNegativeButton(strNegBtn, negListener)
                .setCancelable(canceable).show();
    }

    /**
     * 展示进度框
     * <p>
     * showProcessDialog
     * </p>
     *
     * @param context
     * @param msg     消息
     * @return
     * @Description:
     */
    public static ProgressDialog showProcessDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (progressDialog != null) {
            //Activity activity = progressDialog.getOwnerActivity();
           // if (activity != null && !activity.isFinishing()) {
                progressDialog.show();
           // }
            return progressDialog;
        } else {
            return null;
        }
    }

    /**
     * 关闭进度框
     * <p>
     * closeProcessDialog
     * </p>
     *
     * @param progressDialog
     * @Description:
     */
    public static void closeProcessDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            Activity activity = progressDialog.getOwnerActivity();
            if (activity != null && !activity.isFinishing()) {
                progressDialog.dismiss();
            }
        }

    }


//    /**
//     * 截取验证码函数
//     * <p>showValidCode</p>
//     *
//     * @param mContext
//     * @param edtOtp
//     * @Description:将收到的验证码显示在输入框中
//     */
//    public static void showValidCode(Context mContext, final EditText edtOtp) {
//        final String flagYSX = mContext.getResources().getString(
//                R.string.otp_flag_yunshuxie);
//        final String flagVCode = mContext.getResources().getString(
//                R.string.otp_flag_validate);
//
//        SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();
//        mSMSBroadcastReceiver
//                .setOnReceivedMessageListener(new MessageListener() {
//
//                    @Override
//                    public void OnReceived(String message) {
//                        if (message.contains(flagYSX)
//                                && message.contains(flagVCode)) {
//
//                            String otp = "";
//                            for (int i = 0; i < message.length(); i++) {
//                                if ((message.charAt(i) >= '0')
//                                        && (message.charAt(i) <= '9')) {
//                                    otp = message.substring(i, i + 6);
//                                    edtOtp.setText(otp);
//                                    break;
//                                }
//                            }
//
//                        }
//
//                    }
//                });
//    }

}
