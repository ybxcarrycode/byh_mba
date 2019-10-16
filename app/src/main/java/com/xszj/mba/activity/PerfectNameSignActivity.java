package com.xszj.mba.activity;


import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;

/**
 * Created by Administrator on 2017/2/14.
 */

public class PerfectNameSignActivity extends BaseActivity {

    private ImageButton main_top_left;
    private TextView main_top_right,main_top_title;
    private EditText et_user_freeback_content;
    private String content;
    private String type;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_perfect_name_sign;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void bindViews() {
        main_top_left = (ImageButton) findViewById(R.id.main_top_left);
        main_top_right = (TextView) findViewById(R.id.main_top_right);
        main_top_title = (TextView)findViewById(R.id.main_top_title);
        et_user_freeback_content = (EditText) findViewById(R.id.et_user_freeback_content);
    }

    @Override
    protected void initViews() {
        if (type.equals("1")){
            et_user_freeback_content.setHint("请输入2-12个字");
            main_top_title.setText("更改名字");
            et_user_freeback_content.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(12)});
        }else{
            et_user_freeback_content.setHint("请输入30个字");
            main_top_title.setText("个性签名");
            et_user_freeback_content.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(30)});
        }

    }

    @Override
    protected void hideSoftInput() {
        super.hideSoftInput();

    }

    @Override
    protected void initListeners() {
        main_top_left.setOnClickListener(this);
        main_top_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.main_top_left:
                finish();
                break;

            case R.id.main_top_right:
                content = et_user_freeback_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    showToast("请输入内容吧");
                }else{
                    setResult(RESULT_OK, new Intent().putExtra("content", content));
                    finish();
                }
                break;
        }
    }
}
