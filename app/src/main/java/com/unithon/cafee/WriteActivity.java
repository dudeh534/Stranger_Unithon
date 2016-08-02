package com.unithon.cafee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Youngdo on 2016-07-30.
 */
public class WriteActivity extends AppCompatActivity {
    EditText title, max, workgroup, text;
    TextView location;
    Button make;
    RestClient restClient = new RestClient(this);
    RequestParams params = new RequestParams();
    SharedPreferences setting;
    double latitude, longtitude;
    String workname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setting = getSharedPreferences("setting",0);
        location = (TextView) findViewById(R.id.location);
        title = (EditText) findViewById(R.id.title);
        max = (EditText) findViewById(R.id.max);
        workgroup = (EditText) findViewById(R.id.workgroup);
        text = (EditText) findViewById(R.id.text);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this, LocationAcivity.class);
                startActivityForResult(intent, 1);
            }
        });
        make = (Button) findViewById(R.id.make);
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.put("workplace_name",title);
                params.put("title",title.getText().toString());
                params.put("text",text.getText().toString());
                params.put("address",location.toString());
                params.put("latitude",latitude);
                params.put("longtitude",longtitude);
                params.put("workgroup_type",workgroup.getText().toString());
                params.put("max_limit",Integer.parseInt(max.getText().toString()));
                params.put("workgroup_manager_id",setting.getString("user_id",""));
                restClient.post("workgroup", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(WriteActivity.this,"완료",Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("ff","dsafdsf");
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
                location.setText(data.getStringExtra("Location"));
                latitude = Double.parseDouble(data.getStringExtra("Latitude"));
                longtitude = Double.parseDouble(data.getStringExtra("Longtitude"));
                workname = data.getStringExtra("title");
            }
        }
    }
}
