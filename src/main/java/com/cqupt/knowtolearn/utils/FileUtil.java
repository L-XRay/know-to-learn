package com.cqupt.knowtolearn.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author Ray
 * @date 2023/7/26 17:10
 * @description
 */
public class FileUtil {
    public String readFileToString(InputStream in, Charset charset) throws IOException {
        StringBuilder resultString = new StringBuilder();
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1) {
            resultString.append(new String(buf, 0, len, charset));
        }
        in.close();
        return resultString.toString();
    }
}
