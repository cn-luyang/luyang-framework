package io.github.luyang.base.util.thread;

import io.github.luyang.base.util.ObjectUtil;
import io.github.luyang.base.util.exception.UtilException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 全局公共线程池
 *
 * @author yang.lu
 */
public class GlobalThreadPool {

	private static ExecutorService executor;

	/**
	 * 在全局线程池中执行任务
	 *
	 * @param runnable 要执行的任务
	 * @author yang.lu
	 */
	public static void execute(Runnable runnable) {
		try {
			executor.execute(runnable);
		} catch (Exception e) {
			throw new UtilException(e, "执行任务时发生异常");
		}
	}

	/**
	 * 执行有返回值的异步方法。
	 * <p>
	 * Future 代表一个异步执行的操作，通过 get() 方法可以获得操作的结果，如果异步操作还没有完成，则 get() 会使当前线程阻塞。
	 *
	 * @param <T>  返回值类型
	 * @param task Callable 任务
	 * @return Future
	 * @author yang.lu
	 */
	public static <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}

	/**
	 * 执行无返回值的异步方法。
	 *
	 * @param runnable Runnable 任务
	 * @return Future
	 * @author yang.lu
	 */
	public static Future<?> submit(Runnable runnable) {
		return executor.submit(runnable);
	}

	/**
	 * 检查线程池是否已关闭
	 *
	 * @return 如果已关闭返回 true，否则返回 false
	 * @author yang.lu
	 */
	public static boolean isShutdown() {
		return executor.isShutdown();
	}

	/**
	 * 关闭公共线程池
	 *
	 * @param immediate 是否立即关闭而不等待正在执行的线程
	 * @author yang.lu
	 */
	public static synchronized void shutdown(boolean immediate) {
		if (executor != null) {
			if (immediate) {
				executor.shutdownNow();
			} else {
				executor.shutdown();
			}
		}
	}
}
