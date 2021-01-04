package com.junyi.netty;

import com.junyi.netty.entity.User;
import io.netty.util.Recycler;
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

    public static final Recycler<User> recycler  = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    @Test
    public void test() {
        User user = recycler.get();
        user.setId(1);
        user.setDescription("apple");
        user.recycle();

        User user2 = recycler.get();
        log.info("{}", user2.getDescription());
        log.info("{}", user.equals(user2));
    }

}
