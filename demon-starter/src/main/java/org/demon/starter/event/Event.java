package org.demon.starter.event;

import org.demon.starter.utils.RetCodeEnum;
import org.springframework.context.ApplicationEvent;

/**
 * 事件接口
 * <p>
 * Created by Demon-HY on 2018/4/30 0030.
 */
public class Event extends ApplicationEvent {

    private static final long serialVersionUID = 544976410916533776L;

	/**
	 * 是否停止事件广播
	 */
	public boolean stopDispatch = false;

    /**
     * 告知逻辑是否继续往下走
     */
    public boolean isContinue = true;

    /**
     * 缘由,这个错误说明不是返回给客户端的,是给发送事件的服务说明阻止程序运行的原因
     * isContinue为false时的说明
     */
    public String breakReason = "";

	/**
	 * 被处理的次数
	 */
	public int iteration = 0;

    /**
     * 错误信息
     */
    public RetCodeEnum retCodeEnum;

	/**
	 * 最后一个处理该事件的类
	 */
	public Class<?> lastHandler;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public Event(Object source) {
        super(source);
    }
}
