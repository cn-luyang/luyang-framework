package io.github.luyang.base.util;

import io.github.luyang.base.util.thread.GlobalThreadPool;

import java.util.concurrent.TimeUnit;

/**
 * 线程池工具
 *
 * @author yang.lu
 */
public final class ThreadUtil {

	private ThreadUtil(){}

    /**
     * 在全局线程池中执行任务
     *
     * @param runnable 要执行的任务
     * @author yang.lu
     */
    public static void execute(Runnable runnable) {
        GlobalThreadPool.execute(runnable);
    }

    /**
     * 指定线程睡眠时间
     *
     * @param timeout  睡眠时间
     * @param timeUnit 时间单位
     * @author yang.lu
     */
    public static void sleep(Number timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout.longValue());
        } catch (InterruptedException e) {
            // 恢复中断状态，让后续代码能感知到中断
            Thread.currentThread().interrupt();
        }
    }
}
