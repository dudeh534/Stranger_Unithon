package com.unithon.cafee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Youngdo on 2016-07-30.
 */
public class NicknameActivity extends AppCompatActivity {
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    private InputMethodManager imm;
    RestClient restClient;
    RequestParams params;
    String phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        restClient = new RestClient(this);
        final EditText nick = (EditText) findViewById(R.id.nickname);
        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();
        params = new RequestParams();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final ImageView imageView = (ImageView) findViewById(R.id.ok);
        TelephonyManager telManager = (TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
        phoneNum = telManager.getLine1Number();
        nick.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    imageView.setVisibility(View.VISIBLE);
                    imm.hideSoftInputFromWindow(nick.getWindowToken(), 0);
                    // the user is done typing.

                    return true; // consume.

                }
                return false; // pass on to other listeners.
            }
        });

        Button makeNick = (Button) findViewById(R.id.makenick);
        makeNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nick.getText().toString().isEmpty()) {
                    Toast.makeText(NicknameActivity.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("nick", nick.getText().toString());

                    params.put("nickname", nick.getText().toString());
                    if(phoneNum == null){
                        params.put("phone_number", "0000000000");
                    }else {
                        params.put("phone_number", phoneNum);
                    }
                    restClient.post("user", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {

                                editor.putString("user_id", response.getString("user_id"));
                                editor.commit();
                                Log.e("adsf11111", "Asfssf");
                                Intent intent = new Intent(NicknameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.e("adsf", "Asfssf");
                        }
                    });


                }
            }
        });
    }
}
