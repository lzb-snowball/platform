package com.pro.snowball.common.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class ThreadService {
    private final ThreadPoolTaskExecutor executor;
    private final Map<String, Future<?>> threadMap = new ConcurrentHashMap<>();

    // 开启一个线程
    public void startThread(String threadId, Runnable runnable) {
        if (threadMap.containsKey(threadId)) {
            throw new IllegalArgumentException("Thread with id " + threadId + " is already running.");
        }

        // 提交任务到线程池，并保存 Future 对象
        Future<?> future = executor.submit(runnable);
        threadMap.put(threadId, future);
    }

    // 关闭一个线程
    public void stopThread(String threadId) {
        Future<?> future = threadMap.get(threadId);
        if (future == null) {
            throw new IllegalArgumentException("No thread found with id " + threadId);
        }

        // 尝试取消任务
        future.cancel(true);
        threadMap.remove(threadId);
    }

    // 查询线程状态
    public boolean isThreadRunning(String threadId) {
        Future<?> future = threadMap.get(threadId);
        return future != null && !future.isDone() && !future.isCancelled();
    }
}
