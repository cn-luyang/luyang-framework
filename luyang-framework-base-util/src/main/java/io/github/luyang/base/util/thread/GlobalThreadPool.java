package io.github.luyang.base.util.thread;

import io.github.luyang.base.util.exception.UtilException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

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
     */
    public static void execute(Runnable runnable) {
        try {
            execute(runnable);
        } catch (RejectedExecutionException e) {
            throw new UtilException(e, "任务被拒绝执行，线程池可能已关闭");
        } catch (Exception e) {
            throw new UtilException(e, "执行任务时发生异常");
        }
    }
}
