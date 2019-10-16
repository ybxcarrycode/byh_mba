package com.xszj.mba.utils;

/**
 * Created by Administrator on 2016/12/7.
 */

import android.content.Context;
import android.util.Log;

import com.xszj.mba.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class ShareUtils {


    //判断网络是否连接
    public static void share(Context context, final String url,String title,String text) {
        if (context != null) {
            Log.e("ddddd",url+"==="+title);
            ShareSDK.initSDK(context);
            OnekeyShare oks = new OnekeyShare();
            oks.addHiddenPlatform("ShortMessage");
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle(title);
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(url);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(text);
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/icon.png");//确保SDcard下面存在此张图片
            oks.setImageUrl("http://byh-mobile.oss-cn-shanghai.aliyuncs.com/2222222222222222222222222222.png");
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(url);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(context.getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(url);
            // 启动分享GUI

            oks.setCallback(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {


                    //Utils.shareComplete(isShowShare + isNum, regNumber, 5, platform.getName());
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {

                }

                @Override
                public void onCancel(Platform platform, int i) {

                }
            });
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                    if ("SinaWeibo".equals(platform.getName())) {
                        //paramsToShare.setShareType(Platform.SHARE_TEXT);
                        paramsToShare.setText("重点商学院互动直播，名校MBA校友在线答疑，快来和我一起备考名校MBA吧!" + url);
                    }
                }
            });
            oks.show(context);
        }
    }
}
