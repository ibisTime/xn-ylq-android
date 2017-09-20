package com.chengdai.ehealthproject.model.healthcircle.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ListView;

import com.cdkj.baselibrary.dialog.CommonDialog;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.adapters.NineGridViewClickAdapter2;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/22.
 */

public class LuntanListAdapter extends CommonAdapter<ArticleModel.ListBean> {

    private boolean isShowDelete;

    public LuntanListAdapter(Context context, List<ArticleModel.ListBean> datas,boolean isShowDelete) {
        super(context, R.layout.item_luntan, datas);
        this.isShowDelete=isShowDelete;
    }

    public void setData(List<ArticleModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<ArticleModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }




    @Override
    protected void convert(ViewHolder holder, ArticleModel.ListBean listBean, int position) {

        if(listBean == null){
            return;
        }

        if(isShowDelete){
            holder.setVisible(R.id.tv_delete,true);
            holder.setOnClickListener(R.id.tv_delete,v -> {  //删除帖子

                CommonDialog commonDialog = new CommonDialog(mContext).builder()
                        .setTitle("提示").setContentMsg("确认删除这条帖子？")
                        .setPositiveBtn("确定", view -> {
                            deleteRequest(listBean,position);
                        })
                        .setNegativeBtn("取消", null, false);

                commonDialog.show();

            });
        }else{
            holder.setVisible(R.id.tv_delete,false);
            holder.setOnClickListener(R.id.tv_delete,null);
        }

        holder.setOnClickListener(R.id.lin_shear,v -> {
            ToastUtil.show(mContext,"分享");
        });


        //帖子详情
        holder.setOnClickListener(R.id.line_detail,v -> {
        });

        //点赞
        holder.setOnClickListener(R.id.lin_DZ,v -> {
            DZRequest(listBean);
        });

        //评论列表
        setpinlunlist(holder, listBean);

        //九宫格
        setNineGridView(holder, listBean);
        if(TextUtils.equals(listBean.getIsDZ(),"1")){//已点赞
            ImgUtils.loadImgId(mContext,R.mipmap.good_hand_,holder.getView(R.id.img_isDZ));
        }else{
            ImgUtils.loadImgId(mContext,R.mipmap.good_hand_un,holder.getView(R.id.img_isDZ));
        }

        /**
         * 设置数据显示状态
         */
        setShowState(holder, listBean);

    }



    private void setShowState(ViewHolder holder, ArticleModel.ListBean listBean) {
        ImgUtils.loadImgLogo(mContext, MyConfig.IMGURL+listBean.getPhoto(),holder.getView(R.id.img_user_logo));
        holder.setText(R.id.tv_name,listBean.getNickname());
        holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getPublishDatetime(),DateUtil.DEFAULT_DATE_FMT));

        holder.setText(R.id.tv_content,listBean.getContent());

        if(listBean.getSumLike()>999){
            holder.setText(R.id.tv_dz_sum,"999+");
        }else{
            holder.setText(R.id.tv_dz_sum,listBean.getSumLike()+"");
        }
        if(listBean.getSumComment()>999){
            holder.setText(R.id.tv_pinlun,"999+");
        }else{
            holder.setText(R.id.tv_pinlun,listBean.getSumComment()+"");
        }

        if(TextUtils.isEmpty(listBean.getAddress())){
            holder.setVisible(R.id.lin_location,false);
        }else{
            holder.setVisible(R.id.lin_location,true);
            holder.setText(R.id.tv_location,listBean.getAddress());
        }
    }

    private void setNineGridView(ViewHolder holder, ArticleModel.ListBean listBean) {
        NineGridView b=holder.getView(R.id.nineGrid);
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        List<String> pic=new ArrayList<String>();
        pic= StringUtils.splitAsList(listBean.getPic(),"\\|\\|");

        if (pic != null) {
            for (String imageDetail : pic) {
                if(TextUtils.isEmpty(imageDetail)){
                    continue;
                }
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(MyConfig.IMGURL+imageDetail);
                info.setBigImageUrl(MyConfig.IMGURL+imageDetail);
                imageInfo.add(info);
            }
        }
        b.setAdapter(new NineGridViewClickAdapter2(mContext, imageInfo));
    }

    private void setpinlunlist(ViewHolder holder, ArticleModel.ListBean listBean) {
        ListView lv_pinlun=holder.getView(R.id.lv_pinlun);
        List<ArticleModel.ListBean.CommentListBean> commentListBeanList=new ArrayList<ArticleModel.ListBean.CommentListBean>();
        if(listBean.getCommentList()!=null && listBean.getCommentList().size()>0){

            holder.setVisible(R.id.lin_pinlun,true);

            int i=0;
            for(ArticleModel.ListBean.CommentListBean bean:listBean.getCommentList()){  //最多显示3条评论
                if(i==3){
                    break;
                }
                commentListBeanList.add(bean);
                i++;
            }
            //设置品论数据
            lv_pinlun.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<ArticleModel.ListBean.CommentListBean>(mContext,R.layout.item_pinlun,commentListBeanList) {
                @Override
                protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, ArticleModel.ListBean.CommentListBean item, int position) {

                    if(item== null){
                        return;
                    }

                    viewHolder.setText(R.id.tv_name,item.getNickname());
                    viewHolder.setText(R.id.tv_content,": "+item.getContent());
                }

            });
            if(listBean.getCommentList().size()==1){      //只有一条评论时隐藏查看所有
                holder.setVisible(R.id.lin_pinlun,false);
            }else{
                holder.setVisible(R.id.lin_pinlun,true);
                holder.setOnClickListener(R.id.tv_look_all,v -> {
                });
            }

        }else{
            holder.setVisible(R.id.lin_pinlun,false);
        }
    }

    /**
     * 删除
     */
    public void deleteRequest(ArticleModel.ListBean item,int position){

        Map map=new HashMap();

        List<String> list=new ArrayList<>();

        list.add(item.getCode());
        map.put("codeList",list);
        map.put("userId",SPUtilHelpr.getUserId());
        map.put("type","1");

        RetrofitUtils.getLoaderServer().deleteTiezi("621013",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                .subscribe(isSuccessModes -> {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                },Throwable::printStackTrace);
    }

    /**
     * 点赞
     */
    public void DZRequest(ArticleModel.ListBean item){

        if(!SPUtilHelpr.isLogin(mContext)){
            return;
        }

        Map map=new HashMap();

        map.put("type","1");
/*
1 点赞 2 收藏
*/

        map.put("postCode",item.getCode());
        map.put("userId",SPUtilHelpr.getUserId());

   RetrofitUtils.getLoaderServer().DZRequest("621015",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(null))
                .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                .subscribe(isSuccessModes -> {
                    EventBusModel eventBusModel=new EventBusModel();
                    eventBusModel.setTag("LuntanDzRefeshEnent");//HealthCircleHotFragment PersonalLuntanActivity
                    EventBus.getDefault().post(eventBusModel);
                },Throwable::printStackTrace);

    }

}
