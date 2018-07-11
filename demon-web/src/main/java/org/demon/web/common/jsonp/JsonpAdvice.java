package org.demon.web.common.jsonp;

import com.alibaba.fastjson.support.spring.FastJsonpResponseBodyAdvice;
import org.demon.utils.ValidUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.regex.Pattern;

/**
 * 解决 jsonp 跨域,当请求参数中有callback参数，并前端 Ajax 请求方式为jsonp 时，返回数据格式： jsonp({}),
 * 因为返回的结果用了自定义的FastJson解析,所以需要继承  FastJsonpResponseBodyAdvice
 * <p>
 * Created by Demon-HY on 2018/4/30 0030.
 */
@ControllerAdvice
public class JsonpAdvice extends FastJsonpResponseBodyAdvice {

    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");

    public JsonpAdvice() {
        super();
    }

    @Override
    protected boolean isValidJsonpQueryParam(String value) {
        if (ValidUtils.isBlank(value)) {
            return false;
        }
        return CALLBACK_PARAM_PATTERN.matcher(value).matches();
    }
}
