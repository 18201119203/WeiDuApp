package com.example.weiduapp.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lib_core.base.mvp.BasemvpActivity;
import com.example.lib_core.base.mvp.Basepresenter;
import com.example.weiduapp.R;
import com.example.weiduapp.bean.LoginBean;
import com.example.weiduapp.bean.RegBean;
import com.example.weiduapp.contract.ProductContract;
import com.example.weiduapp.presenter.ProductPresenter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BasemvpActivity<ProductContract.IProductModel,ProductContract.ProductPresentervoid> implements ProductContract.IProductView {

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.show_pwd)
    ImageView show_pwd;

    @BindView(R.id.che_pwd)
    CheckBox che_pwd;

    @BindView(R.id.fly_login)
    TextView fly_login;

    @BindView(R.id.login)
    TextView login;


    private Unbinder bind;
    private SharedPreferences SP;

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        getSupportActionBar().hide();
        SP = getSharedPreferences("Login_pwd", MODE_PRIVATE);
        if (SP.getBoolean("ischeck",false)){
            che_pwd.setChecked(true);
            String phone = SP.getString("phone", "");
            String pwd = SP.getString("pwd", "");
            et_phone.setText(phone);
            et_pwd.setText(pwd);
        }

    }

    @Override
    public Basepresenter initPresenter() {
        return new ProductPresenter();
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }


    @OnClick(R.id.login)
    public void login(){

        String phone = et_phone.getText().toString();
        String pwd = et_pwd.getText().toString();

        if (che_pwd.isChecked()){
            SP.edit().putString("phone",phone).putString("pwd",pwd).putBoolean("ischeck",true).commit();
        }else{
            SP.edit().putBoolean("ischeck",false).commit();
        }

        HashMap<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("pwd",pwd);
        presenter.getProductList(params);


    }

    /**
     * 密码明密文
     */
    Boolean isYeye = true;
    @OnClick(R.id.show_pwd)
    public void show_pwd(){
        if (isYeye){
            //明文
            et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isYeye=false;
        }else {
            //密文
            et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isYeye=true;
        }
    }



    @OnClick(R.id.fly_login)
    public void fly_login(){
        startActivity(new Intent(LoginActivity.this,RegActivity.class));
    }

    @Override
    public void successreg(RegBean regBean) {

    }

    @Override
    public void successlogin(LoginBean result) {
        if ("登录成功".equals(result.message)){
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void failure(String msg) {
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void keywordsEmpty(String error) {
        Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();
    }
}
