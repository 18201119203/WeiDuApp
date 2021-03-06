package com.example.weiduapp.fragment;

import android.view.View;

import com.example.lib_core.base.BaseFragment;
import com.example.weiduapp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment {
    private Unbinder bind;

    @Override
    protected int getViewId() {
        return R.layout.fragmentmy;
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
}
