package org.demon.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class RandomUtil {
    
    public static String uuidStr() {
        long uuid = UUID.randomUUID().getMostSignificantBits();
        byte[] uuidBytes = ByteBuffer.allocate(8).putLong(uuid).array();
        return Base64.encodeBase64URLSafeString(uuidBytes);     
    }

    /**
     * 获取请求唯一标识，长度16位
     */
    public static String getRequestId() {
        long uuid = UUID.randomUUID().getMostSignificantBits();
        byte[] uuidBytes = ByteBuffer.allocate(8).putLong(uuid).array();
        return Base64.encodeBase64URLSafeString(uuidBytes).toLowerCase();
    }
    
}
