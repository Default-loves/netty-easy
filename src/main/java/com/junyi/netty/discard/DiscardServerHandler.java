package com.junyi.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @time: 2021/1/5 9:46
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ByteBuf is a reference-counted object which has to be released explicitly via the release() method. Please keep in mind that it is the handler's responsibility to release any reference-counted object passed to the handler.
        // commonly use in finally:  ReferenceCountUtil.release(msg);
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                log.info("{}", in.toString(CharsetUtil.UTF_8));
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
