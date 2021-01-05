package com.junyi.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @time: 2021/1/5 11:02
 * @version: 1.0
 * @author: junyi Xu
 * @description: TimeClientHandler 存在一个问题，即对于服务端发送回来的数据，可能不能一次性接收完毕，，即可能分为了多个IP包，那么客户端接受的时候需要判断数据是否完整
 * 更好的做法见 time2 包下面的
 */
@Slf4j
public class TimeClientHandler2 extends ChannelInboundHandlerAdapter {

    private ByteBuf byteBuf;
    public static final int CAPACITY = 4;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        byteBuf = ctx.alloc().buffer(CAPACITY);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        byteBuf.release();
        byteBuf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        byteBuf.writeBytes(m);
        m.release();
        if (byteBuf.readableBytes() >= CAPACITY) {
            long currentTimeMillis = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}