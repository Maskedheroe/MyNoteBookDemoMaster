package com.example.asus.mynotebook.presenter.minepager;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.UserBean;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

/**
 * 用户登录与注册逻辑
 * Created by asus on 2018/2/3.
 */

public class MyLogin {
    private CircleImageView circleImageView;
    private MaterialLoginView ml_login;
    private TextView accountName;
    private View view;
    private LinearLayout update_pwd;
    private LinearLayout login;
    private LinearLayout manager_login;
    private LinearLayout update_icon;
    private ImageButton dismissLogin;
    private LinearLayout loginExit;
    private View dealuser;

    public MyLogin(View view) {
        this.view = view;
    }

    public void login(final Activity mactivity){
        initView(view);
        this.view = view;
        SQLiteDatabase db = Connector.getDatabase();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlogin(mactivity);
            }
        });
    }


    public void mlogin(final Activity mactivity) {
        initView(view);
        disMissButton();
        ml_login.setClickable(true);
        ml_login.setVisibility(View.VISIBLE);
        dismissLogin.setVisibility(View.VISIBLE);
        ((DefaultLoginView)ml_login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                //Handle login
                List<UserBean> userBeans = DataSupport.where("userName = ?", loginUser.getEditText().getText().toString()).find(UserBean.class);
                if (userBeans.size()>0) {
                    UserBean userBean = userBeans.get(0);
                    if (userBean.getUserPwd().equals(loginPass.getEditText().getText().toString())) { //litePal的查找逻辑
                        if (userBean.getId()== Flags.currentAccount){
                            Toast.makeText(mactivity, "已经登录，不可重复登录", Toast.LENGTH_SHORT).show();  //判断重复登录的逻辑
                            ml_login.setVisibility(View.INVISIBLE);
                            return;
                        }
                        loginsuccess(userBean, mactivity, loginUser, loginPass);
                    }else {
                        Toast.makeText(mactivity, "登录失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
                        loginPass.getEditText().setText("");
                    }
                }else {
                    Toast.makeText(mactivity,"登录失败,用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((DefaultRegisterView)ml_login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
//                Toast.makeText(mactivity, "账户名和密码必须大于6位", Toast.LENGTH_SHORT).show();
                String accountName = Objects.requireNonNull(registerUser.getEditText()).getText().toString();
                String accountPwd = Objects.requireNonNull(registerPassRep.getEditText()).getText().toString();
                if (accountName.length() > 6 && accountPwd.length() > 6) {
                    //Handle register 处理注册的事务

                    UserBean userBean = new UserBean(accountName, accountPwd);
                    List<UserBean> userBeans = DataSupport.where("userName == ?", accountName)  //存储至数据库
                            .find(UserBean.class);
                    if (userBeans.isEmpty()) {  //判断用户名是否存在
                        if (userBean.save()) {  //判断注册成功
                            Toast.makeText(mactivity, "注册成功", Toast.LENGTH_SHORT).show();
                            Flags.USER = userBean;
                            if (Flags.currentAccount != -1) {
                                Toast.makeText(mactivity, "现在已登录！" + userBean.getUserName() + "账号", Toast.LENGTH_SHORT).show();
                            }
                            loginsuccess(userBean, mactivity, registerUser, registerPass);
                        } else {
                            Toast.makeText(mactivity, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mactivity, "注册失败，用户名已存在", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(mactivity, "注册失败，用户名或密码长度错误", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dismissing();
    }

    private void dismissing() {
        dismissLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ml_login!=null){
                    ml_login.setVisibility(View.INVISIBLE);
                    showMissButton();
                    dismissLogin.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void showMissButton() {
        update_pwd.setClickable(true);
        update_pwd.setAlpha(1);
        update_icon.setClickable(true);
        update_icon.setAlpha(1);
        login.setClickable(true);
        login.setAlpha(1);
        manager_login.setClickable(true);
        manager_login.setAlpha(1);
        loginExit.setClickable(true);
        loginExit.setAlpha(1);
        dealuser.setClickable(true);
        dealuser.setAlpha(1);
    }

    private void disMissButton() {
        update_pwd.setClickable(false);
        update_pwd.setAlpha((float) 0.2);
        update_icon.setClickable(false);
        update_icon.setAlpha((float) 0.2);
        login.setClickable(false);
        login.setAlpha((float) 0.2);
        manager_login.setClickable(false);
        manager_login.setAlpha((float) 0.2);
        loginExit.setClickable(false);
        loginExit.setAlpha((float) 0.2);
        dealuser.setClickable(false);
        dealuser.setAlpha((float) 0.2);
    }

    public void loginsuccess(UserBean userBean, Activity mactivity, TextInputLayout loginUser, TextInputLayout loginPass) {
        Toast.makeText(mactivity, "登录成功", Toast.LENGTH_SHORT).show();  //成功之后的逻辑
        Objects.requireNonNull(loginUser.getEditText()).setText("");
        Objects.requireNonNull(loginPass.getEditText()).setText("");
        if (ml_login!=null){
            ml_login.setVisibility(View.INVISIBLE); //成功后隐藏登录窗
        }
        Flags.USER = userBean;
        Flags.currentAccount = userBean.getId();
        accountName.setText("");
        accountName.setText(userBean.getUserName());
        dismissLogin.setVisibility(View.INVISIBLE);
        showMissButton();
    }


    public void managerlogin(final Activity mactivity) {
        initView(view);
        disMissButton();
        ml_login.setVisibility(View.VISIBLE);
        dismissLogin.setVisibility(View.VISIBLE);
        ((DefaultLoginView)ml_login.getLoginView())
                .setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                //Handle login
                List<UserBean> userBeans = DataSupport.where("userName = ?", loginUser.getEditText().getText().toString()).find(UserBean.class);
                if (userBeans.size()>0) {
                    UserBean userBean = userBeans.get(0);
                    if (userBean.getUserPwd().equals(loginPass.getEditText().getText().toString())&&userBean.getUserName().equals("admin")) { //litePal的查找逻辑
                        if (userBean.getId()==Flags.currentAccount){
                            Toast.makeText(mactivity, "已经登录，不可重复登录", Toast.LENGTH_SHORT).show();  //判断重复登录的逻辑
                            ml_login.setVisibility(View.INVISIBLE);
                            return;
                        }

                        loginsuccess(userBean,mactivity,loginUser,loginPass);
                        Toast.makeText(mactivity,"管理员登录",Toast.LENGTH_SHORT).show();
                        Flags.CURRENT_STATUS = 1;
                        Flags.currentAccount = 1;
                        Flags.USER = userBean;
                    }else {
                        Toast.makeText(mactivity, "登录失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
                        loginPass.getEditText().setText("");
                    }
                }else {
                    Toast.makeText(mactivity,"登录失败,用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((DefaultRegisterView)ml_login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                //Handle register 处理注册的事务
                String userName = registerUser.getEditText().getText().toString();
                String userPwd = registerPassRep.getEditText().getText().toString();
                UserBean userBean = new UserBean(userName,userPwd);

                    if (DataSupport.where("userName == ?", userBean.getUserName())
                            .find(UserBean.class).isEmpty()) {  //判断注册管理员是否合法
                        if (!userName.equals("admin")&&!userPwd.equals("123")){
                            Toast.makeText(mactivity,"注册失败，非合法注册的管理员",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (userBean.save()) {//判断注册成功
                            Toast.makeText(mactivity, "注册成功", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mactivity, "管理员登录", Toast.LENGTH_SHORT).show();
                            Flags.CURRENT_STATUS = 1;
                            Flags.currentAccount = 1;
                            Flags.USER = userBean;
                            accountName.append(userName);
                            registerPassRep.getEditText().setText("");
                            loginsuccess(userBean,mactivity, registerUser, registerPass);
                        }else {
                            Toast.makeText(mactivity,"注册失败",Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        Toast.makeText(mactivity,"注册失败，用户名已存在",Toast.LENGTH_SHORT).show();
                    }
            }
        });
        dismissing();
    }
    private void initView(View view) {
        circleImageView = view.findViewById(R.id.circleImageView);
        accountName = view.findViewById(R.id.tv_accountName);
        ml_login = view.findViewById(R.id.ml_login);
        update_pwd = view.findViewById(R.id.update_pwd);
        login = view.findViewById(R.id.login_pwd);
        manager_login = view.findViewById(R.id.login_manager);
        update_icon = view.findViewById(R.id.login_updateicon);
        dismissLogin = view.findViewById(R.id.ib_dismisslogin);
        loginExit = view.findViewById(R.id.login_exit);
        dealuser = view.findViewById(R.id.login_dealuser);
    }
}
