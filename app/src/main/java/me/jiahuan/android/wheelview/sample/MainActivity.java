package me.jiahuan.android.wheelview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.jiahuan.android.wheelview.WheelView;

public class MainActivity extends AppCompatActivity {

    private WheelView wheelView;
    private WheelView wheelView2;

    private List<String> mWheelView2DataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        wheelView = findViewById(R.id.id_wheel_view);
        wheelView2 = findViewById(R.id.id_wheel_view_2);
        initialize();
    }

    private void initialize() {
//        onLoadButtonClicked(null);
    }


    public void onNotifyDataChangedButtonClicked(View v) {
        mWheelView2DataList.clear();
        for (int i = 2018; i <= 3000; i++) {
            mWheelView2DataList.add(i + "");
        }
        wheelView2.notifyDataChanged();
    }

    public void onLoadButtonClicked(View v) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            data.add(i + "");
        }
        wheelView.bindData(data);

        for (int i = 2000; i <= 2018; i++) {
            mWheelView2DataList.add(i + "");
        }
        wheelView2.bindData(mWheelView2DataList);
    }

    public void onSetIndexButtonClicked(View v) {
        wheelView.setSelectedIndex(2);
        wheelView2.setSelectedIndex(10);
    }

    public void onSmoothSetIndexButtonClicked(View v) {
        wheelView.smoothScrollToSelectedIndex(2);
        wheelView2.smoothScrollToSelectedIndex(10);
    }
}
