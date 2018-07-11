package org.demon.utils.beans;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import org.demon.utils.JsonUtil;
import org.demon.utils.datetime.DateStyle;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实例操作工具类
 * <p/>
 * Created by Demon on 2017/7/22 0022.
 */
public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    /**
     * 判断这个bean中是否存在此属性,如果存在就返回这个属性，不存在就返回null
     *
     * @param bean
     * @param fieldName
     * @return Field
     */
    public static Field isField(Object bean, String fieldName) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 通过属性名给bean中的属性赋值
     * <p>
     * 如果access 设置为false ,是私有的方法的时候，就会抛出异常
     *
     * @param bean
     * @param fieldName
     * @param fieldValue
     * @param access
     */
    public static void setField(Object bean, String fieldName, Object fieldValue, boolean access) {
        Field field = isField(bean, fieldName);
        if (null == field) {
            logger.error("the bean exclusive field :" + fieldName);
            throw new RuntimeException("the bean exclusive field :" + fieldName);
        }
        setField(bean, field, fieldValue, access);
    }

    /**
     * 通过属性名给bean中的属性赋值
     *
     * @param bean
     * @param fieldName
     * @param fieldValue
     */
    public static void setField(Object bean, String fieldName, Object fieldValue) {
        setField(bean, fieldName, fieldValue, true);
    }

    /**
     * 给bean的属性赋值
     *
     * @param bean
     * @param field
     * @param value
     * @param access
     */
    public static void setField(Object bean, Field field, Object value, boolean access) {
        try {
            if (!field.isAccessible() && access)
                field.setAccessible(true);
            field.set(bean, value);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            logger.error("set field with bean error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 给bean的属性赋值，默认是有权限的
     *
     * @param bean
     * @param field
     * @param value
     */
    public static void setField(Object bean, Field field, Object value) {
        setField(bean, field, value, true);
    }

    /**
     * 返回属性的值
     *
     * @param field
     * @param bean
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object bean, Field field) {
        T value = null;
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            value = (T) field.get(bean);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            logger.error("get field value error", e);
            throw new RuntimeException(e);
        }
        return value;
    }

    /**
     * 根据属性名返回属性的值
     *
     * @param bean
     * @param fieldName
     * @return <T>
     */
    public static <T> T getFieldValue(Object bean, String fieldName) {
        Field field = isField(bean, fieldName);
        if (null == field) {
            logger.error("the bean exclusive field :" + fieldName);
            throw new RuntimeException("the bean exclusive field :" + fieldName);
        }
        return getFieldValue(bean, field);
    }

    /**
     * bean复制所有除静态以外的属性到target
     *
     * @param bean
     * @param target
     */
    public static void copyFields(Object bean, Object target) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                copyField(bean, target, field);
            }
        }
    }

    /**
     * 从bean复制一个属性到target中
     *
     * @param bean
     * @param target
     * @param field
     */
    public static void copyField(Object bean, Object target, Field field) {
        try {
            field.setAccessible(true);
            field.set(target, field.get(bean));
        } catch (SecurityException e) {
            logger.error("copy field value error", e);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            logger.error("copy field value error", e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error("copy field value error", e);
            throw new RuntimeException(e);
        }
    }

    public static String describeStr(Object bean) {
        Gson gson = new GsonBuilder()
                .setDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS)
                .registerTypeAdapter(
                        new TypeToken<Map<String, Object>>() {
                        }.getType(),
                        (JsonDeserializer<Map<String, Object>>) (json, typeOfT, context) -> {

                            Map<String, Object> treeMap = new HashMap<>();
                            JsonObject jsonObject = json.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                treeMap.put(entry.getKey(), entry.getValue());
                            }
                            return treeMap;
                        }).create();
        return gson.toJson(bean);
    }

    /**
     * Bean 转为 Map
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> describe(Object bean) {
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS)
                    .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                    .registerTypeAdapter(
                            new TypeToken<Map<String, Object>>() {
                            }.getType(),
                            (JsonDeserializer<Map<String, Object>>) (json, typeOfT, context) -> {

                                Map<String, Object> treeMap = new HashMap<>();
                                JsonObject jsonObject = json.getAsJsonObject();
                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                for (Map.Entry<String, JsonElement> entry : entrySet) {
                                    treeMap.put(entry.getKey(), entry.getValue());
                                }
                                return treeMap;
                            }).create();
            String data = gson.toJson(bean);
            return JsonUtil.jsonToMap(data);
        } catch (Exception e) {
            logger.error("Bean describe failed.Data: {}", new Gson().toJson(bean), e);
            return null;
        }
    }

    public static <B, S extends B> S copy(B bo, Class type) {
        S so = null;
        try {
            so = (S) type.newInstance();
            if (bo == null) {
                return so;
            }
            // PropertyNamingStrategy.CamelCase 属性命名方法为驼峰命名法
            JavaBeanInfo deserializeBeanInfo = JavaBeanInfo.build(so.getClass(), so.getClass(),
                    PropertyNamingStrategy.CamelCase);
            List<FieldInfo> getters = TypeUtils.computeGetters(bo.getClass(), null);

            FieldInfo[] setters = deserializeBeanInfo.fields;
            Object v;
            FieldInfo getterfield;
            FieldInfo setterfidld;

            for (FieldInfo getter : getters) {
                getterfield = getter;
                for (FieldInfo setter : setters) {
                    setterfidld = setter;
                    if (setterfidld.name.compareTo(getterfield.name) == 0) {
                        v = getterfield.method.invoke(bo);
                        setterfidld.method.invoke(so, v);
                        break;
                    }

                }
            }
        } catch (Exception ex) {
            logger.error("对象拷贝失败", ex);
        }

        return so;
    }
}
