package org.demon.module.auth;

import org.demon.sdk.model.entity.User;
import org.demon.sdk.retCode.BizRetCode;
import org.demon.starter.exception.LogicalException;
import org.demon.utils.XCodeUtil;
import org.demon.utils.crypto.SSHAUtils;

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
            throw new LogicalException(BizRetCode.ERR_USER_INFO_BROKEN);
        }

        String format = "'{'{0}'}'{1}";
        MessageFormat mf = new MessageFormat(format);
        Object[] objs = mf.parse(psw);
        String algorithm = (String) objs[0];
        String shadow = (String) objs[1];

        if (AuthConfig.ALG_MD5.equals(algorithm)) {
            String tmp = XCodeUtil.base64ToBase16(shadow);
            password = XCodeUtil.getMD5(password);
            return tmp.equals(password);
        }
        else if (AuthConfig.ALG_SSHA.equals(algorithm)) {
            return SSHAUtils.verifySaltedPassword(password.getBytes(), psw);
        }

        return false;
    }

    /**
     * 校验账号是否合法
     * @param type 账号类型: name/email/phone
     * @param account 账号
     */
    public static void checkAccount(String type, String account) throws LogicalException {
        if (!(AuthConfig.LOGINID_NAME.equals(type) || AuthConfig.LOGINID_EMAIL.equals(type) ||AuthConfig.LOGINID_PHONE.equals(type))) {
            throw new LogicalException(BizRetCode.ERR_ILLEGAL_ACCOUNT_TYPE);
        }

        if (AuthConfig.LOGINID_EMAIL.equals(type) && !account.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")) {
            throw new LogicalException(BizRetCode.ERR_ILLEGAL_EMAIL_ACCOUNT);
        }

        if (AuthConfig.LOGINID_PHONE.equals(type) && !account.matches("^1[34578][0-9]{9}")) {
            throw new LogicalException(BizRetCode.ERR_ILLEGAL_PHONE_ACCOUNT);
        }
    }
}
