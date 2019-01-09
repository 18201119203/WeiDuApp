package com.example.weiduapp.model;

import com.example.lib_core.net.OkHttpCallback;
import com.example.lib_core.net.OkHttpUtils;
import com.example.weiduapp.api.Api;
import com.example.weiduapp.contract.ProductContract;

import java.util.HashMap;

public class ProductModel implements ProductContract.IProductModel {


    @Override
    public void getProductList(HashMap<String, String> params, final IProductCallback callback) {

        OkHttpUtils.getInstance().doPost(Api.LOGIN_URL, params, new OkHttpCallback() {
            @Override
            public void failure(String msg) {
                if (callback!=null){
                    callback.failure(msg);
                }
            }

            @Override
            public void success(String response) {
                if (callback!=null){
                    callback.successlogin(response);
                }
            }

        });
    }

    @Override
    public void getRegList(HashMap<String, String> params, final IProductCallback callback) {
        OkHttpUtils.getInstance().doPost(Api.REG_URL, params, new OkHttpCallback() {
            @Override
            public void failure(String msg) {
                if (callback!=null){
                    callback.failure(msg);
                }
            }

            @Override
            public void success(String response) {
                if (callback!=null){
                    callback.successreg(response);
                }
            }

        });
    }


    public interface IProductCallback {
        void failure(String msg);
        void successlogin(String result);
        void successreg(String result);
    }
}
