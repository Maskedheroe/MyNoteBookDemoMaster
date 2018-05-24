package com.example.asus.mynotebook.view.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.model.UserBean;
import com.example.asus.mynotebook.presenter.mainpager.BlankFragment;
import com.example.asus.mynotebook.presenter.notepager.AddPhotos;
import com.example.asus.mynotebook.presenter.notepager.GridAdapter;
import com.example.asus.mynotebook.utils.GlideImageLoader;
import com.example.asus.mynotebook.utils.SpinnerUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WriteNoteActivity extends AppCompatActivity implements View.OnClickListener {

    //书写页面
    private EditText writetitle;
    private EditText writetext;
    private ImageButton deleteContent;
    private ImageButton addPhotos;
    private ImageButton commit;
    private MaterialSpinner spinner;
    private String currentCourse;
    private ImageView iv_note;
    private String key;
    private Uri imageUri;

    private String mImagePath;


    private static final int CHOOSE_PHOTO = 2;

    private void setPhoto(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setPhoto();
                }else {
                    Toast.makeText(this,"你拒绝了该权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case -1:
                handle(data);
            default:
                break;
        }
    }

    private void handle(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            assert uri != null;
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        dispalyImage(imagePath);
    }

    private void dispalyImage(String imagePath) {
        if (imagePath!=null){
            mImagePath = imagePath;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            GlideImageLoader.glideLoaderByURL(this,imagePath,iv_note);
            iv_note.setImageBitmap(bitmap);
            Toast.makeText(this,"记录成功",Toast.LENGTH_SHORT).show();
            new NoteBean(writetitle.getText().toString(),currentCourse,"",Flags.currentAccount,imagePath+"").save();
        }else {
            Toast.makeText(this,"获得图片失败！",Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        initView();
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            key = extras.getString("key");
        }else {
            key = "user";
        }
    }

    private void initView() {
        writetitle = findViewById(R.id.tv_write_title);
        writetext = findViewById(R.id.et_writetext);
        addPhotos = findViewById(R.id.ib_addphotos);
        deleteContent = findViewById(R.id.ib_deletetext);
        commit = findViewById(R.id.ib_commit);
        spinner = findViewById(R.id.write_note_spinner);
        iv_note = findViewById(R.id.iv_note);
        setListener();
    }

    private void setListener() {
        writetext.setOnClickListener(this);
        writetitle.setOnClickListener(this);
        addPhotos.setOnClickListener(this);
        deleteContent.setOnClickListener(this);
        commit.setOnClickListener(this);
        spinner.setItems("无", "数学", "语文", "英语", "物理", "化学", "生物", "历史", "地理", "政治");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                currentCourse = spinner.getItems().get(position).toString();

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_write_title:
                break;
            case R.id.et_writetext:
                break;
            case R.id.ib_addphotos:
                if (!writetitle.getText().toString().equals("")) {  //选择图片后也要检验是否有标题
                    checkPermissions();

                } else {
                    showNotitle();
                }
                break;
            case R.id.ib_deletetext:
                if (!writetext.getText().toString().isEmpty()) {
                    writetext.getText().clear();//清空文本
                    new SVProgressHUD(this).showInfoWithStatus("已清空", SVProgressHUD.SVProgressHUDMaskType.Clear);
                } else {
                    new SVProgressHUD(this).showInfoWithStatus("内容已为空", SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
                break;
            case R.id.ib_commit:

               if (key.equals("manager")){
//                    CollectionBean collectionBean = new CollectionBean(writetitle.getText().toString(),currentCourse,writetext.getText().toString(),Flags.CURRENT_STATUS);
                   /* BlankFragment.getData(writetitle.getText().toString()).add(new CollectionBean(writetitle.getText().toString(),
                            currentCourse,writetext.getText().toString(),Flags.CURRENT_STATUS));*/

                   if (mImagePath!=null){
                       CollectionBean collectionBean = new CollectionBean(writetitle.getText().toString(),currentCourse,mImagePath,1,true);
                       collectionSave(collectionBean);
                   }else {
                       CollectionBean collectionBean = new CollectionBean(writetitle.getText().toString(),currentCourse,writetext.getText().toString(),1,false);
                       collectionSave(collectionBean);
                   }
                }
                commit();

        }
    }


    private void commit() {

        if (writetitle.getText().toString().equals("")) {
            showNotitle();
        } else if (!DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).isEmpty()) { //此处是判断错题本重名的逻
            if (DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).get(0).getUser() != null) {
                if (!DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).isEmpty() &&
                        DataSupport.where("title = ?", writetitle.getText().toString()).find(NoteBean.class).get(0).getUser().getId() == Flags.currentAccount)
                    new SVProgressHUD(this).showInfoWithStatus("已存在该收藏", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        } else if (currentCourse == null) {
            new SVProgressHUD(this).showErrorWithStatus("没有选择课程", SVProgressHUD.SVProgressHUDMaskType.Clear);

        } else {
            if (mImagePath == null) {
                NoteBean noteBean = new NoteBean(writetitle.getText().toString(), currentCourse, writetext.getText().toString(), Flags.currentAccount); //存储方法
                datasave(noteBean);
            }else {
                NoteBean noteBean = new NoteBean(writetitle.getText().toString(), currentCourse, writetext.getText().toString(), Flags.currentAccount, mImagePath); //存储方法
                datasave(noteBean);
            }

        }
    }

    private void collectionSave(CollectionBean noteBean) {
        if (noteBean.save()) {
            new SVProgressHUD(this).showSuccessWithStatus("保存成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
            startActivity(new Intent(this,MainActivity.class));
            //执行数据存储逻辑
        } else {
            new SVProgressHUD(this).showErrorWithStatus("保存失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
        }
    }

    private void datasave(NoteBean noteBean) {
        if (noteBean.save()) {
            new SVProgressHUD(this).showSuccessWithStatus("保存成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
            //执行数据存储逻辑
        } else {
            new SVProgressHUD(this).showErrorWithStatus("保存失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);

        }else {
            setPhoto();
        }
    }

    public void showNotitle() {
        new SVProgressHUD(this).showInfoWithStatus("没有标题", SVProgressHUD.SVProgressHUDMaskType.Clear);
    }
}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == IMAGE_PICKER) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//
//                grid_note.setAdapter(adapter);
//            } else {
//                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
