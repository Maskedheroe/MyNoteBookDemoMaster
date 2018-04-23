package com.example.asus.mynotebook.utils;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class SpinnerUtil {

    private static String course;

    public static String initSpinner(MaterialSpinner spinner) {
        final ArrayList<Object> searchSpinnerList = new ArrayList<>();
        searchSpinnerList.add("数学");
        searchSpinnerList.add("语文");
        searchSpinnerList.add("英语");
        searchSpinnerList.add("物理");
        searchSpinnerList.add("化学");
        searchSpinnerList.add("生物");
        searchSpinnerList.add("历史");
        searchSpinnerList.add("地理");
        searchSpinnerList.add("政治");
        spinner.setItems("无", "数学", "语文", "英语", "物理", "化学", "生物", "历史", "地理", "政治");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
              course = searchSpinnerList.get(position - 1).toString();
            }
        });
            return course;
    }
}
