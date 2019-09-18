package com.caine.platform.common.security;

import com.caine.platform.common.constant.Constant;
import lombok.SneakyThrows;

import java.security.MessageDigest;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 11:34 2019/9/17
 * @Modified By:
 */
public class SHA256Tool {

    @SneakyThrows
    public static String getSHA256(String val) {
        MessageDigest messageDigest;
        String encodeStr = "";
        messageDigest = MessageDigest.getInstance(Constant.SHA256);
        messageDigest.update(val.getBytes("UTF-8"));
        encodeStr = byte2HexString(messageDigest.digest());
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2HexString(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
