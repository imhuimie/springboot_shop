package com.example.shop.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * HTTP请求响应体
 * <p>
 *     参数含义如下：            <br>
 *     200 - 正常响应           <br>
 *     400 - 客户端发送数据有误   <br>
 *     500 - 服务端异常          <br>
 * </p>
 */
@Data
@AllArgsConstructor
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public static R ok(Object data) {
        return new R(200, "okay", data);
    }

    public static R of(int code, String msg, Object data) {
        return new R(code, msg, data);
    }
}
