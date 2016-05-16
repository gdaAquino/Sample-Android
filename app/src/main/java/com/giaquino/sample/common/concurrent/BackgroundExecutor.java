package com.giaquino.sample.common.concurrent;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class BackgroundExecutor implements Executor {

    private final ExecutorService executorService;

    public BackgroundExecutor(int threadPoolCount) {
        executorService = Executors.newFixedThreadPool(threadPoolCount, new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull final Runnable r) {
                return new Thread() {
                    @Override
                    public void run() {
                        setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                        r.run();
                    }
                };
            }
        });
    }

    @Override
    public void execute(@NonNull Runnable command) {
        executorService.execute(command);
    }
}
