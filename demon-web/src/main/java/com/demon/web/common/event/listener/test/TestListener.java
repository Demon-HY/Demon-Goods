package com.demon.web.common.event.listener.test;

import com.demon.web.common.event.Event;
import com.demon.web.common.event.IListener;
import com.demon.web.common.event.type.test.TestEvent;
import com.demon.web.utils.RetCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 创建订单前监听事件
 * <p>
 * Created by Demon-HY on 2018/4/30 0030.
 */
@Component
public class TestListener implements IListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@EventListener
	@Override
	public void onEvent(Event event) {
		logger.info("Success listener create order event.");

		TestEvent testEvent = (TestEvent) event;

		if (testEvent.flag == 1) {
			logger.info("Flag: {} success.", testEvent.flag);
			return;
		}
		if (testEvent.flag == 2) {
			logger.warn("Flag: {} failed.", testEvent.flag);
			testEvent.isContinue = false;
			testEvent.breakReason = "事件被拦截";
			testEvent.retCodeEnum = RetCodeEnum.ERR_BAD_PARAMS;
			return;
		}
	}
}
