package com.example.aa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.security.auth.callback.Callback;

public class MainActivity extends AppCompatActivity {
    private TextView tvView1;
    private TextView tvView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tvView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtil.getDefault(this).doAsync(HttpInfo.Builder()
                        .setUrl("http://218.94.143.226:7001/xc/topics/findById")
                        .setRequestType(RequestType.POST)
                        .addParam("id", "151b2030d8a34d2892c905f56ac23e9e")
                        .addParam("userId", "fdbd5d4d6a0d4547a777ebeb24e115fa").build(), new Callback() {
                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String request = info.getRetDetail();
                        try {
                            JSONObject jsonObject = new JSONObject(request);
                            int code = jsonObject.optInt("code");
                            if (0 == code){
                                String title = jsonObject.optJSONObject("data").optJSONObject("topic").optString("title");
                                String description = jsonObject.optJSONObject("data").optJSONObject("topic").optString("description");
                                tvView1.setText(title);
                                tvView2.setText(description);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpInfo info) throws IOException {

                    }

                });
            }
        });
    }

    private void initView() {
        tvView1 = findViewById(R.id.tv_1);
        tvView2 = findViewById(R.id.tv_2);
    }

}
