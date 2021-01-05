package com.junyi.netty.entity;

import java.util.Date;

/**
 * @time: 2021/1/5 11:48
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class UnixTime {

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}