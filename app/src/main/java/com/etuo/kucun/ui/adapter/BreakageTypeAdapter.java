package com.etuo.kucun.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.etuo.kucun.R;
import com.etuo.kucun.model.BreakageTypeModel;


import java.util.List;

/**
 * @author yhn
 * @version V1.0
 * @Description:保存类型
 * @date 2018/11/26
 */
public class BreakageTypeAdapter extends BaseAdapter {
	private List<BreakageTypeModel> projectList = null; // 项目类型
	private LayoutInflater layoutInflater;
	private String productType;
	private ProjectStatusCallBack projectStatusCallBack;
	private Context mContext;
	private String damagedStatus;

	public BreakageTypeAdapter(List<BreakageTypeModel> projectList, String productType, Context context,String damagedStatus) {
		this.projectList = projectList;
		this.layoutInflater = LayoutInflater.from(context);
		this.productType = productType;
		this.mContext = context;
		this.damagedStatus = damagedStatus;


	}

	public void setPosition(String productType) {
		this.productType = productType;

	}

	public void setCallBack(ProjectStatusCallBack projectStatusCallBack) {
		this.projectStatusCallBack = projectStatusCallBack;
	}


	public interface ProjectStatusCallBack {
		void onProjectStatus(String position,ViewGroup parent);
	}
	@Override
	public int getCount() {
		return projectList.size();
	}

	@Override
	public Object getItem(int position) {
		return projectList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final ViewHolder viewHodler;
		if (convertView == null) {
			viewHodler = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.select_griditem, null);
			viewHodler.check = (CheckBox) convertView.findViewById(R.id.check);
			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHolder) convertView.getTag();
		}
		if (damagedStatus.equals("1")){ //新增
			if (productType.equals(String.valueOf(position+1))) {
				viewHodler.check.setChecked(true);
				viewHodler.check.setClickable(false);
				viewHodler.check.setTextColor(mContext.getResources().getColor(R.color.white));

			} else {
				viewHodler.check.setChecked(false);
				viewHodler.check.setClickable(true);
				viewHodler.check.setTextColor(mContext.getResources().getColor(R.color.font_black));
			}
		}else { // 详情
			viewHodler.check.setClickable(false);
			if (productType.equals(String.valueOf(position+1))) {
				viewHodler.check.setChecked(true);

				viewHodler.check.setTextColor(mContext.getResources().getColor(R.color.white));
			} else {
				viewHodler.check.setChecked(false);

				viewHodler.check.setTextColor(mContext.getResources().getColor(R.color.font_black));
			}
		}


		viewHodler.check.setText(projectList.get(position).getTypeName());
		viewHodler.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked) {
					productType = String.valueOf(position+1);
					projectStatusCallBack.onProjectStatus(productType, parent);
					notifyDataSetChanged();
				}
			}
		});
		return convertView;
	}

	class ViewHolder {
		CheckBox check;
	}
}
