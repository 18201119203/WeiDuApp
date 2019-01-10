package com.example.weiduapp.fragment;

import android.view.View;

import com.example.lib_core.base.BaseFragment;
import com.example.lib_core.base.mvp.Basepresenter;
import com.example.weiduapp.R;
import com.example.weiduapp.bean.LoginBean;
import com.example.weiduapp.bean.RegBean;
import com.example.weiduapp.contract.ProductContract;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements ProductContract.IProductView {


    private Unbinder bind;
    @BindView(R.id.xlv)
    XRecyclerView xlv;

    @Override
    protected int getViewId() {
        return R.layout.fragmenthome;
    }

    @Override
    protected void initView(View view) {
        bind = ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }

    @Override
    public void successreg(RegBean regBean) {

    }

    @Override
    public void successlogin(LoginBean loginBean) {

    }

    @Override
    public void keywordsEmpty(String error) {

    }

    @Override
    public Basepresenter initPresenter() {
        return null;
    }

    @Override
    public void failure(String msg) {

    }
}
