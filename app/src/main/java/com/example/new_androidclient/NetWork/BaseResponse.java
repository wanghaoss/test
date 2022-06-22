package com.example.new_androidclient.NetWork;

public class BaseResponse<T> {

    /**
     * code : 0
     * msg : success
     * data : {"firstName":"管","lastName":"理员","tenantId":13,"userId":20,"email":"dandong@zhuningkeji.com","token":"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI0Nzc0NGY1MjJjNzBkN2Y0NzhjMmMyNWQ1MDg4NGQ1NCIsImV4cCI6MTU5MTA5NDEwMH0.lWQeQfv9F1g7oYOjFbEuK1aKjENAlCucM0fZrlRt99I"}
     */

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
