package com.example.weiduapp.presenter;

import com.example.weiduapp.bean.LoginBean;
import com.example.weiduapp.bean.RegBean;
import com.example.weiduapp.contract.ProductContract;
import com.example.weiduapp.model.ProductModel;
import com.google.gson.Gson;

import java.util.HashMap;

public class ProductPresenter extends ProductContract.ProductPresentervoid {


    @Override
    public void getProductList(HashMap<String, String> params) {

        model.getProductList(params, new ProductModel.IProductCallback() {
            @Override
            public void failure(String msg) {
                view.failure(msg);
            }

            @Override
            public void successlogin(String result) {
                LoginBean loginBean = new Gson().fromJson(result, LoginBean.class);
                view.successlogin(loginBean);
            }

            @Override
            public void successreg(String result) {

            }


        });

        model.getRegList(params, new ProductModel.IProductCallback() {
            @Override
            public void failure(String msg) {
                view.failure(msg);
            }

            @Override
            public void successlogin(String result) {

            }

            @Override
            public void successreg(String result) {
                RegBean regBean = new Gson().fromJson(result, RegBean.class);
                view.successreg(regBean);
            }
        });


    }



}
