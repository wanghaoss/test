package com.example.new_androidclient.NetWork;

import com.example.new_androidclient.Util.ExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        if (tBaseResponse.getCode() != 0) {
            onFailure(tBaseResponse.getMsg());
        }
//        else if(tBaseResponse.getData() == null && tBaseResponse.getCode() == 0){
//            onFailure("接口暂无数据");
//        }
        else if (tBaseResponse.getCode() == 401) {
            onFailure(tBaseResponse.getMsg());
        } else {
            onSuccess(tBaseResponse.getData());
        }
    }


    @Override
    public void onError(Throwable e) {
        onFailure(ExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T bean);

    public abstract void onFailure(String err);
}
