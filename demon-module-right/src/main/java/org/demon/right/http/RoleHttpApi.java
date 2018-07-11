package org.demon.right.http;

import org.demon.right.RightConfig;
import org.demon.right.api.RoleApi;
import org.demon.starter.common.environment.Env;
import org.demon.starter.utils.ClientResult;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Role 访问层
 * @author Demon-Coffee
 * @since 1.0
 */
@RestController
@RequestMapping("/api/" + RightConfig.MODULE_NAME)
public class RoleHttpApi {

	private Logger logger = Logger.getLogger(RoleHttpApi.class);

	@Autowired
	private RoleApi roleApi;

	@RequestMapping("findList")
	public ClientResult findList(HttpServletRequest req) {
		Env env = (Env) req.getAttribute("env");
		return roleApi.findList(env);
	}

	@RequestMapping("findDetail")
	public ClientResult findDetail(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return roleApi.findDetail(env);
	}

	@RequestMapping("save")
	public ClientResult save(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return roleApi.save(env);
	}

	@RequestMapping("delete")
	public ClientResult delete(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return roleApi.delete(env);
	}

	@RequestMapping("update")
	public ClientResult update(HttpServletRequest req){
		Env env = (Env) req.getAttribute("env");
		return roleApi.update(env);
	}
}
