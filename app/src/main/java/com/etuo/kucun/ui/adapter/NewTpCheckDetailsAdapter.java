package com.etuo.kucun.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etuo.kucun.R;
import com.etuo.kucun.base.ExtraConfig;
import com.etuo.kucun.loader.GlideImageLoader;
import com.etuo.kucun.model.TpCheckDetailsModel;
import com.etuo.kucun.model.TpInStorageModel;
import com.etuo.kucun.ui.fragment.BigPicPopwindow;
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
 * 修订历史：
 * 托盘验收adapter
 * ================================================
 */

public class NewTpCheckDetailsAdapter extends BaseExpandableListAdapter {

    private List<TpCheckDetailsModel.PalletModelListBean> mData;
    private String mType;
    private OnClickBtItem onClickBtItem;

    /**
     * 图片处理
     **/
    private GlideImageLoader mImageLoader;
    private Context mContext;
    private View  rootView ;

    public NewTpCheckDetailsAdapter(Context mcontext, String type,View rootView) {
        this.mContext = mcontext;
        mImageLoader = new GlideImageLoader();
        mType = type;
        this.rootView = rootView;
    }


    //接口回调
    public interface OnClickBtItem {
        void myOrderOkClick(int position,TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData);
        void myOrderCancelClick(int position, TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData);

        void myOrderPassAllByModelClick(int position, TpCheckDetailsModel.PalletModelListBean singleData);
    }

    public void OnClickBtItem(OnClickBtItem onClickBtItem) {
        this.onClickBtItem = onClickBtItem;
    }

    /**
     * 添加数据
     **/
    public void updata(List<TpCheckDetailsModel.PalletModelListBean> groups) {
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

        return mData.get(groupPosition) == null ? 0 : mData.get(groupPosition).getCheckBillPalletList().size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mData != null && mData.size() > 0) {
            List<TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean> childs = mData.get(groupPosition).getCheckBillPalletList();
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
            convertView = View.inflate(mContext, R.layout.item_check_tp_details, null);
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
            convertView = View.inflate(mContext, R.layout.item_tp_yanshou_list_status, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.setData(childPosition, mData.get(groupPosition).getCheckBillPalletList().get(childPosition));
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
        @BindView(R.id.tv_ruku_name)
        TextView mTvRukuName;
        @BindView(R.id.tv_tuopan_yanshou)
        TextView mTvTuopanYanshou;
        @BindView(R.id.tv_tuopan_baoxiu_name)
        TextView mTvTuopanBaoxiuName;
        @BindView(R.id.tv_tuopan_baoxiu)
        TextView mTvTuopanBaoxiu;
        @BindView(R.id.tv_tuopan_dongzai)
        TextView mTvTuopanDongzai;
        @BindView(R.id.tv_tuopan_type)
        TextView mTvTuopanType;
        @BindView(R.id.tv_tuopan_num)
        TextView mTvTuopanNum;
        @BindView(R.id.rltwo)
        LinearLayout mRltwo;
        @BindView(R.id.tv_tpNO)
        TextView mTvTpNO;
        @BindView(R.id.tv_tpOrCon)
        TextView mTvTpOrCon;
        @BindView(R.id.tv_all_pass)
        TextView mTvAllPass;

        @BindView(R.id.llfour)
        LinearLayout mLlfour;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setData(final int positions, final TpCheckDetailsModel.PalletModelListBean singleData) {
            final String tmpImageUrl = singleData.getThumbnail();
            mImageLoader.displayImage(mContext, tmpImageUrl, mIvTuopanKind, R.mipmap.icon_de_kind);
            mIvTuopanKind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new BigPicPopwindow().showPW((Activity) mContext,singleData.getModelId(),"TP",rootView);

                }
            });
            mTvTuopanName.setText(singleData.getPalletName() + singleData.getPalletSpec());//托盘名称

            mTvTuopanNum.setText("x" + singleData.getQuantity());//托盘数量
            mTvTuopanType.setText(singleData.getPalletModel());//托盘型号
            mTvTuopanJingzai.setText("静载: " + singleData.getStaticLoad() + "T");//静载重量
            mTvTuopanDongzai.setText("动载: " + singleData.getDynamicLoad() + "T");//动载重量
            if (ExtraConfig.IntentType.FROM_INTENT_BY_SHOU.equals(mType)) {
                mTvRukuName.setText("已入库: ");
                mTvTuopanBaoxiuName.setText("保修: ");
            }
            mTvTuopanYanshou.setText(singleData.getHaveCheckCnt() + "");
            mTvTuopanBaoxiu.setText(singleData.getRepairsCnt() + "");


            //单型号全部验收
            mTvAllPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickBtItem.myOrderPassAllByModelClick(positions,singleData);


                }
            });

        }
        }


    class ChildViewHolder {
        @BindView(R.id.tv_No)
        TextView mTvNo;
        @BindView(R.id.tv_num)
        TextView mTvNum;
//        @BindView(R.id.tv_yanshou)
//        TextView mTvYanshou;
//        @BindView(R.id.tv_baoxiu)
//        TextView mTvBaoxiu;
        @BindView(R.id.ll_bg)
        LinearLayout mLlBg;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        void setData(final int positions, final TpCheckDetailsModel.PalletModelListBean.CheckBillPalletListBean singleData) {



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
            //0待验收 2已验收 4已报损

            mTvNo.setTextColor(getColorByStatus(singleData.getStatus()));
            mTvNum.setTextColor(getColorByStatus(singleData.getStatus()));

//
//            if ("0".equals(singleData.getStatus())){
//                mTvYanshou.setVisibility(View.VISIBLE);
//            }else if ("2".equals(singleData.getStatus())){
//                mTvYanshou.setVisibility(View.INVISIBLE);
//            }else {
//                mTvYanshou.setVisibility(View.INVISIBLE);
//            }
//
//            mTvYanshou.setText("验收");
//            mTvBaoxiu.setText("报修");
//            mTvYanshou.setTextColor(getColorByStatus(singleData.getStatus() ));
//            mTvBaoxiu.setTextColor(getColorByStatus(singleData.getStatus() ));
//            mTvYanshou.setBackground(getBtBgByStatus(singleData.getStatus()));
//            mTvBaoxiu.setBackground(getBtBgByStatus(singleData.getStatus()));
//
//
//            mTvYanshou.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickBtItem.myOrderOkClick(positions,singleData);
//                }
//            });
//            mTvBaoxiu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickBtItem.myOrderCancelClick(positions,singleData);
//                }
//            });
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
     * @param status  //货物备货单状态：0待验收 2已验收 4已报损
     * @return 根据状态选择不同的颜色
     */
    private int getColorByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getColor(R.color.font_black);
        }else if ("2".equals(status)){
            return mContext.getResources().getColor(R.color.green_text);
        }else {
            return mContext.getResources().getColor(R.color.red);
        }


    }

    /**
     *
     * @param status  //货物备货单状态：0待验收 2已验收 4已报损
     * @return 根据状态选择不同的颜色
     */
    private Drawable getBtBgByStatus(String status){

        if ("0".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_black_bg_white);
        }else if ("2".equals(status)){
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_green_bg_white);
        }else {
            return mContext.getResources().getDrawable(R.drawable.shape_rectangle_line_red_bg_white);
        }


    }

}
