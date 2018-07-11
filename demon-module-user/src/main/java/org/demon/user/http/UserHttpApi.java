package org.demon.user.http;

import org.demon.user.UserConfig;
import org.demon.user.api.UserApi;
import org.demon.web.common.environment.Env;
import org.demon.web.utils.ClientResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * User 访问层
 * @author Demon-Coffee
 * @since 1.0
 */
@RestController
@RequestMapping("/api/" + UserConfig.MODULE_NAME)
public class UserHttpApi {

	private Logger logger = Logger.getLogger(UserHttpApi.class);

	@Autowired
	private UserApi userApi;

	@RequestMapping("findList")
	public ClientResult findList(HttpServletRequest req) {
		Env env = (Env) req.getAttribute("env");
		return userApi.findList(env);
	}

	@RequestMapping("findDetail")
	public ClientResult findDetail(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return userApi.findDetail(env);
	}

	@RequestMapping("save")
	public ClientResult save(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return userApi.save(env);
	}

	@RequestMapping("delete")
	public ClientResult delete(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return userApi.delete(env);
	}

	@RequestMapping("update")
	public ClientResult update(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return userApi.update(env);
	}
}
