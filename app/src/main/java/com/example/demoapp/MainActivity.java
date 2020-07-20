package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.demoapp.util.Util;
import com.example.demoapp.view.GetSegmentView;
import com.example.demoapp.view.LivingStreamerView;
import com.example.demoapp.view.NormalGestureView;
import com.example.demoapp.view.TextFontMetricsView;
import com.example.demoapp.view.ZhiFuBaoAnimView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rlContent = findViewById(R.id.rl_content);

//        LivingStreamerView  livingStreamerView = new LivingStreamerView(this);
//        RelativeLayout.LayoutParams lpSteamerView = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lpSteamerView.addRule(RelativeLayout.CENTER_IN_PARENT);
//        livingStreamerView.setLayoutParams(lpSteamerView);
//        rlContent.addView(livingStreamerView);
//        livingStreamerView.startAnim();

//        GetSegmentView view = new GetSegmentView(this);
//        RelativeLayout.LayoutParams lpSteamerView = new RelativeLayout.LayoutParams(Util.dipToPixel(getResources(), 100), Util.dipToPixel(getResources(), 100));
//        lpSteamerView.addRule(RelativeLayout.CENTER_IN_PARENT);
//        view.setLayoutParams(lpSteamerView);
//        rlContent.addView(view);
//        view.startPlay();


//        ZhiFuBaoAnimView view = new ZhiFuBaoAnimView(this);
//        RelativeLayout.LayoutParams lpSteamerView = new RelativeLayout.LayoutParams(Util.dipToPixel(getResources(), 100), Util.dipToPixel(getResources(), 100));
//        lpSteamerView.addRule(RelativeLayout.CENTER_IN_PARENT);
//        view.setLayoutParams(lpSteamerView);
//        rlContent.addView(view);
//        view.start();

//        TextFontMetricsView view = new TextFontMetricsView(this);
//        RelativeLayout.LayoutParams lpSteamerView = new RelativeLayout.LayoutParams(Util.dipToPixel(getResources(), 500), Util.dipToPixel(getResources(), 500));
//        lpSteamerView.addRule(RelativeLayout.CENTER_IN_PARENT);
//        view.setLayoutParams(lpSteamerView);
//        rlContent.addView(view);

        NormalGestureView view = new NormalGestureView(this);
        RelativeLayout.LayoutParams lpSteamerView = new RelativeLayout.LayoutParams(Util.dipToPixel(getResources(), 500), Util.dipToPixel(getResources(), 500));
        lpSteamerView.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lpSteamerView);
        rlContent.addView(view);
    }
}