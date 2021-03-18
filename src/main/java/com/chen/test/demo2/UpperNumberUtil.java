package com.chen.test.demo2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/2/23
 **/
public class UpperNumberUtil {

    private static final Pattern AMOUNT_PATTERN1 = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$"); // 不考虑分隔符的正确性
    private static final Pattern AMOUNT_PATTERN2 = Pattern.compile("^(0|[1-9]\\d{0,11})$"); // 不考虑分隔符的正确性
    private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
//    private static final String[] UNITS = { "元", "角", "分", "整" };
    private static final String[] UNITS = { "点", "整" };
    private static final String[] U1 = { "", "拾", "佰", "仟" };
    private static final String[] U2 = { "", "万", "亿" };

    /**
     * 将金额（整数部分等于或少于 12 位，小数部分 2 位）转换为中文大写形式.
     *
     * @param amount 金额数字
     * @return 中文大写
     * @throws IllegalArgumentException
     */
    public static String convert(String amount) throws IllegalArgumentException {
        // 去掉分隔符
        amount = amount.replace(",", "");
        // 验证金额正确性
        if (amount.equals("0.00")) {
            throw new IllegalArgumentException("金额不能为零.");
        }
        Matcher matcher1 = AMOUNT_PATTERN1.matcher(amount);
        Matcher matcher2 = AMOUNT_PATTERN2.matcher(amount);
        String result = "";
        if (matcher1.find()) {
            String integer = matcher1.group(1); // 整数部分
            String fraction = matcher1.group(2); // 小数部分
            if (!integer.equals("0") && !fraction.equals("00")) {
                result += integer2rmb(integer) + UNITS[0]; // 整数部分
            }
            if (!integer.equals("0") && fraction.equals("00")) {
                result += integer2rmb(integer); // 整数部分
            }
            if (fraction.equals("00")) {
                result += UNITS[1]; // 添加[整]
            } else if (fraction.startsWith("0") && integer.equals("0")) {
                result += fraction2rmb(fraction).substring(1); // 去掉分前面的[零]
            } else {
                result += fraction2rmb(fraction); // 小数部分
            }
        }
        if(matcher2.find()){
            String integer = matcher2.group(1); // 整数部分
            if (!integer.equals("0")) {
                result += integer2rmb(integer) + UNITS[1]; // 整数部分
            }
        }
        return result;
    }

    // 将金额小数部分转换为中文大写
    private static String fraction2rmb(String fraction) {
        char jiao = fraction.charAt(0); // 角
        char fen = fraction.charAt(1); // 分
        return RMB_NUMS[jiao - '0']
                + (fen > '0' ? RMB_NUMS[fen - '0'] + "" : "");
    }

    // 将金额整数部分转换为中文大写
    private static String integer2rmb(String integer) {
        StringBuilder buffer = new StringBuilder();
        // 从个位数开始转换
        int i, j;
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
            char n = integer.charAt(i);
            if (n == '0') {
                // 当 n 是 0 且 n 的右边一位不是 0 时，插入[零]
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
                    buffer.append(RMB_NUMS[0]);
                }
                // 插入[万]或者[亿]
                if (j % 4 == 0) {
                    if (i > 0 && integer.charAt(i - 1) != '0' || i > 1 && integer.charAt(i - 2) != '0'
                            || i > 2 && integer.charAt(i - 3) != '0') {
                        buffer.append(U2[j / 4]);
                    }
                }
            } else {
                if (j % 4 == 0) {
                    buffer.append(U2[j / 4]); // 插入[万]或者[亿]
                }
                buffer.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
                buffer.append(RMB_NUMS[n - '0']); // 插入数字
            }
        }
        return buffer.reverse().toString();
    }
}
