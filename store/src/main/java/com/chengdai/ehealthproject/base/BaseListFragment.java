package com.chengdai.ehealthproject.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

/** item fragment
 * Created by 李先俊 on 2017/7/4.
 */

public abstract class BaseListFragment<T> extends BaseLazyFragment {

    protected int mPageIndex=1;//分页下标

    private List<T> mDataList;

    private CommonRecycleerBinding mBinding;

    private EmptyWrapper mEmptyWrapper;

    private boolean mIsFristCreate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.common_recycleer, null, false);
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        if(canDrawDivider()){
            mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(mActivity,MyDividerItemDecoration.VERTICAL_LIST));
        }

        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(mActivity));
        mBinding.springvew.setFooter(new DefaultFooter(mActivity));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex=1;
                onMRefresh(mPageIndex);
            }

            @Override
            public void onLoadmore() {
                if(mDataList.size() >0){
                    mPageIndex++;
                }
                onMLoadMore(mPageIndex);
            }
        });
        mDataList=new ArrayList<T>();

        CommonAdapter commonAdapter= new CommonAdapter<T>(mActivity,getItemLayoutId(),mDataList) {
            @Override
            protected void convert(ViewHolder holder, T item, int position) {
                if(item == null){
                    return;
                }

                onBind(holder,item,position);
            }

        };


        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(null !=mDataList.get(position)){
                    onItemCilck(mDataList.get(position),position);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mEmptyWrapper=new EmptyWrapper(commonAdapter);

        mEmptyWrapper.setEmptyView(getEmptyLayoutId());
        mBinding.cycler.setAdapter(mEmptyWrapper);
        mIsFristCreate=true;
        mPageIndex=1;
        onMInitRefresh(mPageIndex);
        return mBinding.getRoot();
    }

    //能否绘制分割线
    protected   boolean canDrawDivider(){
        return true;
    }

    //首次加载
    protected abstract void onMInitRefresh(int pageIndex);

    //刷新
    protected   abstract void onMLazyLoad(int pageIndex);
    //刷新
    protected   abstract void onMRefresh(int pageIndex);
    //加载
    protected   abstract  void onMLoadMore(int pageIndex);

    //获取item布局
    protected   abstract  int getItemLayoutId();

    //获取空数据布局
    protected     int getEmptyLayoutId(){
        return R.layout.empty_view;
    }

    protected   abstract  void onBind(ViewHolder holder, T o, int position);

    //点击
    protected void onItemCilck(T t,int position){

    }

    protected void setData(List<T> datas){
        mBinding.springvew.onFinishFreshAndLoad();
        if(mPageIndex == 1){
            if(datas != null){
                mDataList.clear();
                mDataList.addAll(datas);
                mEmptyWrapper.notifyDataSetChanged();
            }

        }else if(mPageIndex>1){
            if(datas == null || datas.size()<=0){
                mPageIndex--;
                return;
            }
            mDataList.addAll(datas);
            mEmptyWrapper.notifyDataSetChanged();
        }
    }


    @Override
    protected void lazyLoad() {
        if(mIsFristCreate){
            mPageIndex=1;
            onMLazyLoad(mPageIndex);
        }
    }

    @Override
    protected void onInvisible() {

    }

}
