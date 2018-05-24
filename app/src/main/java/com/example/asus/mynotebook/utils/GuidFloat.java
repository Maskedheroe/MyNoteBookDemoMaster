package com.example.asus.mynotebook.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.example.asus.mynotebook.R;

import java.util.ArrayList;
import java.util.List;

import static com.yalantis.contextmenu.lib.ContextMenuDialogFragment.TAG;

public class GuidFloat {

    private static Context context;
    private static ArrayList i_arrs;
    private static ArrayList<View> view_arrs;
    private static ArrayList<String> str_arrs;

    /* NewbieGuide.with(mactivity)
                        .setLabel("LoginLabel")
                        .addGuidePage(GuidePage.newInstance()
                                       .addHighLight(login)
                                       .setLayoutRes(R)   )*/
    public static void addGuide(Context context, String label, View view, int layoutId) {
        NewbieGuide.with((Activity) context)
                .setLabel("LoginLabel")
                .setShowCounts(4)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(view,HighLight.Shape.RECTANGLE)
                        .setLayoutRes(layoutId)
                        .setEverywhereCancelable(true)
                        .setBackgroundColor(context.getResources().getColor(R.color.testColor))
                )
                .show();

        /*GuidFloat.context = context;
        str_arrs = new ArrayList<>();
        view_arrs = new ArrayList<>();
        i_arrs = new ArrayList();
        str_arrs.add(label);
        view_arrs.add(view);
        i_arrs.add(layoutId);
        if(i_arrs.size()>=4) {
            test();
        }*/
    }
/*
    private static void test() {
        NewbieGuide.with((Activity) context)
                .setLabel("page")//设置引导层标示区分不同引导层，必传！否则报错
                .alwaysShow(true)//是否每次都显示引导层，默认false，只显示一次
                .addGuidePage(//添加一页引导页
                        GuidePage.newInstance()//创建一个实例
                                //添加高亮的view
                                .addHighLight(view_arrs.get(0), HighLight.Shape.RECTANGLE)
                                .setLayoutRes((Integer) i_arrs.get(0),R.id.c1)//设置引导页布局
                               *//* .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                                    @Override
                                    public void onLayoutInflated(View view) {
                                        //引导页布局填充后回调，用于初始化
                                        TextView tv = view.findViewById(R.id.textView2);
                                        tv.setText("我是动态设置的文本");
                                    }
                                })*//*
                                .setEverywhereCancelable(true)//是否点击任意地方跳转下一页或者消失引导层，默认true
                                .setBackgroundColor(context.getResources().getColor(R.color.testColor))//设置背景色，建议使用有透明度的颜色
                )
                .addGuidePage(
                        GuidePage.newInstance()
                                .addHighLight(view_arrs.get(1), HighLight.Shape.RECTANGLE, 20)
                                .setLayoutRes((Integer) i_arrs.get(1), R.id.c2)//引导页布局，点击跳转下一页或者消失引导层的控件id
                                .setEverywhereCancelable(true)//是否点击任意地方跳转下一页或者消失引导层，默认true
                                .setBackgroundColor(context.getResources().getColor(R.color.testColor))//设置背景色，建议使用有透明度的颜色

                )
                .addGuidePage(//添加一页引导页
                        GuidePage.newInstance()//创建一个实例
                                //添加高亮的view
                                .addHighLight(view_arrs.get(2), HighLight.Shape.RECTANGLE)
                                .setLayoutRes((Integer) i_arrs.get(2),R.id.c3)//设置引导页布局
                                *//* .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                                     @Override
                                     public void onLayoutInflated(View view) {
                                         //引导页布局填充后回调，用于初始化
                                         TextView tv = view.findViewById(R.id.textView2);
                                         tv.setText("我是动态设置的文本");
                                     }
                                 })*//*
                                .setEverywhereCancelable(true)//是否点击任意地方跳转下一页或者消失引导层，默认true
                                .setBackgroundColor(context.getResources().getColor(R.color.testColor))//设置背景色，建议使用有透明度的颜色
                )
                .addGuidePage(//添加一页引导页
                        GuidePage.newInstance()//创建一个实例
                                //添加高亮的view
                                .addHighLight(view_arrs.get(3), HighLight.Shape.RECTANGLE)
                                .setLayoutRes((Integer) i_arrs.get(3),R.id.c4)//设置引导页布局
                                *//* .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                                     @Override
                                     public void onLayoutInflated(View view) {
                                         //引导页布局填充后回调，用于初始化
                                         TextView tv = view.findViewById(R.id.textView2);
                                         tv.setText("我是动态设置的文本");
                                     }
                                 })*//*
                                .setEverywhereCancelable(true)//是否点击任意地方跳转下一页或者消失引导层，默认true
                                .setBackgroundColor(context.getResources().getColor(R.color.testColor))//设置背景色，建议使用有透明度的颜色
                )
                .show();//显示引导层(至少需要一页引导页才能显示)
    }*/
}