package org.demon.utils.unit;

public class TimeUnit extends BaseNumberUtil {
    
    private static final String TIME_REGEX = "(\\d)+((\\.)(\\d)+)?(\\s)*(ms|s|m|h|d|w)*";

    private static final long VALUE_WEEK = 1000L * 60 * 60 * 24 * 7L;
    private static final long VALUE_DAY = 1000L * 60 * 60 * 24;
    private static final long VALUE_HOUR = 1000L * 60 * 60;
    private static final long VALUE_MINUTE = 1000L * 60;
    private static final long VALUE_SECOND = 1000L;
    private static final long VALUE_MILLISECOND = 1;
    private static final String UNIT_MILLISECOND = "ms";
    private static final String UNIT_SECOND = "s";
    private static final String UNIT_MINUTE = "min";
    private static final String UNIT_HOUR = "h";
    private static final String UNIT_DAY = "d";
    private static final String UNIT_WEEK = "w";
    
    public Double value;
    private String unit;
    
    public TimeUnit(String str) {
        super(TIME_REGEX, str);
        this.value = Double.parseDouble(super.value);
        this.unit = toStanderUnit(super.unit);
        toMSValue();
    }
    
    public static String toStanderUnit(String str) {
        if (null == str || str.trim().length() == 0) {
            return UNIT_MILLISECOND;
        }
        str = str.toLowerCase();
        
        switch(str) {
        case "ms" :
            return UNIT_MILLISECOND;
        case "s" :
            return UNIT_SECOND;
        case "min" :
            return UNIT_MINUTE;
        case "h" :
            return UNIT_HOUR;
        case "d" :
            return UNIT_DAY;
        case "w" :
            return UNIT_WEEK;
        default:
            throw new IllegalArgumentException();
        }
        
    }
    
    private void toMSValue() {
        switch(unit) {
        case UNIT_MILLISECOND :
            value = value * VALUE_MILLISECOND;
            break;
        case UNIT_SECOND :
            value = value * VALUE_SECOND;
            break;
        case UNIT_MINUTE :
            value = value * VALUE_MINUTE;
            break;
        case UNIT_HOUR :
            value = value * VALUE_HOUR;
            break;
        case UNIT_DAY :
            value = value * VALUE_DAY;
            break;
        case UNIT_WEEK :
            value = value * VALUE_WEEK;
            break;
            default: break;
        }
    }
    
    public static String getSuitUnit(double value) {
        String unit = UNIT_MILLISECOND;
        if (value >= VALUE_WEEK) {
            unit = UNIT_WEEK;
        } else if (value >= VALUE_DAY) {
            unit = UNIT_DAY;
        } else if (value >= VALUE_HOUR) {
            unit = UNIT_HOUR;
        } else if (value >= VALUE_MINUTE) {
            unit = UNIT_MINUTE;
        } else if (value >= VALUE_SECOND) {
            unit = UNIT_SECOND;
        }
        
        return unit;
    }
    
    @Override
    public String toStrWithUnit() {
        String unit = getSuitUnit(value);
        return toStrWithUnit(unit);
    }

    @Override
    public String toStrWithUnit(String unit) {
        if (null == unit) {
            throw new IllegalArgumentException();
        }
        
        if (value == 0) {
            return "0 " + unit;
        }
        
        double result;
        switch(unit) {
        
        case UNIT_SECOND :
            result = ((double)value) / VALUE_SECOND;
            break;
        case UNIT_MINUTE :
            result = ((double)value) / VALUE_MINUTE;
            break;
        case UNIT_HOUR :
            result = ((double)value) / VALUE_HOUR;
            break;
        case UNIT_DAY :
            result = ((double)value) / VALUE_DAY;
            break;
        case UNIT_WEEK :
            result = ((double)value) / VALUE_WEEK;
            break;
        case UNIT_MILLISECOND :
            result = value;
            break;
        default:
            throw new IllegalArgumentException();
        }
        
        String tmp = String.format("%.2f", result);
        if ("0.00".equals(tmp)) {
            tmp = "0.01";
        }
        
        return tmp + " " + unit;
    }
    
    public static void main(String[] args) {
        TimeUnit bu = new TimeUnit("86400000");
        System.out.println(bu.value.longValue());
        System.out.println(bu.toStrWithUnit());
    }
}
