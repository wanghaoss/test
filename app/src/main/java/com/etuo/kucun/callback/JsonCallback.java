package com.etuo.kucun.callback;

import android.content.Intent;
import android.util.JsonReader;


import com.etuo.kucun.FrameworkApp;
import com.etuo.kucun.model.common.LzyResponse;
import com.etuo.kucun.model.common.SimpleResponse;
import com.etuo.kucun.ui.activity.LoginActivity;
import com.etuo.kucun.utils.Convert;
import com.etuo.kucun.utils.LogUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private String message;
    @Override
    public T convertSuccess(Response response) throws Exception {

        //以下代码是通过泛型解析实际参数,泛型必须传
        //这里为了方便理解，假如请求的代码按照上述注释文档中的请求来写，那么下面分别得到是

        //com.lzy.demo.callback.DialogCallback<com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>> 得到类的泛型，包括了泛型参数
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.LzyResponse<com.lzy.demo.model.ServerModel>
        Type type = params[0];

        // 这里这么写的原因是，我们需要保证上面我解析到的type泛型，仍然还具有一层参数化的泛型，也就是两层泛型
        // 如果你不喜欢这么写，不喜欢传递两层泛型，那么以下两行代码不用写，并且javabean按照
        // https://github.com/jeasonlzy/okhttp-OkGo/blob/master/README_JSONCALLBACK.md 这里的第一种方式定义就可以实现
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        //如果确实还有泛型，那么我们需要取出真实的泛型，得到如下结果
        //class com.lzy.demo.model.LzyResponse
        //此时，rawType的类型实际上是 class，但 Class 实现了 Type 接口，所以我们用 Type 接收没有问题
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里获取最终内部泛型的类型 com.lzy.demo.model.ServerModel
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (typeArgument == Void.class) {
            //无数据类型,表示没有data数据的情况（以  new DialogCallback<LzyResponse<Void>>(this)  以这种形式传递的泛型)
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            //noinspection unchecked
            return (T) simpleResponse.toLzyResponse();
        } else if (rawType == LzyResponse.class) {
            String json = StringConvert.create().convertSuccess(response);
            //有数据类型，表示有data
            if (parseResponse(json)) {
//                EventBus.getDefault().post("TokenFailure");
//                PreferenceCache.clearAllUserPwd();
                Intent intent = new Intent(FrameworkApp.getAppContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//关掉所要到的界面中间的activity
                FrameworkApp.getAppContext().startActivity(intent);

                if (!"".equals(message) && message!=null){
                    throw new IllegalStateException(message);
                }else{
                    throw new IllegalStateException("登录超时，请重新登录");
                }
            } else {
                LzyResponse lzyResponse = Convert.fromJson(json, type);
                response.close();
                String code = lzyResponse.code;

                if (code == null ){
                    code =lzyResponse.status;
                }

                //这里的0是以下意思
                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
                if (code.equals("1")) {
                    //noinspection unchecked
                    return (T) lzyResponse;
                } else if (code.equals("0")) {
                    //比如：验证码失效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                    LogUtil.e(code , "错误  :  "+lzyResponse.message );
                    throw new IllegalStateException(lzyResponse.message);
                }
                else {
//                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + lzyResponse.message);

                    throw new IllegalStateException(lzyResponse.message);
                }
            }
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }


    // 验证 token 是否过期
    private boolean parseResponse(String response) throws Exception {
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response).getAsJsonObject();
            JsonElement code = jsonObject.get("code");

            if (code == null){
                code = jsonObject.get("status");
            }

            if (code ==null){
                return  false;
            }

            String   codeV = code.getAsString();

            message=jsonObject.get("message").getAsString();
            if ("401".equals(codeV) || "888".equals(codeV)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}