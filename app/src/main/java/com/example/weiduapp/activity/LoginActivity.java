package com.example.weiduapp.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
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
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

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
    private String TAG = this.getClass().getSimpleName();

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


    /**
     * 注册
     */
    @OnClick(R.id.fly_login)
    public void fly_login(){
        startActivity(new Intent(LoginActivity.this,RegActivity.class));
    }


    //友盟授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,BottomActivity.class));

                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }



    /**
     * 快速登陆
     */
    @OnClick(R.id.fast_login)
    public void fast_login(){


        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);

        authorization(SHARE_MEDIA.QQ);

    }

    /**
     * 注册成公
     * @param regBean
     */
    @Override
    public void successreg(RegBean regBean) {

    }


    @Override
    public void successlogin(LoginBean result) {
        if ("登录成功".equals(result.message)){
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this,BottomActivity.class));
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
