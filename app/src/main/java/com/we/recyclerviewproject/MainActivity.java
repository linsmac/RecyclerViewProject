package com.we.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<PostModel> mtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //TODO:
//
//        //這邊一定是你解完JSON的地方
//        // mtList.add(new PostModel("2019/10/01", "亂花", "10000"));
//
//        mtList = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            mtList.add(new PostModel("2019/10/01", "亂花", "10000"));
//            mtList.add(new PostModel("2019/10/02", "輕輕花", "10"));
//        }


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this, getData()));
    }

    private List<PostModel> getData() {
        List<PostModel> onceDataList = new ArrayList<>();
        JSONArray once = loadJsonArray(this);
        Log.v("Test", "once = " +once.length());
        try {
            for (int i = 0; i < once.length(); i++) {
                JSONObject item = once.getJSONObject(i);
                onceDataList.add(new PostModel(
                        item.getString("date"),
                        item.getString("type"),
                        item.getString("money"),
                        item.getString("color")));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return onceDataList;
    }

    private JSONArray loadJsonArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.once);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("once");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
