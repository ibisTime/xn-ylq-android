package com.cdkj.baselibrary.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.databinding.EmptyViewBinding;
import com.cdkj.baselibrary.databinding.LayoutCommonRecyclerRefreshBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**实现下拉刷新 上拉加载 分页逻辑
 * Created by 李先俊 on 2017/7/19.
 */

public abstract class BaseRefreshActivity<T> extends AbsBaseActivity{

    protected LayoutCommonRecyclerRefreshBinding mBinding;

    private int mPageIndex;//分页下标

    private int mLimit;//分页数量

    private List<T> mDataList;

    private BaseQuickAdapter mAdapter;

    protected EmptyViewBinding mEmptyBinding;

    @Override
    public View addMainView() {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_common_recycler_refresh, null, false);
        return mBinding.getRoot();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

         mPageIndex=1;//分页下标

         mLimit=10;//分页数量

        mDataList=new ArrayList<T>();

        mAdapter=onCreateAdapter(mDataList);

        if(canLoadEmptyView()){
            mEmptyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.empty_view, null, false);
        }

        mBinding.rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        if(mAdapter!=null){
            mBinding.rv.setAdapter(mAdapter);
        }

        initRefreshLayout();

        onInit(savedInstanceState,mPageIndex,mLimit);
    }

    protected  boolean canLoadEmptyView(){
        return true;
    }

    /**
     * 初始化刷新加载
     */
    private void initRefreshLayout() {
        mBinding.refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageIndex=1;
                onMRefresh(mPageIndex,mLimit);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mDataList.size() >0){
                    mPageIndex++;
                }
                onMLoadMore(mPageIndex,mLimit);
            }
        });
    }


    //第一次加载
    protected   abstract void onInit(Bundle savedInstanceState, int pageIndex, int limit);

    //第一次加载
    protected   abstract void getListData(int pageIndex, int limit,boolean canShowDialog);

    //刷新
    protected  void onMRefresh(int pageIndex,int limit){
        getListData(pageIndex,limit,false);
    }
    //加载
    protected  void onMLoadMore(int pageIndex,int limit){
        getListData(pageIndex,limit,false);
    }

    protected  abstract BaseQuickAdapter onCreateAdapter(List<T> mDataList);

    public abstract String getEmptyInfo();



    public void loadError(String str){

        if(mPageIndex == 1 && mBinding.refreshLayout.isRefreshing()){
            mBinding.refreshLayout.finishRefresh();
        }else if(mPageIndex >1 && mBinding.refreshLayout.isLoading()){
            mBinding.refreshLayout.finishLoadmore();
        }


        if (mDataList.size() == 0 && canLoadEmptyView() && mEmptyBinding!=null) {
            if (TextUtils.isEmpty(str)) {
                mEmptyBinding.tv.setText("加载错误");
            } else {
                mEmptyBinding.tv.setText(str);
            }
            mEmptyBinding.img.setVisibility(View.GONE);
            if (mAdapter != null) mAdapter.setEmptyView(mEmptyBinding.getRoot());
            if (mBinding.refreshLayout.isLoading()) mBinding.refreshLayout.finishLoadmore();

        }else if(mDataList.size() == 0 ){
            if(getEmptyView()!=null){
                if (mAdapter != null) mAdapter.setEmptyView(getEmptyView());
            }
            if (mBinding.refreshLayout.isLoading()) mBinding.refreshLayout.finishLoadmore();
        }
    }

    /**
     * 设置加载数据
     * @param datas
     */
    protected void setData(List<T> datas){

        if(mPageIndex == 1){
            if(mBinding.refreshLayout.isRefreshing()) mBinding.refreshLayout.finishRefresh();
            if(datas != null){
                mDataList.clear();
                mDataList.addAll(datas);
                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }
            }

        }else if(mPageIndex>1){
            if(mBinding.refreshLayout.isLoading()) mBinding.refreshLayout.finishLoadmore();
            if(datas == null || datas.size()<=0){
                mPageIndex--;
            }else{
                mDataList.addAll(datas);

                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        if (canLoadEmptyView() && mEmptyBinding!=null) {
            mEmptyBinding.tv.setText(getEmptyInfo());
            mEmptyBinding.img.setImageResource(getEmptyImg());
            mEmptyBinding.img.setVisibility(View.VISIBLE);
            if (mAdapter != null) mAdapter.setEmptyView(mEmptyBinding.getRoot());
            if (mBinding.refreshLayout.isLoading()) mBinding.refreshLayout.finishLoadmore();

        }else{
            if(getEmptyView()!=null){
                if (mAdapter != null) mAdapter.setEmptyView(getEmptyView());
            }
            if (mBinding.refreshLayout.isLoading()) mBinding.refreshLayout.finishLoadmore();
        }
    }

    public View getEmptyView() {

        return null;
    }

    public abstract int getEmptyImg() ;
}
