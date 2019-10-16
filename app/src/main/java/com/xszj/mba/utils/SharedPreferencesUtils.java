package com.xszj.mba.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***************************************************************************
 * <pre></pre>
 *
 * @文件名称: StoreUtils.java
 * @包 路 径： com.yunshuxie.utils
 * @类描述: 数据存储工具类
 * @版本: V1.5
 * @创建人： swh
 * @创建时间：2015-9-6 下午3:21:01
 ***************************************************************************/

public class SharedPreferencesUtils {

    /**
     * 数据存储函数
     * <p>
     * setProperty
     * </p>
     *
     * @param context
     * @param key
     * @param value
     * @Description:以SP形式将数据存储于本地
     */
    public static void setProperty(Context context, String key, String value) {
        if (null == context) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 数据读取函数
     * <p>getProperty</p>
     *
     * @param context
     * @param key
     * @return
     * @Description: 将SP中函数读取出来
     */
    public static String getProperty(Context context, String key) {
        if (null == context) {
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);
        String value = new String();
        String localValue = sp.getString(key, value);
        return localValue;
    }

    /**
     * 保存Boolean类型的状态
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setIschecked(Context context, String key, Boolean value) {
        if (null == context) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static Boolean getIschecked(Context context, String key) {
        if (null == context) {
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);

        return sp.getBoolean(key, false);
    }

    /**
     * 保存int的状态
     *
     * @param context
     * @param key
     */

    public static void setIntValue(Context context, String key, int num) {
        if (null == context) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, num);
        editor.commit();

    }

    public static int getIntValue(Context context, String key) {
        if (null == context) {
            return -1;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);

        return sp.getInt(key, 0);
    }

    public static void setListProperty(Context context, String key, ArrayList<String> list) {
        if (null == context) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                sb.append(list.get(i) + ",");
            } else {
                sb.append(list.get(i));
            }
        }

        editor.putString(key, sb.toString());
        editor.commit();

    }

    public static ArrayList<String> getListProperty(Context context, String key) {
        if (null == context) {
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences("properties", Context.MODE_PRIVATE);
        List<String> list = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        String value = new String();

        String localValue = sp.getString(key, value);
        if (localValue != null) {
            String[] temp = null;
            temp = localValue.split(",");
            list = Arrays.asList(temp);
        }

        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i));
        }
        return arrayList;
    }

}
