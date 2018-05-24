package com.example.asus.mynotebook.presenter.mainpager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.utils.DrawableToBytes;
import com.yalantis.phoenix.PullToRefreshView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/2/24.
 */

@SuppressLint("ValidFragment")
public class BlankFragment extends Fragment {


    private RecyclerView noteCollections;
    private String course;
    private Resources r;
    private String TAG = "BlankFragment";
    private View view;
    private SwipeRefreshLayout pulltoRefresh;
    private List<CollectionBean> collectionBeans;

    public  BlankFragment(String course) {
        this.course = course;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        r = this.getContext().getResources();
        if ( view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.main_pager_blankfragment, null);
            noteCollections = view.findViewById(R.id.notecollections);
            pulltoRefresh = view.findViewById(R.id.pull_refresh_blankfrg);
            initNoteCollections(noteCollections);// 控件初始化
       }
        return view;
    }

    public void initNoteCollections(final RecyclerView noteCollections) {

        noteCollections.setLayoutManager(new LinearLayoutManager(getContext()));
        if (collectionBeans!=null)
        collectionBeans.clear();
        collectionBeans = DataSupport.where("course = ?", course).find(CollectionBean.class);
        //连接数据库后开启线程，向表单搜索数据

        //集合存储数据
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), (ArrayList<CollectionBean>) collectionBeans);

        //发送给adapter 适配器 适配数据
        noteCollections.setAdapter(mainRecyclerAdapter);
        mainRecyclerAdapter.notifyDataSetChanged();
        if (pulltoRefresh!=null){
            pulltoRefresh.setColorSchemeResources(R.color.colorPrimary);
            pulltoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //开启线程 ，向数据库搜索数据 ，发送给适配器
                    final List<CollectionBean> collectionBeans = DataSupport.where("course = ?", course).find(CollectionBean.class);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            }catch (InterruptedException ex){
                                ex.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                             MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), (ArrayList<CollectionBean>) collectionBeans);

                                    noteCollections.setAdapter(mainRecyclerAdapter);
                                    mainRecyclerAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();
                }
            });
        }


    }




    public static ArrayList<CollectionBean> getData(String course){
        ArrayList<CollectionBean> noteList;
        switch (course){
            case "数学":
                noteList= new ArrayList();
                noteList.add(new CollectionBean("设X0是多项式P(X) = x^+ ax^3 + bx^2 + cx + d 的最小实根","数学",
                        "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/ff79ff95409ad6cc802c4bdc3edb8f22.png",-1,true));
                noteList.add(new CollectionBean("极值点问题","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/0854f30e401d701d80cffa1c0bc07b1d.png",-1   ,true));
                noteList.add(new CollectionBean("极值点问题1","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/2e9a9e9a40b6ce3a80979b7edfb07890.png",-1  ,true) );
                noteList.add(new CollectionBean("极值点问题2","数学",  "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/2de354ed40aa169f805d8379a9f46919.png",-1,true));
                saveOrUpdate(noteList);
                return noteList;
            case "英语":
                noteList= new ArrayList();
                noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/7248f87d40df38c3806dc9eb0e9667b0.png" ,-1,true));
                noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/df3d6f34407916a88033d76f94ddb36b.png",-1 ,true));
                noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/8e30f378403afe7b80c28f6601f43e42.png",-1 ,true));
                noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/7f6892c840f042a580b2e92adc3eb65b.png",-1 ,true));
                saveOrUpdate(noteList);
                return noteList;
            case "政治":
                noteList= new ArrayList();
                noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/01ecab35400d001780f552ea4c4b661c.png",-1,true));
                noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/0194382f406a58de8021c75d62344f44.png",-1,true));;
                noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/8898906540de4e6a8077e767584bd7bb.png",-1,true));
                noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/1e60d2d140d4075b80a9c23d51316def.png",-1,true));
                noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/e470635a40f779e580ea5180216efa0d.png",-1,true));

                saveOrUpdate(noteList);
                return noteList;
            case "化学":
                noteList= new ArrayList();
                noteList.add(new CollectionBean("化学题","化学", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/21bafc124060170780d5053cf4ca2ea6.png" ,-1,true));
                noteList.add(new CollectionBean("化学题","化学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/e839c7ac40ce16a4806ec83b084c1874.png",-1  ,true));;
                noteList.add(new CollectionBean("化学题","化学", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/125c1252404a4cbe8044443fcdc9a46f.png" ,-1,true));
                noteList.add(new CollectionBean("化学题","化学", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/a76fa82f40209528800f8cd9ff1d4bfb.png" ,-1,true));

                saveOrUpdate(noteList);
                return noteList;
            case "物理":
                noteList= new ArrayList();
                noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/8f85601e40feab0f80a9e7bbcb206a63.png" ,-1 ,true));
                noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/7e37962840d80cf480b4a73ae15dfbc7.png",-1  ,true));;
                noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/e587d96f4099d8f38043ad766f035202.png" ,-1 ,true));
                noteList.add(new CollectionBean("物理题","物理", " http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/ce7185574064a11c808e1e5d006f3e13.png" ,-1,true));

                saveOrUpdate(noteList);
                return noteList;
            case "语文":
                noteList= new ArrayList();
                noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/3833d84040fb5d8680fb12202c6c9fa3.png" ,-1,true));
                noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/96ba8b8d40f2676c80ac68b8130d030a.png",-1 ,true));;
                noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/e123285d40d325f480c07a5aab895635.png" ,-1,true));
                noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/1f14bb2c40349b7280a05373bb32a315.png" ,-1,true));
                noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/e0054094408a5e618035e7d6e53c5abb.png" ,-1,true));
                noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/6d73d59c40ef72ae80bae5680f66d948.png" ,-1,true));
                return noteList;
            default:
                noteList = new ArrayList<>();
                noteList.add(new CollectionBean("极值点问题","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/0854f30e401d701d80cffa1c0bc07b1d.png",-1   ,true));
                noteList.add(new CollectionBean("极值点问题1","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/2e9a9e9a40b6ce3a80979b7edfb07890.png",-1  ,true));
                noteList.add(new CollectionBean("极值点问题2","数学",  "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/2de354ed40aa169f805d8379a9f46919.png",-1,true));
                return noteList;
        }

    }

    private static void saveOrUpdate(ArrayList<CollectionBean> noteList) {
        for (CollectionBean collectionBean : noteList) {
            collectionBean.saveOrUpdate();
        }
    }


}
