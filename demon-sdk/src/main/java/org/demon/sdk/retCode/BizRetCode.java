package org.demon.sdk.retCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.demon.starter.common.retCode.BaseRetCode;

/**
 * 业务相关错误码 [3位的业务类型,3位的具体错误码]
 * 300XXX - 用户
 * 301XXX - 鉴权
 * 302XXX - 角色
 * 303XXX - 权限
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizRetCode extends BaseRetCode {

    // 用户
    /** 用户不存在 */
    public static RetCode ERR_NO_SUCH_ACCOUNT = RetCode.newInstance("用户账号不存在", 300000);
    /** 用户不存在 */
    public static RetCode ERR_USER_NOT_FOUND = RetCode.newInstance("用户不存在", 300001);
    /** 禁止修改账号信息 */
    public static RetCode ERR_REVISE_ACCOUNT_FORBIDDEN = RetCode.newInstance("禁止修改账号信息", 300002);
    /** 创建用户失败 */
    public static RetCode ERR_CREATE_USER_FAILED = RetCode.newInstance("创建用户失败", 300003);
    /** 设置用户属性失败 */
    public static RetCode ERR_SET_USER_ATTR_FAILED = RetCode.newInstance("设置用户属性失败", 300004);
    /** 删除用户失败 */
    public static RetCode ERR_DELETE_USER_FAILED = RetCode.newInstance("删除用户失败", 300005);
    /** 该账号已被使用 */
    public static RetCode ERR_ACCOUNT_EXIST = RetCode.newInstance("该账号已被使用", 300006);
    /** 手机已被使用 */
    public static RetCode ERR_PHONE_USED = RetCode.newInstance("手机已被使用", 300007);
    /** 邮箱已被使用 */
    public static RetCode ERR_EMAIL_USED = RetCode.newInstance("邮箱已被使用", 300008);
    /** 无效电话号码 */
    public static RetCode ERR_ILLEGAL_PHONE = RetCode.newInstance("无效电话号码", 300009);
    /** 无效密码 */
    public static RetCode ERR_ILLEGAL_PASSWORD = RetCode.newInstance("无效密码", 300010);
    /** 用户信息损坏 */
    public static RetCode ERR_USER_INFO_BROKEN = RetCode.newInstance("用户信息损坏", 300011);
    /** 密码错误 */
    public static RetCode ERR_WRONG_PASSWORD = RetCode.newInstance("密码错误", 300012);
    /** 设置密码失败 */
    public static RetCode ERR_RESET_PASSWORD_FAILED = RetCode.newInstance("设置密码失败", 300013);
    /** 不能删除自己 */
    public static RetCode ERR_DELETE_SELF_FORBITEN = RetCode.newInstance("不能删除自己", 300014);
    /** 用户已被锁定 */
    public static RetCode ERR_USER_LOCKED = RetCode.newInstance("用户已被锁定", 300015);
    /** 用户已登录使用 */
    public static RetCode ERR_USER_ALREADY_USED = RetCode.newInstance("用户已登录使用", 300016);
    /** 验证码已过期,请重新获取 */
    public static RetCode ERR_REGISTER_CONFIRM_EXPIRED = RetCode.newInstance("验证码已过期,请重新获取", 300017);
    /** 找回密码邮件过期 */
    public static RetCode ERR_RETRIEVE_EMAIL_EXPIRED = RetCode.newInstance("找回密码邮件过期", 300018);
    /** 无效的用户名 */
    public static RetCode ERR_ILLEGAL_USER_NAME = RetCode.newInstance("无效的用户名", 300019);
    /** 不能删除系统初始创建的serverAdmin用户 */
    public static RetCode ERR_INIT_SERVERADMIN_USER = RetCode.newInstance("不能删除系统初始创建的serverAdmin用户", 300020);
    /** 用户已被删除 */
    public static RetCode ERR_USER_DELETE = RetCode.newInstance("用户已被删除", 300021);

    // 鉴权
    /** 无效令牌 */
    public static RetCode ERR_TOKEN = RetCode.newInstance("无效令牌", 301001);
    /** 手机号已经被绑定 */
    public static RetCode ERR_PHONE_ALREADY_BOUND = RetCode.newInstance("手机号已经被绑定", 301002);
    /** 请不要重复发送验证码 */
    public static RetCode ERR_SEND_CODE_FREQUENTLY = RetCode.newInstance("请不要重复发送验证码", 301003);
    /** 验证码过期 */
    public static RetCode ERR_VALIDATE_CODE_EXPIRED = RetCode.newInstance("验证码过期", 301004);
    /** 验证码错误 */
    public static RetCode ERR_INVALID_VALIDATE_CODE = RetCode.newInstance("验证码错误", 301005);
    /** 请登录 */
    public static RetCode ERR_USER_NOT_LOGIN = RetCode.newInstance("请登录", 301006);
    /** 请登录,Token没找到 */
    public static RetCode ERR_TOKEN_NOT_FOUND = RetCode.newInstance("请登录", 301007);
    /** 请重新登录,Token过期 */
    public static RetCode ERR_TOKEN_EXPIRED = RetCode.newInstance("请重新登录", 301008);
    /** 清除用户数据失败 */
    public static RetCode ERR_CLEAN_ACCOUNT_FAILED = RetCode.newInstance("清除用户数据失败", 301009);
    /** 注册登录信息失败 */
    public static RetCode ERR_ADD_LOGIN_ID_FAILED = RetCode.newInstance("注册登录信息失败", 301010);
    /** 登录的用户与新建token用户不为一个用户 */
    public static RetCode ERR_TOKEN_UID_MISMATCHING = RetCode.newInstance("登录的用户与新建token用户不为一个用户", 301011);
    /** 非法手机号 */
    public static RetCode ERR_ILLEGAL_PHONE_ACCOUNT = RetCode.newInstance("非法手机号", 301012);
    /** 非法账号类型 */
    public static RetCode ERR_ILLEGAL_ACCOUNT_TYPE = RetCode.newInstance("非法账号类型", 301013);
    /** 非法邮箱 */
    public static RetCode ERR_ILLEGAL_EMAIL_ACCOUNT = RetCode.newInstance("非法邮箱", 301014);

    // 角色
    /** 角色不存在 */
    public static RetCode ERR_ROLE_NOT_FOUND = RetCode.newInstance("角色不存在", 302001);
    /** 用户没有该角色 */
    public static RetCode ERR_USER_NOT_HAS_THE_ROLE = RetCode.newInstance("用户没有该角色", 302002);
    /** 角色已存在 */
    public static RetCode ERR_ROLE_EXIST = RetCode.newInstance("角色已存在", 302003);
    /** 角色权限冲突 */
    public static RetCode ERR_ROLE_RIGHT_CONFLICT = RetCode.newInstance("角色权限冲突", 302004);
    /** 初始化角色权限错误 */
    public static RetCode ERR_ROLE_RIGHT_INIT_FAILED = RetCode.newInstance("初始化角色权限错误", 302005);
    /** 用户没有角色 */
    public static RetCode ERR_USER_NOT_ROLE = RetCode.newInstance("用户没有角色", 302006);

    // 权限
    /** 权限参数不合法 */
    public static RetCode ERR_RIGHT_INVALID = RetCode.newInstance("权限参数不合法", 303001);
    /** 权限不存在 */
    public static RetCode ERR_RIGHT_NOT_FOUND = RetCode.newInstance("权限不存在", 303002);

}
