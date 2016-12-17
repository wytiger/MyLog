package com.wytiger.logutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d("Test");
        LogUtil.i("TAG","Test2");

        JSONObject jsonObject = null;
        try{
             jsonObject = new JSONObject();
            jsonObject.put("name","xiaoming");
            jsonObject.put("age","16");
        }catch (Exception e){
            e.printStackTrace();
        }


        String json = jsonObject.toString();
        LogUtil.json("TAG",json);
    }
}
