/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xszj.mba.utils;


import android.app.Activity;
import android.content.Context;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbDialogUtil.java
 * 描述：Dialog工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2014-07-02 下午11:52:13
 */

public class AbDialogUtil {


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
    public static DialogProgressHelper showProcessDialog(Context context, String msg) {
        DialogProgressHelper progressDialog = DialogProgressHelper.show(context, msg, true, false, true, null);
        if (progressDialog != null) {
            Activity activity = progressDialog.getOwnerActivity();
            if (activity != null && !activity.isFinishing()) {
                progressDialog.show();
            }
            return progressDialog;
        } else {
            return null;
        }
    }

    public static DialogProgressHelper showProcessDialog11(Context context, String msg) {
        DialogProgressHelper progressDialog = DialogProgressHelper.show(context, msg, true, false, false, null);
        if (progressDialog != null) {
            Activity activity = progressDialog.getOwnerActivity();
            if (activity != null && !activity.isFinishing()) {
                progressDialog.show();
            }
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
     * @param
     * @Description:
     */
    public static void closeProcessDialog(DialogProgressHelper dialogProgressHelper) {
        if (dialogProgressHelper != null) {
            Activity activity = dialogProgressHelper.getOwnerActivity();
            if (dialogProgressHelper.isShowing() && activity != null && !activity.isFinishing()) {
                dialogProgressHelper.dismiss();
            }
        }
    }

}
