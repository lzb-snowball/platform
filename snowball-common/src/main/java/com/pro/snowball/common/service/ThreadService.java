package com.pro.snowball.common.service;

import cn.hutool.core.map.WeakConcurrentMap;
import com.pro.framework.api.util.AssertUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class ThreadService {
    private final ThreadPoolTaskExecutor executor;
    private final Map<String, Future<?>> threadMap = new WeakConcurrentMap<>();

    // 开启一个线程
    public void startThread(String threadId, Runnable runnable) {
        if (threadMap.containsKey(threadId)) {
            Future<?> future = threadMap.get(threadId);
            if (!future.isDone()) { // 如果任务未完成
                throw new IllegalArgumentException("Thread with id " + threadId + " is already running.");
            }
            // 如果任务已经完成，允许覆盖
            threadMap.remove(threadId);
        }

        Runnable safeRunnable = () -> {
            try {
                runnable.run(); // 原始任务的执行逻辑
            } catch (Exception e) {
                // 记录日志或处理异常
                log.error("Thread task encountered an error: {}", threadId, e);
            }
        };
        // 提交任务到线程池，并保存 Future 对象
        Future<?> future = executor.submit(safeRunnable);
        threadMap.put(threadId, future);
    }

    // 关闭一个线程
    public void stopThread(String threadId) {
        Future<?> future = threadMap.get(threadId);
        if (future == null) {
            throw new IllegalArgumentException("No thread found with id " + threadId);
        }
        future.cancel(true);
        // 尝试取消任务
//        AssertUtil.isTrue(,"Thread with id " + threadId + " is cancelled.");
        threadMap.remove(threadId);
    }

    // 查询线程状态
    public boolean isThreadRunning(String threadId) {
        Future<?> future = threadMap.get(threadId);
        return future != null;
    }

    @Scheduled(fixedDelay = 3000) // 每分钟执行一次
    public void cleanupFinishedThreads() {
        threadMap.entrySet().removeIf(entry -> {
            Future<?> future = entry.getValue();
            return future.isDone() || future.isCancelled();
        });
    }
}
