package com.example.asus.mynotebook.presenter.mainpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by asus on 2018/2/10.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    //解决再次加载失效的方法 FragmentStatePagerAdapter  但是光标会与Fragment不同步.
    private final List<Fragment> fragmentList;
    private final List<String> str_title;



    public MainViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.str_title = titleList;
    }


    @Override
    public int getCount() {
        return fragmentList.size();
//        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return str_title.get(position);

        /*switch (position){
            case 1:
                return "数学";

            case 2:
                return "语文";

            case 3:
                return "英语";

            case 4:
                return "物理";

            case 5:
                return "化学";

            case 6:
                return "生物";

            case 7:
                return "历史";

            case 8:
                return "地理";

            case 9:
                return "政治";

            default:
                return "数学";
        }*/
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
        /*switch (position){
            case 1:
            return new BlankFragment("数学");

            case 2:
            return new BlankFragment("语文");

            case 3:
            return new BlankFragment("英语");

            case 4:
            return new BlankFragment("物理");

            case 5:
            return new BlankFragment("化学");

            case 6:
            return new BlankFragment("生物");

            case 7:
            return new BlankFragment("历史");

            case 8:
            return new BlankFragment("地理");

            case 9:
            return new BlankFragment("政治");

            default:
                return new BlankFragment("数学");
        }*/
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
       return FragmentStatePagerAdapter.POSITION_NONE;
    }
}
