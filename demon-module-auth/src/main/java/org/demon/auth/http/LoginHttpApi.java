package org.demon.auth.http;

import org.demon.auth.AuthConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/" + AuthConfig.MODULE_NAME)
public class LoginHttpApi {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }
}
