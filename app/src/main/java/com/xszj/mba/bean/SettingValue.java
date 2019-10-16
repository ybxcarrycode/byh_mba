package com.xszj.mba.bean;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 系统配置参数
 */
public class SettingValue {

    private static String content = "{\"returnCode\":\"0\",\"data\":[{\"academyId\":\"1\",\"academyName\":\"北京大学\"},{\"academyId\":\"2\",\"academyName\":\"清华大学\"},{\"academyId\":\"3\",\"academyName\":\"四川大学\"},{\"academyId\":\"4\",\"academyName\":\"南开大学\"},{\"academyId\":\"5\",\"academyName\":\"人民大学\"}],\"returnMsg\":\"操作成功\"}";
    public static ArrayList<DlgDataPicker> expertSchool = new ArrayList<DlgDataPicker>();

    public static void initSettingValue(final Context mContext) {

        String url = ServiceUtils.SERVICE_ABOUT_HOME+"/v1/consult/expertAcademyList.json?";
        Log.e("dddddd",url);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                content = responseInfo.result;
                if (content == null || content.equals("")){
                    initListValueFromLocal(mContext);
                }else{
                    try {
                        JSONObject json = new JSONObject(content);
                        String code = json.optString("returnCode");
                        if (code.equals("0")){
                            save(content, mContext);
                            parse(content);
                        }
                    } catch (Exception e) {
                        // Log.e("e", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                initListValueFromLocal(mContext);
            }
        });



    }


    private static void parse(String content) {
        try {
            ExpertAcademyListBean expertAcademyListBean= JsonUtil.parseJsonToBean(content,ExpertAcademyListBean.class);


            insertValue(expertAcademyListBean, expertSchool);

        } catch (Exception e) {
            // Log.e("e", e.getMessage());
        }
    }

    private static void insertValue(ExpertAcademyListBean pici, ArrayList<DlgDataPicker> setttings) throws JSONException {
        if (null != pici && 0 < pici.getData().size()) {
            setttings.clear();
            for (int i = 0; i < pici.getData().size(); ++i) {
                DlgDataPicker s = new DlgDataPicker();
                s.menuStr = pici.getData().get(i).getAcademyName();
                s.sid = pici.getData().get(i).getAcademyId();
                setttings.add(s);
            }
        }
    }



    public static void save(String content, Context mContext) {

        SharedPreferencesUtils.setProperty(mContext, "setting_value", content);
    }


    private static void initListValueFromLocal(Context mContext) {
        String content = getLocal(mContext);
        if (TextUtils.isEmpty(content)) {

            return;
        }
        parse(content);
    }

    private static String getLocal(Context mContext) {

        return SharedPreferencesUtils
                .getProperty(mContext, "setting_value");
    }
}
