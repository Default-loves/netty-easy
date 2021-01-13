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
        String s = ""
    }

    public int minDeletionSize(String[] A) {
        int n = A.length;
        int m = A[0].length();
        // point[i] = true，说明A[j]字符串不再需要和A[j+1]字符串比较了
        boolean[] point = new boolean[n-1];
        int ans = 0;

        search:
        for (int j = 0; j < m; ++j) {
            for (int i = 0; i < n-1; ++i) {
                if (!point[i] && A[i].charAt(j) > A[i+1].charAt(j)) {   // 降序序列，那么需要删除该列，统计结果加1
                    ans++;
                    continue ;
                }
            }
            // 更新 point 数组
            // 需要注意的是，true值的个数应该是逐渐增加的，即不能够判断后赋值为 false
            for (int i = 0; i < n-1; ++i) {
                if (A[i].charAt(j) < A[i+1].charAt(j))
                    point[i] = true;
            }
        }
        return ans;
    }
}
