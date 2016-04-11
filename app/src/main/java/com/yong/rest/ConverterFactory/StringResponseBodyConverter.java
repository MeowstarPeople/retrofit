package com.yong.rest.ConverterFactory;

import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import retrofit.Converter;

/**
 * Created by CuiXiaoYong
 * on 2016/3/18.
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {

    /**
     * 重写convert方法，将ResponseBody转换为String
     *
     * @param value
     * @return String
     * @throws IOException
     */
    @Override
    public String convert(ResponseBody value) throws IOException {
        InputStream reader = value.byteStream();
        return fromStream(reader);
    }

    private String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
        StringBuffer out = new StringBuffer();
        String line=null;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }

        if (null != reader) {
            in.close();
        }

        return out.toString();
    }
}
