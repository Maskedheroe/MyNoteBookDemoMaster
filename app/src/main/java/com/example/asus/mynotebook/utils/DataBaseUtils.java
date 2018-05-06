package com.example.asus.mynotebook.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseUtils {

    private static SQLiteDatabase db;

    public static void getUser(Context context, String string) {
        db = new MyDBHelper(context, string, null, 2).getWritableDatabase();
    }

    public static void add() {
        db.execSQL("insert into Book(name, author, pagesm, price) values(?,?,?,?)",
                new String[]{ "f234567","User", "1234567", null});
        db.execSQL("insert into Book(name, author, pagesm, price) values(?,?,?,?)",
                new String[]{ "1","User", "1234567", null});  //账号不合格
    }

    public static void upgreade(){
        db.execSQL("update Book set price = ? where name = ?", new String[]{ "10.99",
        "The Da Vinci Code"});
    }

    public static void delete(){
        db.execSQL("delete from Book where pages > ?" , new String[]{"500"});
    }

    public static void query(){
        db.rawQuery(" select * from Book", null);
    }

}
