package com.example.asus.mynotebook.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.view.activity.MainActivity;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;

public class InitDataBase {
    public static void initDataBase(Activity activity) {
        SharedPreferences setting = activity.getSharedPreferences("data", 0);
        Boolean user_first = setting.getBoolean("FIRST",true);
        if(user_first){//第一次
            setting.edit().putBoolean("FIRST", false).apply();

            int a = -2;
            SQLiteDatabase db = Connector.getDatabase();
            //拿到引用 连接至数据库（litepal）

            ArrayList<CollectionBean> noteList = new ArrayList();
            noteList.add(new CollectionBean("设X0是多项式P(X) = x^+ ax^3 + bx^2 + cx + d 的最小实根","数学",
                    "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/ff79ff95409ad6cc802c4bdc3edb8f22.png",-1,true));
            noteList.add(new CollectionBean("极值点问题","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/0854f30e401d701d80cffa1c0bc07b1d.png",   a,true));
            noteList.add(new CollectionBean("极值点问题1","数学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/2e9a9e9a40b6ce3a80979b7edfb07890.png",  a,true));
            noteList.add(new CollectionBean("极值点问题2","数学",  "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/05/2de354ed40aa169f805d8379a9f46919.png",a,true));


            noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/7248f87d40df38c3806dc9eb0e9667b0.png",a ,true));
            noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/df3d6f34407916a88033d76f94ddb36b.png",a ,true));
            noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/8e30f378403afe7b80c28f6601f43e42.png",a ,true));
            noteList.add(new CollectionBean("英语作文","英语","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/7f6892c840f042a580b2e92adc3eb65b.png",a ,true));

            noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/01ecab35400d001780f552ea4c4b661c.png",a,true));
            noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/0194382f406a58de8021c75d62344f44.png",a,true));
            noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/8898906540de4e6a8077e767584bd7bb.png",a,true));
            noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/1e60d2d140d4075b80a9c23d51316def.png",a,true));
            noteList.add(new CollectionBean("政治题","政治", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/e470635a40f779e580ea5180216efa0d.png",a,true));

            noteList.add(new CollectionBean("化学题","化学", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/21bafc124060170780d5053cf4ca2ea6.png" ,a,true));
            noteList.add(new CollectionBean("化学题","化学","http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/e839c7ac40ce16a4806ec83b084c1874.png",  a,true));
            noteList.add(new CollectionBean("化学题","化学", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/125c1252404a4cbe8044443fcdc9a46f.png" ,a,true));
            noteList.add(new CollectionBean("化学题","化学", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/a76fa82f40209528800f8cd9ff1d4bfb.png" ,a,true));

            noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/8f85601e40feab0f80a9e7bbcb206a63.png" ,a ,true));
            noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/7e37962840d80cf480b4a73ae15dfbc7.png", a ,true));
            noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/e587d96f4099d8f38043ad766f035202.png" ,a ,true));
            noteList.add(new CollectionBean("物理题","物理", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/06/ce7185574064a11c808e1e5d006f3e13.png",a,true));


            noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/3833d84040fb5d8680fb12202c6c9fa3.png" ,a,true));
            noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/96ba8b8d40f2676c80ac68b8130d030a.png", a ,true));
            noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/e123285d40d325f480c07a5aab895635.png" ,a,true));
            noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/1f14bb2c40349b7280a05373bb32a315.png" ,a,true));
            noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/e0054094408a5e618035e7d6e53c5abb.png" ,a,true));
            noteList.add(new CollectionBean("语文题","语文", "http://bmob-cdn-15510.b0.upaiyun.com/2018/03/22/6d73d59c40ef72ae80bae5680f66d948.png" ,a,true));

            noteList.add(new CollectionBean("历史题","历史","http://a3.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/VTEUajfk2jT5qh4k4hv.T68UghXfTU6qgJm9fKKPIgs!/m/dEIBAAAAAAAAnull&bo=UwQ5AQAAAAADB00!&rf=photolist&t=5",    a ,true));
            noteList.add(new CollectionBean("历史题","历史","http://a1.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/123ejPgOHtAdkL3XxujJszp0pSZE59EWhgzdcUEQRpk!/m/dEQBAAAAAAAAnull&bo=fgT7AAAAAAADB6M!&rf=photolist&t=5",    a ,true));
            noteList.add(new CollectionBean("历史题","历史",  "http://a4.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/zFxaxSFYv*nMW4pr.2y*8Pmxeq.qPdL8y9bE5gxBK0I!/m/dEMBAAAAAAAAnull&bo=8wPBAAAAAAADBxM!&rf=photolist&t=5",a ,true));

            noteList.add(new CollectionBean("生物题","生物",  "http://a3.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/doVkZOJ190d*Ygy5v1dw2F2td2XNRjOFR*giI*IZ0AE!/m/dDIBAAAAAAAAnull&bo=xAP5AAAAAAADBxw!&rf=photolist&t=5",a,true));
            noteList.add(new CollectionBean("生物题","生物",  "http://a1.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/7ctW840RycoCXSs4Itxx5DooDMsoT*d4am7gH87n8E4!/m/dEABAAAAAAAAnull&bo=zAJgAQAAAAADB40!&rf=photolist&t=5",a,true));
            noteList.add(new CollectionBean("生物题","生物",  "http://a3.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/04Q3cX0wRk6ITUl5glLseWjdj2VsHRLOLaKwXByBp*M!/m/dDIBAAAAAAAAnull&bo=5gNyAQAAAAADB7Q!&rf=photolist&t=5",a,true));

            noteList.add(new CollectionBean("地理题", "地理",  "http://a1.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/MRVS*jViY1EnMCkoVXRG19zDxq1eVsvSiFi2lim3EnY!/m/dAgBAAAAAAAAnull&bo=PwMpAQAAAAADBzY!&rf=photolist&t=5",a,true));
            noteList.add(new CollectionBean("地理题1","地理",  "http://a2.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/0sRPzHqg8MwEaVH8uIpfkd40hkQ6niNJp.fTlOcouQ0!/m/dDEBAAAAAAAAnull&bo=9wIiAQAAAAADB*Q!&rf=photolist&t=5",a,true));
            noteList.add(new CollectionBean("地理题2","地理",  "http://a3.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/5t9Zx5nNsrKqDUYW6AdaH*Tc21OFyamc0aq00J07saQ!/m/dDIBAAAAAAAAnull&bo=QQQ3AQAAAAADB1E!&rf=photolist&t=5",a,true));
            noteList.add(new CollectionBean("地理题2","地理",  "http://a3.qpic.cn/psb?/f34d684a-9513-4e73-a9da-20e73945ae90/j8HkPHZHyuctJ.NtbUZbNOss.hRlDYBXf9*yi.xQgPM!/m/dDIBAAAAAAAAnull&bo=*wJDAQAAAAADB50!&rf=photolist&t=5",a,true));
            for (CollectionBean collectionBean : noteList) {
                collectionBean.save();
            }
        }




    }
}
