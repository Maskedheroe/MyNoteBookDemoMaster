package com.example.asus.mynotebook.presenter.minepager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.model.UserBean;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import tyrantgit.explosionfield.ExplosionField;

public class DetailsAdapterWithUser extends RecyclerView.Adapter<DetailsAdapterWithUser.ViewHolder> {

    private final Context mContext;
    private static List<UserBean> mUserList;

    private boolean isDeleteAble = true;
    private View inflate;
    private final FragmentManager mFragmentManager;

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ulUserName)
        TextView tvUlUserName;
        @BindView(R.id.tv_ulUserAccountID)
        TextView tvUlUserAccountID;
        @BindView(R.id.tv_ulUserAccountPWD)
        TextView tvUlUserAccountPWD;
        @BindView(R.id.tv_ulUserAccountCollects)
        TextView tvUlUserAccountCollects;
        @BindView(R.id.rl_users)
        RelativeLayout rlUsers;
        public ViewHolder(View itemView) {
            super(itemView);
            tvUlUserName = itemView.findViewById(R.id.tv_ulUserName);
            tvUlUserAccountID = itemView.findViewById(R.id.tv_ulUserAccountID);
            tvUlUserAccountPWD = itemView.findViewById(R.id.tv_ulUserAccountPWD);
            tvUlUserAccountCollects = itemView.findViewById(R.id.tv_ulUserAccountCollects);
            rlUsers= itemView.findViewById(R.id.rl_users);
        }
    }


    public DetailsAdapterWithUser(List userlist, Context context, FragmentManager fragmentManager) {
        mUserList = userlist;
        mContext = context;
        mFragmentManager = fragmentManager;
    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        inflate = View.inflate(parent.getContext(), R.layout.users_list, null);
        final ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.rlUsers.setOnLongClickListener(new View.OnLongClickListener() {

            private String str_id;

            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("操作");
                final String[] items = {"删除"};
                builder.setNegativeButton("取消", null);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (items[which]) {
                            //业务逻辑
                            case "删除":
                                deleteAnim(viewHolder);
                                str_id = viewHolder.tvUlUserAccountID.getText().toString();
                                DataSupport.deleteAll(UserBean.class, "id = ?", str_id);
                                DataSupport.deleteAll(NoteBean.class,"userId = ?", str_id);
                                DataSupport.deleteAll(CollectionBean.class,"userId = ?", str_id);
                                break;
                            default:
                                //        Toast.makeText(mcontext,"功能未开放",Toast.LENGTH_SHORT).show();
                                //                                如何刷新呢?
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserBean userBean = mUserList.get(position);
        holder.tvUlUserName.append(userBean.getUserName());
        holder.tvUlUserAccountID.append(userBean.getId()+"");
        holder.tvUlUserAccountPWD.append(userBean.getUserPwd());
        holder.tvUlUserAccountCollects.append(userBean.getCollectionBeans()+"");
    }


    private void deleteAnim(ViewHolder viewHolder) {
        change(viewHolder);
    }

    private void change(ViewHolder viewHolder) {
        if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
            ExplosionField.attach2Window((Activity) mContext).explode(inflate);  //要用attach2Window
            this.notifyItemRemoved(viewHolder.getAdapterPosition());
            isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
            //true,起到让数据源刷新完成的作用
            int position = viewHolder.getAdapterPosition() + this.mUserList.size();  //定位   ※※※完美解决！！※※※※※※为什么要加  ？？？？
            this.mUserList.remove(position);//删除数据源
            this.notifyItemRemoved(position);//刷新被删除的地方
            this.notifyItemRangeChanged(position, this.getItemCount()); //刷新被删除数据，以及其后面的数据

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);//休息
                        isDeleteAble = true;//可点击按钮
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
