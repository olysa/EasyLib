package com.dingfang.org.example.okhttp.model;

import java.io.Serializable;

/**
 * 这个类是给 LzyResponse 类 打辅助的，一般不直接使用
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int status;
    public String returnMsg;

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.status = status;
        lzyResponse.returnMsg = returnMsg;
        return lzyResponse;
    }
}
