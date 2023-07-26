package org.dizena.utils;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    /**
     * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
     */
    private static final int SIZE_CORE_POOL;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int SIZE_MAX_POOL;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final long ALIVE_TIME = 10;

    /**
     * 线程缓冲队列
     */
    private static final BlockingQueue<Runnable> queue;

    private static final ThreadPoolExecutor pool;


    static {
        SIZE_CORE_POOL = Runtime.getRuntime().availableProcessors();
        SIZE_MAX_POOL = SIZE_CORE_POOL * 2;
        queue = new ArrayBlockingQueue<>(100);
        pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
        pool.prestartAllCoreThreads();
    }


    public static ThreadPoolExecutor getPool() {
        return pool;
    }

    public static void start(Runnable runnable) {
        pool.execute(runnable);
    }

    public static void start(Thread thread) {
        pool.execute(thread);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return pool.submit(task);
    }
    public static void shutdown() {
        pool.shutdown();
    }
}