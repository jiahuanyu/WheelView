package me.jiahuan.android.wheelview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.android.wheelview.WheelView;

public class MainActivity extends AppCompatActivity {

    private WheelView wheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        wheelView = findViewById(R.id.id_wheel_view);
    }


    public void onLoadButtonClicked(View v) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            data.add(i + "");
        }
        wheelView.setData(data);
    }
}
