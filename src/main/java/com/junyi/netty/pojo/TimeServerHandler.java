package com.junyi.netty.pojo;

import com.junyi.netty.entity.UnixTime;
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
        System.out.println("1. TimeServerHandler");
        final ChannelFuture f = ctx.writeAndFlush(new UnixTime()); // (3)
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
