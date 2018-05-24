package com.example.asus.mynotebook.view.pages.notepager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.presenter.DataOfmain;
import com.example.asus.mynotebook.presenter.GlideImageLoader;
import com.example.asus.mynotebook.presenter.notepager.MynotesAdapter;
import com.example.asus.mynotebook.utils.GuidFloat;
import com.example.asus.mynotebook.view.activity.NoteDetails;
import com.example.asus.mynotebook.view.activity.WriteNoteActivity;
import com.example.asus.mynotebook.view.interfaces.BasePager;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.banner.Banner;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

/**
 * Created by asus on 2018/1/20.
 *
 */

public class NotePager extends BasePager {

    private RecyclerView notes;
    private LinearLayout takeNotes;
    private PullToRefreshView refresh;
    private View myNotes;
    private View tip;
    private final View view;

    public NotePager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);

        view = View.inflate(mactivity, R.layout.pager_note, null);
        frame_Content_Layout.addView(view);    //此处会导致重新向父布局添加view

    }

    @Override
    public void initData() {

        initViews();

        listener();
        if (Flags.CURRENT_STATUS!=1){
            initRecycler(notes, //从CollectionBean表单中搜索
                    DataSupport.where("userId = ?", String.valueOf(Flags.currentAccount)).find(CollectionBean.class)
            );
        }else {
            notes.setVisibility(View.INVISIBLE);
        }
    }

    private void initViews() {

        notes = view.findViewById(R.id.rv_mynotes);
        myNotes = view.findViewById(R.id.ll_mynotes);
        takeNotes = view.findViewById(R.id.ll_takenotes);
        refresh = view.findViewById(R.id.pull_refresh);
//        tip = view.findViewById(R.id.tip);
        Banner banner = view.findViewById(R.id.banner);
        //基于ViewPager实现的轮播图效果 关键点在于时刻得到手指位置和屏幕位置 以及下方点的坐标进行正确配对

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(DataOfmain.getImgaes());
        banner.start();
        Bundle extras = mactivity.getIntent().getExtras();
     /*   if (extras != null) {
            String icon = extras.getString("icon");
            NoteBean last = DataSupport.findLast(NoteBean.class);
            last.setContentMap(icon);
        }*/

    }

    private void listener() {
        System.gc();

        myNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.CURRENT_STATUS == 1){
                    new SVProgressHUD(mactivity).showErrorWithStatus("管理员没有收藏", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                }
                if (Flags.currentAccount!=-1){
                    mactivity.startActivity(new Intent(mactivity, NoteDetails.class));
                }else {
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登陆", SVProgressHUD.SVProgressHUDMaskType.Clear);
                }

            }
        });

        GuidFloat.addGuide(mactivity,"GuidTakeNote",takeNotes,R.layout.view_guid_note);

        takeNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.CURRENT_STATUS == 1){
                    new SVProgressHUD(mactivity ).showErrorWithStatus("管理员没有笔记", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                }
                if (Flags.currentAccount == -1){
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登陆", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                }
                Intent intent = new Intent(mactivity,WriteNoteActivity.class);
                mactivity.startActivity(intent);
            }
        });
        refresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<CollectionBean> collectionBeans = DataSupport.where("userId = ?", String.valueOf(Flags.currentAccount)).find(CollectionBean.class);
                        mactivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecycler(notes, collectionBeans);
                            }
                        });
                        refresh.setRefreshing(false);
                    }
                },1000);
            }
        });

    }



    private void initRecycler(RecyclerView notes,List note) {
        notes.setLayoutManager(new LinearLayoutManager(mactivity));
        if (Flags.currentAccount!=-1){
            MynotesAdapter mynotesAdapter = new MynotesAdapter(note, mactivity);
            notes.setAdapter(mynotesAdapter);
            mynotesAdapter.notifyDataSetChanged();
        }else {
            notes.setVisibility(View.INVISIBLE);

        }
    }


}
