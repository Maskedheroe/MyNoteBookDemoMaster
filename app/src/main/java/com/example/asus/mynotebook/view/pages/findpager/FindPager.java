package com.example.asus.mynotebook.view.pages.findpager;

import android.app.Activity;

import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.presenter.findpager.FindRecyclerAdapter;
import com.example.asus.mynotebook.presenter.mainpager.BlankFragment;
import com.example.asus.mynotebook.utils.GuidFloat;
import com.example.asus.mynotebook.utils.InitDataBase;
import com.example.asus.mynotebook.view.interfaces.BasePager;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;

/**
 * Created by asus on 2018/1/20.
 */

public class FindPager extends BasePager {
    //笔记页面

    private MaterialSpinner sp_search;
    private SearchView search;
    private ArrayList<String> searchSpinnerList;
    private ArrayAdapter<String> searchAdapter;
    private boolean isAutoSelect;
    private RecyclerView recycler;
    private final View view;

    public FindPager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);
        view = View.inflate(mactivity, R.layout.pager_find, null);
        frame_Content_Layout.addView(view);

    }

    @Override
    public void initData() {
        Log.d("FindPage","init Methord" );
        sp_search = view.findViewById(R.id.sp_search);
        search = view.findViewById(R.id.search);
        recycler = view.findViewById(R.id.rv_find);
        initSpinner();
    }

    private void initSpinner() {
        initRecycler();

//        InitDataBase.initDataBase(mactivity);
        Flags.SEARCH_STATE = Flags.SEARCH_ONNOTE;
        searchSpinnerList = new ArrayList<>();
        searchSpinnerList.add("题库");
        searchSpinnerList.add("错题");
        sp_search.setItems("题库","错题");
        GuidFloat.addGuide(mactivity,"serach",search,R.layout.search_guid);
        search.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                if (Flags.currentAccount!=-1){
                    if (Flags.SEARCH_STATE == Flags.SEARCH_ONNOTE) {
                        //从NoteBean表单中进行搜索
                        List<NoteBean> noteBeans = DataSupport.where("title = ?",string).find(NoteBean.class);
//                        setAdapter(string);
                        recycler.setAdapter(new FindRecyclerAdapter(noteBeans, mactivity, Flags.SEARCH_ONNOTE));

                        if(noteBeans.isEmpty()){
                                List<CollectionBean> collectionBeans = DataSupport.where("title = ?",string).find(CollectionBean.class);
//                        setAdapter(string);
                                recycler.setAdapter(new FindRecyclerAdapter(collectionBeans, mactivity, Flags.SEARCH_ONLINE));

                                if (collectionBeans.isEmpty()){
                                    recycler.setAdapter(new FindRecyclerAdapter(DataSupport.findAll(NoteBean.class), mactivity, Flags.SEARCH_ONNOTE));

                                }
                        }

                    }
                }else if(Flags.SEARCH_STATE == Flags.SEARCH_ONLINE){

                    List<CollectionBean> noteBeans = DataSupport.where("title = ?",string).find(CollectionBean.class);
//                        setAdapter(string);
                    recycler.setAdapter(new FindRecyclerAdapter(noteBeans, mactivity, Flags.SEARCH_ONLINE));
                }else{

                    List<CollectionBean> noteBeans = DataSupport.where("title = ?",string).find(CollectionBean.class);
//                        setAdapter(string);
                    recycler.setAdapter(new FindRecyclerAdapter(noteBeans, mactivity, Flags.SEARCH_ONLINE));
//                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登陆", SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            }
        });
        sp_search.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (searchSpinnerList.get(position).equals("错题")) {  //根据选择进行标记用户的状态
                    Flags.SEARCH_STATE = Flags.SEARCH_ONNOTE;
                }
                Flags.SEARCH_STATE = Flags.SEARCH_ONLINE;
            }
        });

    }

    private void setAdapter(String string) {
        backDoor(string);
    }


    private void initRecycler() {  //初始化Recycler
        recycler.setLayoutManager(new LinearLayoutManager(mactivity));
    }


    private void backDoor(String string) {  //测试
        List <CollectionBean> beans = new ArrayList<>();
        switch (string){
            case "极值点问题":
                beans.add(new CollectionBean("极值点问题","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/0854f30e401d701d80cffa1c0bc07b1d.png",-1));
                recycler.setAdapter(new FindRecyclerAdapter( beans, mactivity, Flags.SEARCH_ONLINE));
            case "语文题" :
                recycler.setAdapter(new FindRecyclerAdapter(BlankFragment.getData("语文"),mactivity, Flags.SEARCH_ONLINE));
        }

    }

}
