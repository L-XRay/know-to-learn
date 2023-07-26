package com.cqupt.knowtolearn.service.captcha.impl;

import com.cqupt.knowtolearn.service.captcha.ICaptchaGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Ray
 * @date 2023/7/25 09:42
 * @description
 */
@Component("simpleCaptchaGenerator")
public class SimpleCaptchaGenerator implements ICaptchaGenerator {

    @Override
    public String generate(int n) {
        // 定义随机数构造
        Random random = new Random();
        // 定义空字符串保存验证码
        StringBuilder code = new StringBuilder();
        // 定义一个for循环，表示生成位验证码
        for (int i = 0; i < n; i++) {
            // 根据随机数选择各个位置的字符表示
            int r = random.nextInt(3);
            switch (r){
                // 大写字母，A 65 - Z 90  随机数 （0-25）+ 65
                case 0: char a = (char) (random.nextInt(26)+65);
                    code.append(a);
                    break;
                // 小写字母，a 97 - z 122  随机数 （0-25）+ 97
                case 1:  char c = (char) (random.nextInt(26)+97);
                    code.append(c);
                    break;
                // 数字字符，0-9
                case 2:  int x = random.nextInt(10);
                    code.append(x);
                    break;
            }
        }
        return code.toString();
    }
}
