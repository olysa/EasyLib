package com.dingfang.org.example.okhttp.model;

import java.io.Serializable;

/**
 * 请求结果泛型
 * Created by zuoqing on 2017/10/11.
 */

public class LzyResponse<T> implements Serializable{
    private static final long serialVersionUID = 5213230387175987834L;

    public int status;
    public String message;
    public T result;

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\tcode=" + status + "\n" +//
                "\tmsg='" + message + "\'\n" +//
                "\tdata=" + result + "\n" +//
                '}';
    }
}
