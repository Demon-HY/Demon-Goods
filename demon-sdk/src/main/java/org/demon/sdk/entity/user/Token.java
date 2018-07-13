package org.demon.sdk.entity.user;

import org.apache.commons.codec.binary.Base64;
import org.demon.utils.datetime.Time;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

public class Token {

    /** 用户登录凭据 */
    public String token;
    /** 用户ID */
    public long uid;
    /** token的有效期限 */
    public Date expires;
    /** 创建时间 */
    public Date ctime;
    /** 客户端IP */
    public String ip;
    /** 客户端设备 */
    public String device;

    public Token(String token, long uid, Date expires, Date ctime, String ip) {
        this.token = token;
        this.uid = uid;
        this.expires = expires;
        this.ctime = ctime;
        this.ip = ip;
    }

    public Token(String token, long uid, Date expires, Date ctime, String ip, String device) {
        this.token = token;
        this.uid = uid;
        this.expires = expires;
        this.ctime = ctime;
        this.ip = ip;
        this.device = device;
    }

    /**
     * 新建token信息
     * @param uid 用户id
     * @param age token寿命
     * @param ip 用户的ip地址
     * @return Token
     */
    public static Token newToken(long uid, long age, String ip) {
        long now = Time.currentTimeMillis();
        return new Token(makeToken(), uid, new Date(now + age), new Date(now), ip);
    }
    /**
     * 新建token信息
     * @param uid 用户id
     * @param age token寿命
     * @param ip 用户的ip地址
     * @param device 用户登录设备
     * @return Token
     */
    public static Token newToken(long uid, long age, String ip, String device) {
        long now = Time.currentTimeMillis();
        return new Token(makeToken() + "@" + uid, uid, new Date(now + age), new Date(now), ip, device);
    }

    /**
     * 生成token字符串
     * @return token
     */
    private static String makeToken() {
        long uuid = UUID.randomUUID().getMostSignificantBits();
        byte[] uuidBytes = ByteBuffer.allocate(8).putLong(uuid).array();
        return Base64.encodeBase64URLSafeString(uuidBytes);
    }
}
