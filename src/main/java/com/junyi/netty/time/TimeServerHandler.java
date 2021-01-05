package com.junyi.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @time: 2021/1/5 9:46
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /** 当有客户端第一次连接到此服务器时触发 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        int value = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
        log.info("channel active: {}", value);
        time.writeInt(value);

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
