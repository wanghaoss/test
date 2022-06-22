package com.etuo.kucun.model.common;

import java.io.Serializable;
import java.util.List;

public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public String code;
    public String message;
    public String status;
    public String token;// token 值
    public String userId;// 用户id
    public String commonFiled;//
    private String buttonFlg;//能否操作数据
    public   List<ReturnReasonListBean> commonList;


    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.code = code;
        lzyResponse.status = status;
        lzyResponse.message = message;
        lzyResponse.token = token;
        lzyResponse.userId = userId;
        lzyResponse.commonFiled = commonFiled;
        lzyResponse.buttonFlg =  buttonFlg;//能否操作数据
        lzyResponse.commonList = commonList;
        return lzyResponse;
    }
}