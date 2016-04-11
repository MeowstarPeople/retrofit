package com.yong.rest.ConverterFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter.Factory;

import com.squareup.okhttp.ResponseBody;

/**
 * Created by CuiXiaoYong
 * on 2016/3/18.
 */

/**
 * 自定义返回String类型
 */
public class StringConverterFactory extends Factory {

    /**
     * 创建StringConverterFactory对象
     *
     * @return StringConverterFactory
     */
    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    @Override
    public retrofit.Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new StringResponseBodyConverter();
    }
}
