package com.junyi.netty.tmp;

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
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive message: {}", msg.toString());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        // Initialize the message.
        content = ctx.alloc().directBuffer(255).writeZero(255);

//        generateTraffic();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void generateTraffic() {
        // Flush the outbound buffer to the socket.
        // Once flushed, generate the same amount of traffic again.
        log.info("generateTraffic");
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
    }

    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            log.info("ChannelFutureListener");
            if (future.isSuccess()) {
                generateTraffic();
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };


}
