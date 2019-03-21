package com.nao.otp_demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.nao.otp_demo.R;
import com.nao.otp_demo.bean.User;
import com.nao.otp_demo.util.TotpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yzou on 3/21/2019
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.userName)
    EditText userNameText;

    @BindView(R.id.otpSk)
    EditText skText;

    @OnClick(R.id.login)
    public void login(){
        Intent intent = new Intent(this,OtpActivity.class);
        User user = new User();
        user.setUsername(userNameText.getText().toString());
        user.setOtpsk(skText.getText().toString());
        intent.putExtra("userName",user.getUsername());
        intent.putExtra("otpSk",user.getOtpsk());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
    }
}
