package com.unithon.cafee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Youngdo on 2016-07-30.
 */
public class SplashActivity extends AppCompatActivity {
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setting = getSharedPreferences("setting", 0);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(!setting.getString("nick","").isEmpty()){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this, NicknameActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        handler.sendEmptyMessageDelayed(0,3000);
    }
}
