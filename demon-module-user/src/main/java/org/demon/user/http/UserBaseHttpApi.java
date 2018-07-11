package org.demon.user.http;

import org.apache.log4j.Logger;
import org.demon.user.UserConfig;
import org.demon.user.api.UserApi;
import org.demon.starter.common.environment.Env;
import org.demon.starter.utils.ClientResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * User 更新插入接口
 * @author Demon-Coffee
 * @since 1.0
 */
@RestController
@RequestMapping("/api/" + UserConfig.MODULE_NAME)
public class UserBaseHttpApi {

	private Logger logger = Logger.getLogger(UserBaseHttpApi.class);

	@Autowired
	private UserApi userApi;

	@RequestMapping("findList")
	public ClientResult findList(HttpServletRequest req) {
		Env env = (Env) req.getAttribute("env");
		return userApi.findList(env);
	}
}
