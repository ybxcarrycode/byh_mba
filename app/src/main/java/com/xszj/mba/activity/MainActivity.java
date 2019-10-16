package com.xszj.mba.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.ui.fragments.CommunityMainFragment;
import com.xszj.mba.R;
import com.xszj.mba.adapter.SelcectPagerAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.fragment.AllTopicFragment;
import com.xszj.mba.fragment.FindFragment;
import com.xszj.mba.fragment.HomeFragment;
import com.xszj.mba.fragment.KaoQuanFragment;
import com.xszj.mba.fragment.MeFragment;
import com.xszj.mba.fragment.MentersFragment;
import com.xszj.mba.runtimepermissions.PermissionsManager;
import com.xszj.mba.runtimepermissions.PermissionsResultAction;
import com.xszj.mba.utils.NmLoginUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.view.NoTouchViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class MainActivity extends BaseActivity {

    protected Context context;

    private int index;
    private int currentTabIndex=2;
    private Button[] mTabs;
    private FindFragment findFragment;
    private MeFragment meFragment;
    private MentersFragment mentersFragment;
    private HomeFragment emptyFragment;
    private AllTopicFragment communityMainFragment;
    private NoTouchViewPager main_tab_pager;
    private List<Fragment> list;

    final CommunitySDK mCommSDK = CommunityFactory.getCommSDK(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissions();

        // 等待同步数据完成
        boolean syncCompleted = LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(new Observer<Void>() {
            @Override
            public void onEvent(Void v) {
                DialogMaker.dismissProgressDialog();
            }
        });

        Log.e("main", "sync completed = " + syncCompleted);
        if (!syncCompleted) {
            DialogMaker.showProgressDialog(MainActivity.this, getString(R.string.prepare_data)).setCanceledOnTouchOutside(false);
        }

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode> () {
                    public void onEvent(StatusCode status) {
                        // 判断在线状态，如果为被其他端踢掉，做登出操作
                        Log.e("ddddd",status+"");
                        if (status ==StatusCode.UNLOGIN){
                            String imUser = SharedPreferencesUtils.getProperty(context, "imUser");
                            String imPwd =SharedPreferencesUtils.getProperty(context, "imPwd");
                            if (imUser!=null && imPwd!=null){
                                NmLoginUtils.nmLogin(context, imUser, imPwd);
                            }
                        }
                    }
                }, true);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews() {
        main_tab_pager = (NoTouchViewPager) findViewById(R.id.main_tab_pager);
        //xinjia
        mTabs = new Button[5];
        mTabs[0] = (Button) findViewById(R.id.btn_scriptures);
        mTabs[1] = (Button) findViewById(R.id.btn_introduction);
        mTabs[2] = (Button) findViewById(R.id.btn_common);
        mTabs[3] = (Button) findViewById(R.id.btn_homework);
        mTabs[4] = (Button) findViewById(R.id.btn_apply_class);

    }

    @Override
    protected void initViews() {
        mTabs[currentTabIndex].setSelected(true);
        communityMainFragment = new AllTopicFragment();
        //communityMainFragment.setBackButtonVisibility(View.INVISIBLE);
        findFragment = new FindFragment();
        meFragment = new MeFragment();
        mentersFragment = new MentersFragment();
        emptyFragment = new HomeFragment();

        list = new ArrayList<Fragment>();
        list.add(communityMainFragment);
        list.add(mentersFragment);
        list.add(emptyFragment);
        list.add(findFragment);
        list.add(meFragment);

        main_tab_pager.setAdapter(new SelcectPagerAdapter(getSupportFragmentManager(), list));
        main_tab_pager.setCurrentItem(currentTabIndex);
    }

    @Override
    protected void initListeners() {
    }


    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scriptures://社区

                index = 0;
                if (currentTabIndex == index) {
                    return;
                }

                break;
            case R.id.btn_introduction://寻师

                index = 1;
                if (currentTabIndex == index) {
                    return;
                }

                break;
            case R.id.btn_common://首页
                index = 2;
                if (currentTabIndex == index) {
                    return;
                }
                break;

            case R.id.btn_homework://新闻
                index = 3;
                if (currentTabIndex == index) {
                    return;
                }
                break;

            case R.id.btn_apply_class://我
                index = 4;
                if (currentTabIndex == index) {
                    return;
                }
                break;
            default:
                break;
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        main_tab_pager.setCurrentItem(index);
        currentTabIndex = index;
    }

    /**
     * 重写back建
     */
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast("    再按一次退出程序    ");
                mExitTime = System.currentTimeMillis();
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return super.onKeyDown(keyCode, event);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
