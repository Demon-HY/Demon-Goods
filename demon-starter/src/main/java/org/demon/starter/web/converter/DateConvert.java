package org.demon.starter.web.converter;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.demon.starter.common.constants.DateConstants;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期转换器
 * @zyj
 * @date 2018年01月23日 下午4:28:57
 */
public class DateConvert extends StdDateFormat {

    private static final long serialVersionUID = -3201781773655300201L;

    private final static Map<String, FastDateFormat> regexFormatMap=new HashMap<>();

    static {
        regexFormatMap.put("\\d{4}-\\d{1,2}", FastDateFormat.getInstance(DateConstants.yyyyMM));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2}", FastDateFormat.getInstance(DateConstants.yyyyMMdd));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}\\:\\d{1,2}", FastDateFormat.getInstance(DateConstants.yyyyMMddHHmm));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}\\:\\d{1,2}\\:\\d{1,2}", FastDateFormat.getInstance(DateConstants.yyyyMMddHHmmss));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}\\:\\d{1,2}\\:\\d{1,2}:\\d{1,3}", FastDateFormat.getInstance(DateConstants.yyyyMMddHHmmssSSS));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}\\:\\d{1,2}\\:\\d{1,2}.\\d{1,3}", FastDateFormat.getInstance(DateConstants.yyyyMMddHHmmssSSS_D));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}.\\d{1,3}Z", FastDateFormat.getInstance(DateConstants.yyyyMMddHHmmssSSS_TZ));
        regexFormatMap.put("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}.\\d{1,3}\\+\\d{1,4}", FastDateFormat.getInstance(DateConstants.yyyyMMddHHmmssSSS_TZ));

    }

    @Override
    public Date parse(String dateStr, ParsePosition pos) {
        return this.parse(dateStr);
    }

    @Override
    public Date parse(String source) {
        if(StringUtils.isEmpty(source)){
            return  null;
        }
        for(Map.Entry<String, FastDateFormat> entry : regexFormatMap.entrySet()) {
            String regex = entry.getKey();
            if(source.matches(regex)) {
                try {
                    return entry.getValue().parse(source);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid Parse date value '" + source + "'",e);
                }
            }
        }

        throw new IllegalArgumentException("Invalid date value '" + source + "'");
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return new StringBuffer(FastDateFormat.getInstance(DateConstants.yyyyMMddHHmmssSSS).format(date));
    }

    @Override
    public StdDateFormat clone() {
        return this;
    }
}