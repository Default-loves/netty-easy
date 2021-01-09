package com.junyi.netty;

import com.junyi.netty.entity.User;
import io.netty.util.Recycler;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @time: 2021/1/4 11:02
 * @version: 1.0
 * @author: junyi Xu
 * @description: 对象池 Recycler
 */
@Slf4j
public class aaa {

    public static final Recycler<User> recycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    public static final MpscArrayQueue<String> MPSC_ARRAY_QUEUE = new MpscArrayQueue<>(2);

    @Test
    public void test() {
        for (int i = 1; i <= 3; i++) {
            int index = i;
            new Thread(() -> MPSC_ARRAY_QUEUE.offer("data" + index), "thread" + index).start();
        }
        try {
            Thread.sleep(1000L);
            MPSC_ARRAY_QUEUE.add("data3"); // 入队操作，队列满则抛出异常
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("队列大小：" + MPSC_ARRAY_QUEUE.size() + ", 队列容量：" + MPSC_ARRAY_QUEUE.capacity());
        System.out.println("出队：" + MPSC_ARRAY_QUEUE.remove()); // 出队操作，队列为空则抛出异常
        System.out.println("出队：" + MPSC_ARRAY_QUEUE.poll()); // 出队操作，队列为空则返回 NULL
    }

}
