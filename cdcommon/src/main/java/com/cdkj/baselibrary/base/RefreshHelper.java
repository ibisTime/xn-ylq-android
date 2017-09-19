package com.cdkj.baselibrary.base;

import com.cdkj.baselibrary.databinding.EmptyViewBinding;
import com.cdkj.baselibrary.databinding.LayoutCommonRecyclerRefreshBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by 李先俊 on 2017/9/15.
 */

public class RefreshHelper<T> {


    protected LayoutCommonRecyclerRefreshBinding mBinding;

    protected int mPageIndex;//分页下标

    private int mLimit;//分页数量

    private List<T> mDataList;

    protected BaseQuickAdapter mAdapter;

    protected EmptyViewBinding mEmptyBinding;


    interface RefreshListener<T>{
       void listDataRequest(int pageIndex, int limit, boolean canShowDialog);
       void onMRefresh(int pageIndex, int limit, boolean canShowDialog);
       void onMLoadMore(int pageIndex, int limit, boolean canShowDialog);
        //获取数据适配器
       BaseQuickAdapter onCreateAdapter(List<T> mDataList);
    }

}
