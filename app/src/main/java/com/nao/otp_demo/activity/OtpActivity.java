package com.nao.otp_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nao.otp_demo.R;
import com.nao.otp_demo.bean.User;
import com.nao.otp_demo.util.TotpUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpActivity extends AppCompatActivity {
    //初始化标志
    static boolean first = true;

    //时间戳
    static long time;

    @BindView(R.id.otp)
    TextView otpText;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);

        refresh();
        new Thread(mRunnable).start();
    }

    private void refresh() {
        //获取user信息
        Intent intent = getIntent();
        User user = new User();
        user.setUsername(intent.getStringExtra("userName"));
        user.setOtpsk(intent.getStringExtra("otpSk"));
        //生成otp
        otpText.setText(TotpUtil.generate(user.getOtpsk()));
    }

    //调用定时刷新函数
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refresh();
        }
    };

    //实现定时刷新
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    //初始化
                    if (first) {
                        long X = 30;//时间间隔30s
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        df.setTimeZone(TimeZone.getTimeZone("UTC"));
                        long currentTime = System.currentTimeMillis() / 1000L;
                        time = currentTime % X * 1000;
                        first = false;
                    } else {
                        time = 0;
                    }
                    progressBar.setProgress((int) time / 1000);
                    while (time < 30000) {
                        Thread.sleep(1000);
                        time += 1000;
                        progressBar.setProgress((int) time / 1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }
    };
}
