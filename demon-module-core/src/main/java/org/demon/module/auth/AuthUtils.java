package org.demon.module.auth;

import org.demon.sdk.entity.User;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.XCodeUtil;
import org.demon.utils.crypto.SSHA;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.ParseException;

public class AuthUtils {

    /**
     * 检查用户密码
     *
     * @param user
     *            用户信息
     * @param password
     *            密码
     * @return
     * @throws LogicalException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    public static boolean checkPassword(User user, String password)
            throws LogicalException, NoSuchAlgorithmException,
            UnsupportedEncodingException, ParseException {

        if (null == user || password == null) {
            throw new IllegalArgumentException();
        }

        String psw = user.password;

        if (psw == null || psw.length() == 0) {
            throw new LogicalException(RetCodeEnum.ERR_USER_INFO_BROKEN);
        }

        String format = "'{'{0}'}'{1}";
        MessageFormat mf = new MessageFormat(format);
        Object[] objs = mf.parse(psw);
        String algorithm = (String) objs[0];
        String shadow = (String) objs[1];

        if (AuthConfig.ALG_MD5.equals(algorithm)) {
            String tmp = XCodeUtil.base64ToBase16(shadow);
            password = XCodeUtil.getMD5(password);
            if (tmp.equals(password)) {
                return true;
            }
        } else if (AuthConfig.ALG_SSHA.equals(algorithm)) {
            return SSHA.verifySaltedPassword(password.getBytes(), psw);
        }

        return false;
    }
}
