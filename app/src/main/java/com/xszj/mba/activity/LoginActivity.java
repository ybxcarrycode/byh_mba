package com.xszj.mba.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xszj.mba.R;
import com.xszj.mba.activity.presenter.LoginPresenterImpl;
import com.xszj.mba.activity.view.LoginView;
import com.xszj.mba.utils.AbDialogUtil;
import com.xszj.mba.utils.DialogProgressHelper;
import com.xszj.mba.utils.NmLoginUtils;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Administrator on 2016/11/11.
 */
public class LoginActivity extends Activity implements LoginView,View.OnClickListener{

    private EditText login_num,login_pwd;
    private Button login;
    private TextView reg;
    private LoginPresenterImpl loginPresenter;
    private DialogProgressHelper dialogProgressHelper;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        initView();


    }

    private void initView() {
        login_num = (EditText)findViewById(R.id._edt_login_num);
        login_pwd = (EditText)findViewById(R.id._edt_login_pwd);
        login = (Button) findViewById(R.id._btn_login_login);
        login.setOnClickListener(this);
        reg = (TextView) findViewById(R.id._txv_login_reg);
        reg.setOnClickListener(this);
        loginPresenter = new LoginPresenterImpl(this);

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {
        AbDialogUtil.closeProcessDialog(dialogProgressHelper);
    }

    @Override
    public void showSuccess(String content) {
        AbDialogUtil.closeProcessDialog(dialogProgressHelper);
        try {
            JSONObject jsonObject = new JSONObject(content);
            if(jsonObject.has("returnCode")) {
                String code = jsonObject.optString("returnCode");
                String msg = jsonObject.optString("returnMsg");
                if(code.equals("0")){
                    NmLoginUtils.nmLogin(LoginActivity.this,"swh124","123456");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id._btn_login_login:
                dialogProgressHelper =AbDialogUtil.showProcessDialog(LoginActivity.this,"登陆中.......");
                loginPresenter.login(login_num.getText().toString(),login_pwd.getText().toString());
                break;

            case R.id._txv_login_reg:

                break;
        }
    }
}
