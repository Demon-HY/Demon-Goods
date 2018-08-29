package org.demon.starter.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方便使用日志
 */
public abstract class AbstractLogClass {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractLogClass() {
    }

}
