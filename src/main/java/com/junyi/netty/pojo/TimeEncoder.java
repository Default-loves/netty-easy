package com.junyi.netty.pojo;

import com.junyi.netty.entity.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @time: 2021/1/5 11:46
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        System.out.println("2. TimeEncoder");
        out.writeInt((int) msg.value());
    }
}
