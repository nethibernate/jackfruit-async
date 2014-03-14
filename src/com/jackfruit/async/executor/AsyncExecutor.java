package com.jackfruit.async.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
/**
 * 通信框架处理器所有用户的调用和对用户可修改的变量访问全部都在本线程中执行
 * @author yaguang.xiao
 *
 */
public class AsyncExecutor {
	/** 单例对象 */
	public static final AsyncExecutor Instance = new AsyncExecutor();
	/** 单线程的线程池 */
	private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	/** 拒绝外部调用本类的构造方法 */
	private AsyncExecutor() {}
	
	/**
	 * 提交任务
	 * @param task
	 * @return
	 */
	public Future<?> submit(Runnable task) {
		return this.singleThreadExecutor.submit(task);
	}
	
	/**
	 * 关闭该处理器
	 */
	public void shutdown() {
		this.singleThreadExecutor.shutdown();
	}
	
	/**
	 * 等待该处理器的关闭
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 */
	public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		this.singleThreadExecutor.awaitTermination(timeout, unit);
	}
}
