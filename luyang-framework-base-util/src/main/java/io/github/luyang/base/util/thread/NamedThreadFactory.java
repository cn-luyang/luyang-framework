package io.github.luyang.base.util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JDK 实现的线程工厂
 *
 * @author yang.lu
 */
public class NamedThreadFactory implements ThreadFactory {

	private final static Logger logger = LoggerFactory.getLogger(NamedThreadFactory.class);

	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;
	private final boolean daemon;

	public NamedThreadFactory(String namePrefix, boolean daemon) {
		this.group = Thread.currentThread().getThreadGroup();
		this.namePrefix = namePrefix + "-";
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread t = new Thread(group, runnable,
			namePrefix + threadNumber.getAndIncrement(),
			0);
		t.setDaemon(daemon);

		// 保证优先级正常
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}

		// 捕获未处理异常，防止线程默默死亡
		t.setUncaughtExceptionHandler((thread, e) -> {
			logger.error("全局线程池任务未捕获异常", e);
		});

		return t;
	}
}
