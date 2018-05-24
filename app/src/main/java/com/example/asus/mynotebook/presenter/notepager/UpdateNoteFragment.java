package com.example.asus.mynotebook.presenter.notepager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.utils.GlideLoadUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UpdateNoteFragment extends Fragment {


    @BindView(R.id.tv_updatenote_title)
    EditText tvUpdatenoteTitle;
    @BindView(R.id.et_updateNote_content)
    EditText etUpdateNoteContent;
    @BindView(R.id.iv_updateNote_commit)
    ImageButton ivUpdateNoteCommit;
    @BindView(R.id.tv_updateNote_commit)
    TextView tvUpdateNoteCommit;
    Unbinder unbinder;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.materi_sp)
    MaterialSpinner materiSp;
    @BindView(R.id.iv_imagecontent)
    ImageView ivImagecontent;
    private View view;
    private String course;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_updatenote, container, false);
        Log.d("UpdateNote", "初始化？");
        unbinder = ButterKnife.bind(this, view);
        String title;
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = (String) bundle.get("Data");
        } else {
            title = "x";
        }
        List<NoteBean> noteBeans = DataSupport.where("title = ?", title).find(NoteBean.class);
        NoteBean noteBean = noteBeans.get(0);
        tvUpdatenoteTitle.setText(noteBean.getTitle());
        etUpdateNoteContent.setText(noteBean.getContent());

        materiSp.setItems("无", "数学", "语文", "英语", "物理", "化学", "生物", "历史", "地理", "政治");
        materiSp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                course = materiSp.getItems().get(position).toString();
            }
        });

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assemble();
            }
        });

        GlideLoadUtils.getInstance().glideLoad(this,noteBean.getContentMap(),ivImagecontent,0);

        /*ivImagecontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("操作");
                        final String[] items = {"删除"};
                        builder.setNegativeButton("取消", null);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (items[which]) {
                                    //业务逻辑
                                    case "删除":
                                        deleteAnim(ivImagecontent);
                                        DataSupport.de`(NoteBean.class,"title = ?" , viewHolder.title.getText().toString());
                                        break;
                                    default:
                                        //        Toast.makeText(mcontext,"功能未开放",Toast.LENGTH_SHORT).show();
                                        //如何刷新呢?
                                        break;
                                }
                            }
                        });
                        builder.show();

            }
        });*/
        return view;
    }

    private void assemble() {
        String title = tvUpdatenoteTitle.getText().toString().trim();
        String content = etUpdateNoteContent.getText().toString().trim();

        if (title.equals("") && content.equals("")) {
            Toast.makeText(getContext(), "题目不可为空", Toast.LENGTH_SHORT).show();
        } else {
            NoteBean noteBean = new NoteBean(title, course, content, Flags.currentAccount);
            noteBean.saveOrUpdate("title = ?", title);
            Toast.makeText(getContext(), "更新成功！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
