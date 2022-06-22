package com.etuo.kucun.model.common;

import java.io.Serializable;
import java.util.List;

public class LzyResponse<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;
    public String code; //pc使用
    public String status;// 手机端使用
    public String message;
    public String token;// token 值
    public String userId;// 用户id
    public String companyId;

    public String commonFiled;// 共同的 放string 数据

    public String buttonFlg;//能否操作数据
    public List<ReturnReasonListBean> commonList;

    public T data;
    public T bean;
}