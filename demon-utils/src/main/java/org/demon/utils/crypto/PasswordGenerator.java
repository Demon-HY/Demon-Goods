package org.demon.utils.crypto;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.zip.CRC32;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户密码加密解密
 * @author monitor
 * @see 2016-3-14 16:13:06
 */
public class PasswordGenerator {
 
	private static final String PASSWORD_KEY = "H0eYaNPasW0e";
	private static final String CRC_KEY = "MQP0TGVX0KFBX5F6";
	/**
	 *  加密后的密码的前三位的无效码
	 */
	private static final int PREFIX_LENGTH = 3;
	private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXWZ";
	
	/**
	 * 创建加密密码
	 */
	public static String createPassword(String name, String password, String creator, Object...params) {
		JSONObject json = new JSONObject();
		json.put("password", password);

        String info = json.toJSONString();
        byte[] data = info.getBytes();
        
        passwordCode(data, PASSWORD_KEY);
        byte[] crc = crcUnsigned(data);
        byte[] tmp = new byte[data.length + crc.length];
        for (int i = 0; i < data.length; i++) {
            tmp[i] = data[i];
        }
        for (int i = 0; i < crc.length; i++) {
            tmp[i+data.length] = crc[i];
        }
        data = tmp;
        return createPrefix(PREFIX_LENGTH) + Base64.encodeBase64URLSafeString(data);
	}
	/**
	 * 解密密码
	 * @param password
	 * @return 用户名,密码,作者三者的 Map 集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parsePassword(String password) {
		String real = password.substring(PREFIX_LENGTH);
		byte[] rst = Base64.decodeBase64(real);
		
		byte[] data = Arrays.copyOf(rst, rst.length-(Long.SIZE / Byte.SIZE));
		byte[] crc = Arrays.copyOfRange(rst, data.length, rst.length);
		
		long value = byteArrayToLong(crc);
		byte[] realCrc = crcUnsigned(data);
		long realValue = byteArrayToLong(realCrc);
		if (!(value == realValue)) {
		    System.out.println("CRC verify failed.");
		    System.exit(0);
		}
		
		passwordCode(data, PASSWORD_KEY);
		
		String info = new String(data);
		
		return JSONObject.parseObject(info, Map.class);
	}
	/**
	 * 密码编码
	 * @param data
	 * @param key
	 */
	private static void passwordCode(byte[] data, String key) {
		for (int i = 0, j = 0; i < data.length; i++) {
			byte d = data[i];
			char k = key.charAt(j);
			byte tmp = (byte) (d ^ k);
			data[i] = tmp;
			j++;
			if (j >= key.length()) {
				j = 0;
			}
		}
	}
	
	private static byte[] crcUnsigned(byte[] str) {
        CRC32 crc = new CRC32();
        crc.update(str);
        crc.update(CRC_KEY.getBytes());
        
        long value = crc.getValue();
        if (value < 0) {
            value = 0xFFFFFFFF & value;
        }
        return longToByteArray(value);
    }
	
	private static byte[] longToByteArray(long value) {
        ByteBuffer bb = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putLong(value);
        return bb.array();
    }
	
	private static long byteArrayToLong(byte[] data) {
        long value = 0;
        for (int i = 0; i < data.length; i++) {
            long tmp = 0x000000ff & data[i];
            
            value += tmp << (8*i);
        }
        return value;
    }
	
	private static String createPrefix(int prefixLength) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < prefixLength; i++) {
			int tmp = random.nextInt();
			if (tmp < 0) {
				tmp = -tmp;
			}
			int index = tmp % DIGITS.length();
			sb.append(DIGITS.charAt(index));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String passCode = createPassword("18671186205", "ksbau1", "Demon-HY");
		System.out.println(passCode);
		Map<String, Object> infos = parsePassword(passCode);
		System.out.println(infos.get("username"));
		System.out.println(infos.get("password"));
		System.out.println(infos.get("creator"));
	}
}
