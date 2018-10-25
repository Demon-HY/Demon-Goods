//package org.demon.starter.utils.valid;
//
//import org.demon.starter.exception.LogicalException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 业务 验证 不通过 简单 抛出异常
// */
//public class BizValidatorUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(BizValidatorUtils.class);
//
//    /**
//     * obj为null 则抛出消息为 message 的Biz异常
//     *
//     * @param obj     the obj
//     * @param message the message
//     */
//    public static void notNull(Object obj, String message) {
//        if (obj == null) {
//            logger.error(message);
//            throw new LogicalException(message);
//        }
//    }
//
//    /**
//     * flag不为true 则抛出消息为 message 的Biz异常
//     *
//     * @param flag    the flag
//     * @param message the message
//     */
//    public static void isTrue(boolean flag, String message) {
//        if (!flag) {
//            logger.error(message);
//            throw new BizException(message);
//        }
//    }
//}
