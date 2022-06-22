package com.etuo.kucun.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.BomDetailsModel;
import com.etuo.kucun.model.StockUpDetailsModel;
import com.etuo.kucun.ui.activity.WebServiceViewActivity;
import com.etuo.kucun.ui.fragment.BigPicPopwindow;
import com.etuo.kucun.utils.UrlTools;
import com.etuo.kucun.view.CustomListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 *
 * @author :haining.yang
 * @version :V 1.0.0
 * @date :2020/02/25/14:05
 * @ProjectNameDescribe :折叠listAdapter
 * 修订历史：出库详情
 * ================================================
 */

public class NewTpBomDetailsAdapter extends BaseExpandableListAdapter {


    private List<BomDetailsModel.GatherListBean> mData;
    private String mType;
    private OnClickBtItem onClickBtItem;


    /**
     * 图片处理
     **/
    private GlideImageLoader mImageLoader;
    private Context mContext;
    private View  rootView ;

    public NewTpBomDetailsAdapter(Context mcontext, String type,View rootView) {
        this.mContext = mcontext;
        mImageLoader = new GlideImageLoader();
        mType = type;
        this.rootView = rootView;
    }


    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position, BomDetailsModel.GatherListBean.DetailListBean singleData);
        void myOrderCancelClick(int position, BomDetailsModel.GatherListBean.DetailListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }

    /**
     * 添加数据
     **/
    public void updata(List<BomDetailsModel.GatherListBean> groups) {
        this.mData = groups;

    }

    /**
     * 移除所有数据
     **/
    public void removeAll() {
        if (mData != null && mData.size() > 0) {
            for (int i = mData.size() - 1; i >= 0; i--) {
                mData.remove(i);
            }
        }

    }


    @Override
    public int getGroupCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return mData.get(groupPosition) == null ? 0 : mData.get(groupPosition).getDetailList().size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mData != null && mData.size() > 0) {
            List<BomDetailsModel.GatherListBean.DetailListBean> childs = mData.get(groupPosition).getDetailList();
            if (childs != null && childs.size() > 0) {
                return childs.get(childPosition);
            } else {
                return childs != null ? childs.get(0) : null;
            }
        } else {
            return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tp_bom, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.setData(groupPosition, mData.get(groupPosition));
        return convertView;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tp_bom_list_status, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.setData(childPosition, mData.get(groupPosition).getDetailList().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class GroupViewHolder {
        @BindView(R.id.iv_tuopan_kind)
        ImageView mIvTuopanKind;
        @BindView(R.id.tv_tuopan_name)
        TextView mTvTuopanName;
        @BindView(R.id.tv_tuopan_jingzai)
        TextView mTvTuopanJingzai;
        @BindView(R.id.tv_tp_out_num_name)
        TextView mTvTpOutNumName;
        @BindView(R.id.tv_tuopan_yanshou)
        TextView mTvTuopanYanshou;
        @BindView(R.id.tv_tuopan_dongzai)
        TextView mTvTuopanDongzai;
        @BindView(R.id.tv_tuopan_baoxiu_name)
        TextView mTvTuopanBaoxiuName;
        @BindView(R.id.tv_tuopan_baoxiu)
        TextView mTvTuopanBaoxiu;
        @BindView(R.id.tv_tuopan_type)
        TextView mTvTuopanType;
        @BindView(R.id.tv_tuopan_num)
        TextView mTvTuopanNum;
        @BindView(R.id.rltwo)
        LinearLayout mRltwo;
        @BindView(R.id.iv_tuopan_kind_cargo)
        ImageView mIvTuopanKindCargo;
        @BindView(R.id.tv_tuopan_name_cargo)
        TextView mTvTuopanNameCargo;
        @BindView(R.id.tv_out_name_by_num)
        TextView mTvOutNameByNum;
        @BindView(R.id.tv_out_num)
        TextView mTvOutNum;
        @BindView(R.id.tv_out_name_by_weight)
        TextView mTvOutNameByWeight;
        @BindView(R.id.tv_out_weight)
        TextView mTvOutWeight;
        @BindView(R.id.tv_cargo_num)
        TextView mTvCargoNum;
        @BindView(R.id.tv_cargo_weight)
        TextView mTvCargoWeight;
        @BindView(R.id.rlthree)
        LinearLayout mRlthree;
        @BindView(R.id.tv_tpNO)
        TextView mTvTpNO;
        @BindView(R.id.tv_tpOrCon)
        TextView mTvTpOrCon;
        @BindView(R.id.tv_tp_fenqu)
        TextView mTvTpFenqu;

        @BindView(R.id.llfour)
        LinearLayout mLlfour;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final BomDetailsModel.GatherListBean singleData) {

            final String tmpImageUrl = singleData.getPalletImgPath1();


            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);

            mIvTuopanKind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new BigPicPopwindow().showPW((Activity) mContext,singleData.getModelId(),"TP",rootView);

                }
            });

            mTvTuopanName.setText(singleData.getPalletName() +singleData.getPalletSpec());//托盘名称
            mTvTuopanJingzai.setText("静载: " + singleData.getStaticLoad() + "T");//静载重量
            mTvTuopanDongzai.setText("动载: " + singleData.getDynamicLoad() + "T");//动载重量
            mTvTuopanNum.setText("x" + singleData.getPalletCnt());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());


            mTvOutNameByNum.setText("出库数量: ");
            mTvTuopanBaoxiuName.setText("异常托数: ");
            mTvTpOutNumName.setText("出库托数: ");

            mTvTuopanYanshou.setText(singleData.getEndCnt() +"");
            mTvTuopanBaoxiu.setText(singleData.getUnusualCnt() +"");
            if (null != singleData.getGoods()) {
                mRlthree.setVisibility(View.VISIBLE);
                //货物
                final String tmpCargoImageUrl = singleData.getGoods().getGoodsImgPath1();
                mImageLoader.displayImage(mContext, tmpCargoImageUrl, mIvTuopanKindCargo, R.mipmap.icon_de_kind);
                mTvTuopanNameCargo.setText(singleData.getGoods().getGoodsName() + " " + singleData.getGoods().getGoodsModel());//
                mTvCargoNum.setText("x" + singleData.getGoodsCnt());//货物数量
                mTvCargoWeight.setText(singleData.getGoodsWeight() + "kg");//货物重量

                mTvOutNum.setText(singleData.getEndGoodsCnt()+"");
                mTvOutWeight.setText(singleData.getEndGoodsWeight()  +"kg");

                //点击进入查看货物详情
                mRlthree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WebServiceViewActivity.class);
                        intent.putExtra("LoadingUrl", UrlTools.getInterfaceUrl(UrlTools.getGoodsInfo(singleData.getGoodsId())));
                        intent.putExtra("title", "货物详情");
                        mContext.startActivity(intent);
                    }
                });

            } else {
                mRlthree.setVisibility(View.GONE);


            }

        }

    }

    class ChildViewHolder {
        @BindView(R.id.tv_No)
        TextView mTvNo;
        @BindView(R.id.tv_num)
        TextView mTvNum;
        @BindView(R.id.tv_cargo_info)
        TextView mTvCargoInfo;
        @BindView(R.id.tv_kuqu_num)
        TextView mTvKuquNum;
        @BindView(R.id.tv_yanshou)
        TextView mTvYanshou;
        @BindView(R.id.tv_baoxiu)
        TextView mTvBaoxiu;
        @BindView(R.id.ll_bg)
        LinearLayout mLlBg;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final BomDetailsModel.GatherListBean.DetailListBean singleData) {

            if ((positions +1)%2 !=0 ){
                if (singleData.isLastCheck()){
                    mLlBg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_orange_gray));
                }else {
                    mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
                }
            }else {
                if (singleData.isLastCheck()){
                    mLlBg.setBackground(mContext.getResources().getDrawable(R.drawable.bg_check_orange_white));
                }else {
                    mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }

            }
            mTvNo.setText(getNewNum(positions+1));
            mTvNum.setText(singleData.getPalletNum());
            mTvCargoInfo.setText("数量 : " + singleData.getGoodsCnt()+" " + "重量 : "+singleData.getGoodsWeight() +"kg" );
            mTvKuquNum.setText(singleData.getStorageAreaName());
            //货物备货单状态：0待备货 1已备货

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvCargoInfo.setTextColor(getColorByStatus(singleData.getStatus() ));
            mTvKuquNum.setTextColor(getColorByStatus(singleData.getStatus() ));

            mTvYanshou.setText("出库");
            mTvBaoxiu.setText("取消");
            mTvYanshou.setTextColor(getColorByStatus(singleData.getStatus() ));
            mTvBaoxiu.setTextColor(getColorByStatus(singleData.getStatus() ));
            mTvYanshou.setBackground(getBtBgByStatus(singleData.getStatus()));
            mTvBaoxiu.setBackground(getBtBgByStatus(singleData.getStatus()));

            mTvYanshou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myOrderOkClick(positions,singleData);
                }
            });
            mTvBaoxiu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBtItem.myOrderCancelClick(positions,singleData);
                }
            });

        }
    }


    private String getNewNum(int num){

        if (num <= 9){
            return "0"+num;
        }else {
            return num +"";
        }

    }

    /**
     *
     * @param status  //货物备货单状态：0待备货 1已备货 2 ->解绑
     * @return 根据状态选择不同的颜色
     */
    private int getColorByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getColor(R.color.font_black);
        }else if ("1".equals(status)){
            return mContext.getResources().getColor(R.color.green_text);
        }else {
            return mContext.getResources().getColor(R.color.red);
        }


    }

    /**
     *
     * @param status  //货物备货单状态：0待备货 1已备货2 ->解绑
     * @return 根据状态选择不同的颜色
     */
    private Drawable getBtBgByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_black_bg_white);
        }else if ("1".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_green_bg_white);
        }else {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_red_bg_white);
        }


    }

}
