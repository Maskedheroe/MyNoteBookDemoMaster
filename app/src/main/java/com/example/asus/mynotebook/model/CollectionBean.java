package com.example.asus.mynotebook.model;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.widget.ImageView;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

/**
 * Created by wan on 2018/2/27.
 *
 * 题库的题目bean类
 */

public class CollectionBean extends DataSupport {

    private Calendar mdate;
    private int id;
    private String title;
    private String date;
    private final String course;
    private final int userId;
    private String contentMap;
    private String content;
    private ImageView imageView;
    private boolean isImageView;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CollectionBean(String title, String course, String imageView, int i, boolean b) {
        this.title = title;
        this.course = course;
        if (b) {
            this.contentMap = imageView;
        }else {
            content = imageView;
        }
        this.userId = i;
        isImageView = b;
    }

    public Calendar getMdate() {
        return mdate;
    }

    public void setMdate(Calendar mdate) {
        this.mdate = mdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }





    public int getUserId() {
        return userId;
    }


    public String getContentMap() {
        return contentMap;
    }


    public CollectionBean(String title, String course, String contentMap, int id) {
            this.title = title;
            this.course = course;
            this.content = contentMap;
            this.userId = id;
    }


}
