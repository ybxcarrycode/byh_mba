package com.xszj.mba.activity;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.view.GlobalTitleView;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/16.
 */

public class LawActivity extends BaseActivity {
    private GlobalTitleView titleView;
    private TextView txvRegProto;
    @Override
    protected int getContentViewResId() {

        return R.layout.activity_law;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        txvRegProto = (TextView) findViewById(R.id._txv_protocol_content);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("使用条款");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
        showProtocol();
    }


    @Override
    protected void initListeners() {

    }

    private void showProtocol() {
        String fileName = "Law.txt";
        AssetManager assetManager = null;
        InputStream in = null;
        int length = 0;
        String result = "";
        byte[] buff = null;
        Resources r = LawActivity.this.getResources();
        assetManager = r.getAssets();
        try {
            in = assetManager.open(fileName);
            length = in.available();
            buff = new byte[length];
            in.read(buff);
            result = EncodingUtils.getString(buff, "UTF-8");

        } catch (IOException e) {
            result = e.getMessage();
        } catch (Exception e1) {
            result = e1.getMessage();
        }

        txvRegProto.setText(result);
    }
}
