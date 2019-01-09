package com.example.weiduapp.contract;

import com.example.lib_core.base.mvp.BaseView;
import com.example.lib_core.base.mvp.Basemodel;
import com.example.lib_core.base.mvp.Basepresenter;
import com.example.weiduapp.bean.LoginBean;
import com.example.weiduapp.bean.RegBean;
import com.example.weiduapp.model.ProductModel;

import java.util.HashMap;

public interface ProductContract {


    abstract class ProductPresentervoid extends Basepresenter<IProductModel,IProductView>  {
        @Override
        public IProductModel getModel() {
            return new ProductModel();
        }
        public abstract void getProductList(HashMap<String,String> params);
    }

    interface IProductModel extends Basemodel {
        void  getProductList(HashMap<String,String> params, ProductModel.IProductCallback callback);
        void  getRegList(HashMap<String,String> params, ProductModel.IProductCallback callback);
    }

    interface IProductView extends BaseView {

        void successreg(RegBean regBean);
        void successlogin(LoginBean loginBean);
        void keywordsEmpty(String error);

    }



}
