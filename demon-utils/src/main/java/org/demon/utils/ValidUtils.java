package org.demon.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 有效验证
 * <p>
 * Created by Demon on 2017/7/22 0022.
 */
public class ValidUtils {

    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 判断某个对象是否为空 集合类、数组做特殊处理
     *
     * @param obj param
     * @return 如为空，返回true,否则false
     * @author YZH
     */
    @SuppressWarnings("unchecked")
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }

        // 如果不为null，需要处理几种特殊对象类型
        if (obj instanceof String) {
            String _obj = (String) obj;
            return StringUtils.isBlank(_obj);
        } else if (obj instanceof Collection) {
            // 对象为集合
            Collection coll = (Collection) obj;
            return coll.size() == 0;
        } else if (obj instanceof Map) {
            // 对象为Map
            Map map = (Map) obj;
            return map.size() == 0;
        } else if (obj.getClass().isArray()) {
            // 对象为数组
            return Array.getLength(obj) == 0;
        } else {
            // 其他类型，只要不为null，即不为empty
            return false;
        }
    }

    /**
     * 判断多个字段是否有为空的，用于做请求参数校验
     *
     * @param objs 可变数组
     * @return true 是空
     */
    public static boolean isBlanks(Object... objs) {
        if (objs.length == 0) {
            return true;
        }

        for (Object obj : objs) {
            if (isBlank(obj)) {
                return true;
            }
        }
        return false;
    }
}
