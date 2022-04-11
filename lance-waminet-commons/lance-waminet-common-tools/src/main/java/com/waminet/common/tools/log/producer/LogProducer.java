

package com.waminet.common.tools.log.producer;

import com.waminet.common.tools.log.BaseLog;
import com.waminet.common.tools.redis.RedisKeys;
import com.waminet.common.tools.redis.RedisUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 日志生产者（日志通过redis队列，异步保存到数据库）
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Component
public class LogProducer {

    @Autowired
    private RedisUtils redisUtils;

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("log-producer-pool-%d").build();

    ExecutorService pool = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 保存Log到Redis消息队列
     */
    public void saveLog(BaseLog log) {
        String key = RedisKeys.getSysLogKey();

        // 异步保存到队列
        pool.execute(() -> redisUtils.leftPush(key, log, RedisUtils.NOT_EXPIRE));
    }
}
