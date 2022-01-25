package com.junyi.netty;

import com.junyi.netty.entity.User;
import io.netty.util.Recycler;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import lombok.extern.slf4j.Slf4j;

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

    public static final int MAGIC_NUMBER = 0x12345678;

    public static void main(String[] args) {
        log.info(MAGIC_NUMBER + "");
    }

}
